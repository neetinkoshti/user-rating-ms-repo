package com.ntn.gateway.service.jwtfilter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());


    private RouteValidator routeValidator;

    private RestTemplate restTemplate;

    private WebClient.Builder webClientBuilder;

    @Autowired
    public JwtAuthenticationFilter(RouteValidator routeValidator, RestTemplate restTemplate,WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {
        // Put configuration properties here
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Implement filter logic here
        return (exchange, chain) -> {
            // Implement filter logic here

            if (routeValidator.isSecured.test(exchange.getRequest())) {
                // check header contains token
                logger.info("Inside JwtAuthenticationFilter");

                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
//                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    throw new RuntimeException("Authorization header is missing");
                }

                String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (!token.startsWith("Bearer ")) {
                    throw new RuntimeException("Authorization header is invalid");
                }else{
                    token = token.substring(7);
                }

                // call auth service to validate token
                try {

//                  String message =   callUsingRestTemplate(token);

                    // Using Feign client
//                   String message =  callUsingFeignClient(token);

//                    logger.info("Message from auth service : " + message);


                    // call auth service to validate token
                    return webClientBuilder.build()
                            .get()
                            .uri("http://AUTH-SERVICE/auth/validate/" + token)
                            .retrieve()
                            .bodyToMono(String.class)
                            .flatMap(message -> {
                                logger.info("Message from auth service : " + message);
                                return chain.filter(exchange);
                            })
                            .onErrorResume(e -> {
                                logger.info("Exception in validating token : " + e.getMessage());
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().setComplete();
                            });


                    } catch (Exception e) {
                    logger.info("Exception in validating token : " + e.getMessage());
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
            }

            return chain.filter(exchange);
        };

    }

    private static void callUsingFeignClient(String token) {
//        String message = authOpenFeignService.validateToken(token);
    }

    private String callUsingRestTemplate(String token) {
        String message = restTemplate.getForObject("http://AUTH-SERVICE/auth/validate/" + token, String.class);

        /*  ResponseEntity<String> response = restTemplate.exchange(
                            "http://AUTH-SERVICE/auth/validate/" + token,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<>() {
                            }
                    );
                   String message = response.getBody();*/

        return message;
    }
}
