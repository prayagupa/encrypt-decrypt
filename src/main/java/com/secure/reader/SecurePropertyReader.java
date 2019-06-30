package com.secure.reader;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;

import java.util.Base64;
import java.util.Optional;

public class SecurePropertyReader {

    private String region;
    private AWSSecretsManager client;

    public SecurePropertyReader(String region) {
        this.region = region;
        this.client = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();
    }

    public Optional<String> getSecret(String secretName) {

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);

        try {
            var getSecretValueResult = client.getSecretValue(getSecretValueRequest);
            // Decrypts secret using the associated KMS CMK.
            // Depending on whether the secret is a string or binary, one of these fields will be populated.
            if (getSecretValueResult.getSecretString() != null) {
                return Optional.of(getSecretValueResult.getSecretString());
            } else {
                return Optional.of(new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array()));
            }

        } catch (DecryptionFailureException e) {
            // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
            // Deal with the exception here, and/or rethrow at your discretion.
            e.printStackTrace();
            return Optional.empty();
        } catch (InternalServiceErrorException e) {
            // An error occurred on the server side.
            // Deal with the exception here, and/or rethrow at your discretion.
            e.printStackTrace();
            return Optional.empty();
        } catch (InvalidParameterException e) {
            // You provided an invalid value for a parameter.
            // Deal with the exception here, and/or rethrow at your discretion.
            e.printStackTrace();
            return Optional.empty();
        } catch (InvalidRequestException e) {
            // You provided a parameter value that is not valid for the current state of the resource.
            // Deal with the exception here, and/or rethrow at your discretion.
            e.printStackTrace();
            return Optional.empty();
        } catch (ResourceNotFoundException e) {
            // We can't find the resource that you asked for.
            // Deal with the exception here, and/or rethrow at your discretion.
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static SecurePropertyReader instance = new SecurePropertyReader(
            "us-west-2"
    );

    public static void main(String[] args) {

        var username = instance.getSecret("db.username");
        System.out.println("username: " + username);
        var password = instance.getSecret("db.password");
        System.out.println("password: " + password);

    }
}
