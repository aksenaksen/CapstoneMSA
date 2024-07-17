package com.example.apigatewayserver.filter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig>{

    @Value("${jwt.secret}")
    private String secret;
    Environment env;
    public AuthorizationHeaderFilter(Environment env){
        super(NameConfig.class);
        this.env = env;
    }

    @Override
    public GatewayFilter apply(NameConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "No Authorizaion Header",HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer ", "");

            if(!isTokenValid(jwt)){
                return onError(exchange, "Token is not Valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };

    }

    private boolean isTokenValid(String token) {
        log.info(token);
        try {
            DecodedJWT decodedJWT =  JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
            log.info("Token expires at: " + decodedJWT.getExpiresAt());
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 Token입니다", e.getMessage());
            return false;
        }
    }
    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(error);
        return response.setComplete();
    }
}
