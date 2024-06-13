package com.notificationservice.Services.Impls;


import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.http.GraphServiceException;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Service
public class EmailService {

    @Value("${azure.tenant-id}")
    private String tenantId;

    @Value("${azure.client-id}")
    private String clientId;

    @Value("${azure.client-secret}")
    private String clientSecret;

    @Autowired
    private TokenService tokenService;

    public void sendEmail() throws Exception {
        String accessToken = tokenService.getAccessToken();

        // Authenticate with Microsoft Graph API using MSAL4J
        TokenCredentialAuthProvider authProvider = getTokenAuthProvider(accessToken);

        // Build GraphServiceClient
        GraphServiceClient<Request> graphClient = GraphServiceClient
                .builder()
                .authenticationProvider(authProvider)
                .buildClient();

        // Create email message
        Message email = new Message();
        email.subject = "Test Email";
        ItemBody body = new ItemBody();
        body.contentType = BodyType.TEXT;
        body.content = "This is a test email sent from Spring Boot using Microsoft Graph API.";
        email.body = body;

        // Set recipient
        Recipient recipient = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = "lab@sathurshanath2021gmail.onmicrosoft.com"; // Change to recipient's email
        recipient.emailAddress = emailAddress;
        email.toRecipients = Collections.singletonList(recipient);

        // Create the UserSendMailParameterSet
        UserSendMailParameterSet parameters = new UserSendMailParameterSet();

        try {
            // Send the email with the provided parameters
            graphClient.me().sendMail(parameters).toString();
            System.out.println("Email sent successfully!");
        } catch (GraphServiceException ex) {
            System.out.println("Error sending email: " + ex.getResponseCode());
        }



    }

    private TokenCredentialAuthProvider getTokenAuthProvider(String accessToken) throws ExecutionException, InterruptedException {


        // Build ClientSecretCredential
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        // Return TokenCredentialAuthProvider initialized with the obtained access token
        return new TokenCredentialAuthProvider(clientSecretCredential);
    }
}
