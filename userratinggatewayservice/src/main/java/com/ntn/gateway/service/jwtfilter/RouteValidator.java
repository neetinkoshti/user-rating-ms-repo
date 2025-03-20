package com.ntn.gateway.service.jwtfilter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final List<String> publicRoutes = Arrays.asList("/auth/token", "/auth/register","/eureka");  ;

    public Predicate<ServerHttpRequest> isSecured =
            request -> publicRoutes.stream()
            .noneMatch(uri-> request.getURI().getPath().contains(uri));
}
