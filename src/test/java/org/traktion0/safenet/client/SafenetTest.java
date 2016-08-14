/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.traktion0.safenet.client.beans.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.*;

/**
 *
 * @author paul
 */
public class SafenetTest {

    private static final String LAUNCHER_URL = "http://localhost:8100";

    private static final String APP_NAME = "Java Library Unit Tests";
    private static final String APP_ID = "traktion0";
    private static final String APP_VERSION = "0.0.1";
    private static final String APP_VENDOR = "Paul Green";

    private static Token token;
    private static Auth auth;
    private static Client client;
    private static WebTarget webTarget;
    
    @BeforeClass
    public static void setUpClass() {
        String[] permissions = {"SAFE_DRIVE_ACCESS"};
        auth = new Auth(
                new App(
                        APP_NAME,
                        APP_ID,
                        APP_VERSION,
                        APP_VENDOR
                ),
                permissions
        );

        Client client = ClientBuilder.newClient();
        webTarget = client.target(LAUNCHER_URL);
        token = new CreateAuthTokenCommand(webTarget, auth).execute();
    }
    
    @AfterClass
    public static void tearDownClass() {
        new DeleteAuthTokenCommand(webTarget, token).execute();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testDeleteMissingAuthToken() {
        String reason = "";
        String statusCode = "";

        try {
            Token missingToken = new Token();
            missingToken.setToken("1234567890");

            new DeleteAuthTokenCommand(webTarget, missingToken).execute();
        } catch(HystrixBadRequestException e) {
            reason = e.getCause().getMessage();
            statusCode = e.getMessage();
        }

        assertEquals("401", statusCode);
        assertEquals("Unauthorized", reason);
    }

    @Test
    public void testDeleteExistingAuthToken() {
        Token tempToken = new CreateAuthTokenCommand(webTarget, auth).execute();
        String authMessage = new DeleteAuthTokenCommand(webTarget, tempToken).execute();

        assertEquals("OK", authMessage);
    }

    @Test
    public void testValidateExistingAuthToken() {
        String authMessage = new ValidateAuthTokenCommand(webTarget, token).execute();

        assertEquals("OK", authMessage);
    }

    @Test
    public void testGetMissingDirectory() {
        String reason = "";
        String statusCode = "";

        try {
            new GetDirectoryCommand(webTarget, token, "missing_directory").execute();
        } catch(HystrixBadRequestException e) {
            reason = e.getCause().getMessage();
            statusCode = e.getMessage();
        }

        assertEquals("404", statusCode);
        assertEquals("Not Found", reason);
    }

    @Test
    public void testCreateNewDirectory() {
        // PG: Remove new directory if it already exists
        try {
            new DeleteDirectoryCommand(webTarget, token, "new_directory").execute();
        } catch(HystrixBadRequestException e) {
            // PG:ASSERT: Already deleted
        }

        String createMessage = new CreateDirectoryCommand(webTarget, token, "new_directory", new Directory()).execute();

        assertEquals("OK", createMessage);
    }

    @Test
    public void testGetExistingDirectory() {
        // PG: Setup existing directory to test
        try {
            new CreateDirectoryCommand(webTarget, token, "existing_directory", new Directory()).execute();
        } catch(HystrixBadRequestException e) {
            // PG:ASSERT: Already exists
        }

        Directory existingDirectory = new GetDirectoryCommand(webTarget, token, "existing_directory").execute();

        assertEquals("existing_directory", existingDirectory.getInfo().getName());
    }

    @Test
    public void testDeleteExistingDirectory() {
        // PG: Setup existing directory to test
        try {
            new CreateDirectoryCommand(webTarget, token, "delete_directory", new Directory()).execute();
        } catch(HystrixBadRequestException e) {
            // PG:ASSERT: Already exists
        }

        String deleteMessage = new DeleteDirectoryCommand(webTarget, token, "delete_directory").execute();

        assertEquals("OK", deleteMessage);
    }

    // PG:TODO: Test subdirectories
}
