package com.luminara.controller;

import com.luminara.config.SpringMongoConfig;
import com.luminara.domain.Client;
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
public class ClientController {

    private static final Logger log = Logger.getLogger(ClientController.class);


    // TODO pagination, sorting
    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> index() {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        List<Client> list;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        list = mongoOperation.findAll(Client.class);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public ResponseEntity<Client> index(@PathVariable String id) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchClientQuery;
        Client client;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        searchClientQuery = new Query(Criteria.where("_id").is(id));

        client = mongoOperation.findOne(searchClientQuery, Client.class);

        if(client != null)
            return new ResponseEntity<>(client, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public HttpEntity upsert (@RequestBody Client client) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchClientQuery;
        Update updateClient;
        WriteResult result;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        if(log.isDebugEnabled())
            log.debug("Client: " + client);

        // Query to search client
        searchClientQuery = new Query(Criteria.where("_id").is(client.getId()));

        client.setCreatedDate(new Date());
        client.setLastUpdate(new Date());
        updateClient = client.getUpdate();

        // update client if exist, if not create it
        result = mongoOperation.upsert(
                searchClientQuery,
                updateClient,
                Client.class);

        if(result.wasAcknowledged())
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/client", method = RequestMethod.PUT)
    public ResponseEntity<Client> update (@RequestBody Client client) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchClientQuery;
        Update updateClient;
        Client clientResult;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        if(log.isDebugEnabled())
            log.debug("Client: " + client);

        // Query to search Payment
        searchClientQuery = new Query(Criteria.where("_id").is(client.getId()));

        client.setLastUpdate(new Date());
        updateClient = client.getUpdate();

        // update client if exist
        clientResult = mongoOperation.findAndModify(
                searchClientQuery,
                updateClient,
                Client.class);

        if(clientResult != null)
            return new ResponseEntity<>(clientResult, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/client", method = RequestMethod.DELETE)
    public ResponseEntity<Client> delete (@RequestBody Client client) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchClientQuery;
        WriteResult result;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        if(log.isDebugEnabled())
            log.debug("Client: " + client);

        // Query to search Payment
        searchClientQuery = new Query(Criteria.where("_id").is(client.getId()));

        // Update payment if exist
        result = mongoOperation.remove(
                searchClientQuery,
                Client.class);

        if(result.wasAcknowledged() && result.isUpdateOfExisting())
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
