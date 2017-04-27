package com.luminara.controller;

import com.luminara.config.SpringMongoConfig;
import com.luminara.domain.Payment;
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
public class PaymentController {

    private static final Logger log = Logger.getLogger(PaymentController.class);


    // TODO pagination, sorting
    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public ResponseEntity<List<Payment>> index() {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        List<Payment> list;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        list = mongoOperation.findAll(Payment.class);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/payment/{id}", method = RequestMethod.GET)
    public ResponseEntity<Payment> index(@PathVariable String id) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchPaymentQuery;
        Payment payment;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        searchPaymentQuery = new Query(Criteria.where("_id").is(id));

        payment = mongoOperation.findOne(searchPaymentQuery, Payment.class);

        if(payment != null)
            return new ResponseEntity<>(payment, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public HttpEntity upsert (@RequestBody Payment payment) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchPaymentQuery;
        Update updatePayment;
        WriteResult result;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        if(log.isDebugEnabled())
            log.debug("Payment: " + payment);

        // Query to search Payment
        searchPaymentQuery = new Query(Criteria.where("_id").is(payment.getId()));

        payment.setCreatedDate(new Date());
        payment.setLastUpdate(new Date());
        updatePayment = payment.getUpdate();

        // update payment if exist, if not create it
        result = mongoOperation.upsert(
                searchPaymentQuery,
                updatePayment,
                Payment.class);

        if(result.wasAcknowledged())
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/payment", method = RequestMethod.PUT)
    public ResponseEntity<Payment> update (@RequestBody Payment payment) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchPaymentQuery;
        Update updatePayment;
        Payment paymentResult;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        if(log.isDebugEnabled())
            log.debug("Payment: " + payment);

        // Query to search Payment
        searchPaymentQuery = new Query(Criteria.where("_id").is(payment.getId()));

        payment.setLastUpdate(new Date());
        updatePayment = payment.getUpdate();

        // update payment if exist
        paymentResult = mongoOperation.findAndModify(
                searchPaymentQuery,
                updatePayment,
                Payment.class);

        if(paymentResult != null)
            return new ResponseEntity<>(paymentResult, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/payment", method = RequestMethod.DELETE)
    public ResponseEntity<Payment> delete (@RequestBody Payment payment) {
        ApplicationContext ctx;
        MongoOperations mongoOperation;
        Query searchPaymentQuery;
        WriteResult result;

        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        if(log.isDebugEnabled())
            log.debug("Payment: " + payment);

        // Query to search Payment
        searchPaymentQuery = new Query(Criteria.where("_id").is(payment.getId()));

        // Update payment if exist
        result = mongoOperation.remove(
                searchPaymentQuery,
                Payment.class);

        if(result.wasAcknowledged() && result.isUpdateOfExisting())
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
