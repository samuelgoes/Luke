package com.luminara.controller;

import com.luminara.config.SpringMongoConfig;
import com.luminara.domain.Payment;
import com.luminara.domain.Property;
import com.mongodb.WriteResult;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PropertyController {

    private static final Logger log = Logger.getLogger(PropertyController.class);


    // TODO pagination, sorting
    @RequestMapping(value = "/property", method = RequestMethod.GET)
    public ResponseEntity<List<Property>> index() {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        List<Property> list;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        list = mongoOperation.findAll(Property.class);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/property/{id}", method = RequestMethod.GET)
    public ResponseEntity<Property> index(@PathVariable String id) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchPropertyQuery;
        Property property;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        searchPropertyQuery = new Query(Criteria.where("_id").is(id));

        property = mongoOperation.findOne(searchPropertyQuery, Property.class);

        if(property != null)
            return new ResponseEntity<>(property, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/property", method = RequestMethod.POST)
    public HttpEntity upsert (@RequestBody Property property) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchPropertyQuery;
        Update updateProperty;
        WriteResult result;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        if(log.isDebugEnabled())
            log.debug("Property: " + property);

        // Query to search Property
        searchPropertyQuery = new Query(Criteria.where("_id").is(property.getId()));

        property.setCreatedDate(new Date());
        property.setLastUpdate(new Date());
        updateProperty = property.getUpdate();

        // update property if exist, if not create it
        result = mongoOperation.upsert(
                searchPropertyQuery,
                updateProperty,
                Property.class);

        if(result.wasAcknowledged())
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/property", method = RequestMethod.PUT)
    public ResponseEntity<Property> update (@RequestBody Property property) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchPropertyQuery;
        Update updateProperty;
        Property propertyResult;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        if(log.isDebugEnabled())
            log.debug("Property: " + property);

        // Query to search Payment
        searchPropertyQuery = new Query(Criteria.where("_id").is(property.getId()));

        property.setLastUpdate(new Date());
        updateProperty = property.getUpdate();

        // update property if exist
        propertyResult = mongoOperation.findAndModify(
                searchPropertyQuery,
                updateProperty,
                Property.class);

        if(propertyResult != null)
            return new ResponseEntity<>(propertyResult, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/property", method = RequestMethod.DELETE)
    public ResponseEntity<Payment> delete (@RequestBody Property property) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchPropertyQuery;
        WriteResult result;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        if(log.isDebugEnabled())
            log.debug("Property: " + property);

        // Query to search property
        searchPropertyQuery = new Query(Criteria.where("_id").is(property.getId()));

        // Update property if exist
        result = mongoOperation.remove(
                searchPropertyQuery,
                Property.class);

        if(result.wasAcknowledged() && result.isUpdateOfExisting())
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
