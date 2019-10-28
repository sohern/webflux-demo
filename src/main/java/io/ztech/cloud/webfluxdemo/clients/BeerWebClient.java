package io.ztech.cloud.webfluxdemo.clients;

import io.ztech.cloud.webfluxdemo.domain.Beer;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BeerWebClient {

    private WebClient client = WebClient.create("http://localhost:8080");

    private Mono<String> result = client.get()
            .uri("/beers/5/5")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .flatMap(res -> res
                    .bodyToMono(String.class)
                    .flatMap(beerList -> Mono.just(
                            "\n\nFound new Beer Inventory of " + " beers:" +
                                    beerList.toString()
                    )));

    public void getResult() {
        result.doOnNext(System.out::println)
                .block();
    }

}
