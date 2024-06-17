package com.notificationservice.Services.Impls;

import com.microsoft.aad.msal4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@Service
public class TokenService {

    @Autowired
    private ConfidentialClientApplication confidentialClientApplication;

    public String getAccessToken() throws Exception {
        ClientCredentialParameters parameters = ClientCredentialParameters
                .builder(Collections.singleton("https://graph.microsoft.com/.default"))
                .build();

        CompletableFuture<IAuthenticationResult> future = confidentialClientApplication.acquireToken(parameters);
        IAuthenticationResult authResult = future.get();

        return authResult.accessToken();
    }
}
