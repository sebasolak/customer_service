package com.example.online_shop.clientproxy;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientProxyConfig {
    @Value("${customers.api.url.v1}")
    private String customersApiUrlV1;

    @Bean
    public CustomerResourceV1 getCustomerResourceV1() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(customersApiUrlV1);
        CustomerResourceV1 proxy = target.proxy(CustomerResourceV1.class);
        return proxy;
    }
}
