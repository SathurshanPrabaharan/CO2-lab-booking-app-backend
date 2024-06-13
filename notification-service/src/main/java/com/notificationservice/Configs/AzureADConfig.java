package com.notificationservice.Configs;

import com.microsoft.aad.msal4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureADConfig {

    @Value("${azure.client-id}")
    private String clientId;

    @Value("${azure.client-secret}")
    private String clientSecret;

    @Value("${azure.tenant-id}")
    private String tenantId;

    @Bean
    public ConfidentialClientApplication confidentialClientApplication() throws Exception {
        IClientSecret clientSecret = ClientCredentialFactory.createFromSecret(this.clientSecret);
        ConfidentialClientApplication app = ConfidentialClientApplication.builder(clientId, clientSecret)
                .authority("https://login.microsoftonline.com/" + tenantId)
                .build();
        return app;
    }
}
