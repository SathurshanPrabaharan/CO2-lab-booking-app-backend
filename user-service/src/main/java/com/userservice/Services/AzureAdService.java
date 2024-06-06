package com.userservice.Services;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;

import java.util.Collections;

public class AzureAdService {

    private GraphServiceClient<?> graphClient;

    public AzureAdService(String clientId, String clientSecret, String tenantId) {
        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
                Collections.singletonList("https://graph.microsoft.com/.default"), credential);

        this.graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .buildClient();
    }

    public User createUser(User newUser) {
        try {
            return graphClient.users()
                    .buildRequest()
                    .post(newUser);
        }catch (Exception e){
            throw new RuntimeException("Error occurred while creating user in Azure AD: " + e.getMessage(), e);
        }

    }

    public User updateUser(String userId, User updatedUser) {
        try {
            return graphClient.users(userId)
                    .buildRequest()
                    .patch(updatedUser);
        }catch (Exception e){
            throw new RuntimeException("Error occurred while updating user in Azure AD: " + e.getMessage(), e);
        }
    }

    public void disableUserAccount(String userId) {
        try {
            User userToUpdate = new User();
            userToUpdate.accountEnabled = false;

            graphClient.users(userId)
                    .buildRequest()
                    .patch(userToUpdate);
        }catch (Exception e){
            throw new RuntimeException("Error occurred while disabling user account in Azure AD: " + e.getMessage(), e);
        }
    }


}
