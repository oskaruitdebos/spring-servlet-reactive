package com.deloitte.codingforfun.quotesapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class QuotesController {

    private QuoteRepository quoteRepository;

    @Autowired
    public QuotesController(QuoteRepository quoteService) {
        this.quoteRepository = quoteService;
    }

    @GetMapping(path = "v1/quote")
    public Flux<Quote> getAll() {
        return quoteRepository.findAll();
    }

    @PostMapping(path = "v1/quote")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Quote> save(@RequestBody Quote quote) {
        quote.setId(UUID.randomUUID().toString());
        return quoteRepository.save(quote).map(q -> q);
    }
}