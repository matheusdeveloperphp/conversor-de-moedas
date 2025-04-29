package com.solucaotecnologia.conversor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;


@RestController
public class CotacaoController {

    private final WebClient webClient = WebClient.create();

    @GetMapping("/api/converter")
    public double converter(@RequestParam double valorReal) {
        double cotacaoDolar = buscarCotacaoDolar();
        return valorReal / cotacaoDolar;
    }

    private double buscarCotacaoDolar() {
        String url = "https://economia.awesomeapi.com.br/json/last/USD-BRL";

        Mono<Map> monoResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Map.class);

        Map<String, Map<String, String>> response = monoResponse.block();

        String bidStr = response.get("USDBRL").get("bid");

        return Double.parseDouble(bidStr);
    }
}