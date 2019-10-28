package io.ztech.cloud.webfluxdemo;

import io.ztech.cloud.webfluxdemo.controllers.BeerController;
import io.ztech.cloud.webfluxdemo.domain.Beer;
import io.ztech.cloud.webfluxdemo.repositories.BeerRepository;
import io.ztech.cloud.webfluxdemo.services.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest(controllers = BeerController.class)
@Import(BeerServiceImpl.class)
class BeerServiceImplTest {

    @MockBean
    private
    BeerRepository repository;

    @Autowired
    private WebTestClient client;

    @Test
    void getAll() {
        Mockito
                .when(this.repository.findAll())
                .thenReturn(Flux.just(
                        Beer.builder()
                                .id("21st_amendment_brewery_cafe-21a_ipa")
                                .brewery("21st_amendment_brewery_cafe")
                                .name("21A IPA")
                                .abv((float) 7.20)
                                .description( "Deep golden color. Citrus and piney hop aromas. Assertive malt backbone supporting the overwhelming bitterness. Dry hopped in the fermenter with four types of hops giving an explosive hop aroma. Many refer to this IPA as Nectar of the Gods. Judge for yourself. Now Available in Cans!")
                                .build()));

        this.client
                .get()
                .uri("/beers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo("21st_amendment_brewery_cafe-21a_ipa")
                .jsonPath("$.[0].name").isEqualTo("21A IPA");
    }

}
