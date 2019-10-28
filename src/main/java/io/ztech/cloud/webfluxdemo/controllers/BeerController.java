package io.ztech.cloud.webfluxdemo.controllers;

import io.ztech.cloud.webfluxdemo.domain.Beer;
import io.ztech.cloud.webfluxdemo.services.BeerService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log
@RestController
public class BeerController {

    @Autowired
    private BeerService beerService;

    @GetMapping("/beers")
    public Flux<Beer> getBeers() {
        log.info("in getBeers no vars");
        return beerService.allBeers().onErrorReturn(Beer.builder().build());
    }

    @GetMapping("/beers/{limit}/{offset}")
    public Flux<Beer> getBeers(@PathVariable int limit, @PathVariable int offset) {
        log.info("In getBeers with vars");
        return beerService.allBeers(limit, offset);
    }

    @GetMapping("/beer/{id}")
    public Mono<Beer> getBeer(@PathVariable String id) {
        return beerService.findById(id);
    }
}
