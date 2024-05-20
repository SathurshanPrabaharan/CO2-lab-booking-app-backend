package com.adminapigateway.Filters;

import com.adminapigateway.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;
//    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if(validator.isSecured.test(exchange.getRequest())){
                //header contains token or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("missing runtime authorization header");
                }

                //get authorization header
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }

                try{
                    //REST call to AUTH service
//                    restTemplate.getForObject("http://IDENTIY-SERVICE/api/v1/auth/validate?token="+authHeader,String.class);
                    jwtUtil.validateToken(authHeader);

                }catch(Exception e){
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }

            return chain.filter(exchange);
        }));
    }

    public AuthenticationFilter() {
        super(Config.class);
    }

    public static  class Config{

    }
}
