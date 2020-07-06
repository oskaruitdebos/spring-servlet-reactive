package com.deloitte.codingforfun.quotesapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class QuotesController {

    private MongoTemplate mongoTemplate;

    @Autowired
    public QuotesController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @RequestMapping(value = "/v1/quote")
    public ResponseEntity<List<Quote>> retrieve() {
        return new ResponseEntity<>(mongoTemplate.find(new Query(), Quote.class), HttpStatus.OK);
    }

    @PostMapping(value = "/v1/quote")
    public ResponseEntity<Quote> submit(@RequestBody Quote quote) {
        quote.setId(UUID.randomUUID().toString());
        mongoTemplate.insert(quote);
        return new ResponseEntity(quote, HttpStatus.OK);
    }
}