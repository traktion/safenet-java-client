/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.glassfish.jersey.client.ClientProperties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.traktion0.safenet.client.beans.*;
import org.traktion0.safenet.client.commands.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.*;

/**
 *
 * @author paul
 */
public class SafenetIntegrationTest {

    private static final String LAUNCHER_URL = "http://localhost:8100";

    private static final String APP_NAME = "Java Library Unit Tests";
    private static final String APP_ID = "traktion0";
    private static final String APP_VERSION = "0.0.1";
    private static final String APP_VENDOR = "Paul Green";

    private static Token token;
    private static Auth auth;
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

        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        Client client = ClientBuilder.newClient();
        client.property(ClientProperties.REQUEST_ENTITY_PROCESSING, "CHUNKED");
        webTarget = client.target(LAUNCHER_URL);
        token = new CreateAuthToken(webTarget, auth).execute();
    }
    
    @AfterClass
    public static void tearDownClass() {
        new DeleteAuthToken(webTarget, token).execute();
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
        int statusCode = 0;

        try {
            Token missingToken = new Token();
            missingToken.setToken("1234567890");

            new DeleteAuthToken(webTarget, missingToken).execute();
        } catch(SafenetBadRequestException e) {
            reason = e.getDescription();
            statusCode = e.getStatusCode();
        }

        assertEquals(401, statusCode);
        assertEquals("Unauthorized", reason);
    }

    @Test
    public void testDeleteExistingAuthToken() {
        Token tempToken = new CreateAuthToken(webTarget, auth).execute();
        String authMessage = new DeleteAuthToken(webTarget, tempToken).execute();

        assertEquals("OK", authMessage);
    }

    @Test
    public void testValidateExistingAuthToken() {
        String authMessage = new GetAuthToken(webTarget, token).execute();

        assertEquals("OK", authMessage);
    }

    @Test
    public void testGetMissingDirectory() {
        String reason = "";
        int statusCode = 0;

        try {
            new GetDirectory(webTarget, token, SafenetCommand.DRIVE, "missing_directory").execute();
        } catch(SafenetBadRequestException e) {
            reason = e.getDescription();
            statusCode = e.getStatusCode();
        }

        assertEquals(404, statusCode);
        assertEquals("Not Found", reason);
    }

    @Test
    public void testCreateNewDirectory() {
        // PG: Remove new directory if it already exists
        try {
            new DeleteDirectory(webTarget, token, SafenetCommand.DRIVE, "new_directory").execute();
        } catch(SafenetBadRequestException e) {
            // PG:ASSERT: Already deleted
        }

        String message = new CreateDirectory(webTarget, token, SafenetCommand.DRIVE, "new_directory", new SafenetDirectory()).execute();

        assertEquals("OK", message);
    }

    @Test
    public void testGetExistingDirectory() {
        // PG: Setup existing directory to test
        try {
            new CreateDirectory(webTarget, token, SafenetCommand.DRIVE, "existing_directory", new SafenetDirectory()).execute();
        } catch(SafenetBadRequestException e) {
            // PG:ASSERT: Already exists
        }

        SafenetDirectory existingSafenetDirectory = new GetDirectory(webTarget, token, SafenetCommand.DRIVE, "existing_directory").execute();

        assertEquals("existing_directory", existingSafenetDirectory.getInfo().getName());
    }

    @Test
    public void testDeleteExistingDirectory() {
        // PG: Setup existing directory to test
        try {
            new CreateDirectory(webTarget, token, SafenetCommand.DRIVE, "delete_directory", new SafenetDirectory()).execute();
        } catch(SafenetBadRequestException e) {
            // PG:ASSERT: Already exists
        }

        String message = new DeleteDirectory(webTarget, token, SafenetCommand.DRIVE, "delete_directory").execute();

        assertEquals("OK", message);
    }

    // PG:TODO: Test subdirectories

    @Test
    public void testCreateNewFile() {
        try {
            new DeleteFile(webTarget, token, SafenetCommand.DRIVE, "new_file").execute();
        } catch(SafenetBadRequestException e) {
            // PG:ASSERT: Already deleted
        }

        File file = new File("src/it/resources/maidsafe.svg");
        String message = new CreateFile(webTarget, token, SafenetCommand.DRIVE, "new_file", file).execute();

        assertEquals("OK", message);
    }

    @Test(expected = SafenetBadRequestException.class)
    public void testCreateNewFileSourceMissing() {
        try {
            new DeleteFile(webTarget, token, SafenetCommand.DRIVE, "new_file").execute();
        } catch(SafenetBadRequestException e) {
            // PG:ASSERT: Already deleted
        }

        File file = new File("src/it/resources/missingfile.txt");
        new CreateFile(webTarget, token, SafenetCommand.DRIVE, "new_file", file).execute();
    }

    @Test
    public void testGetExistingFile() {
        long uploadFileLength = 0;
        long downloadFileLength = 0;

        try {
            File uploadFile = new File("src/it/resources/maidsafe.svg");
            uploadFileLength = uploadFile.length();
            new CreateFile(webTarget, token, SafenetCommand.DRIVE, "existing_directory/existing_file.svg", uploadFile).execute();
        } catch(SafenetBadRequestException e) {
            // PG: Already exists
        }

        SafenetFile downloadFile = new GetFile(webTarget, token, SafenetCommand.DRIVE, "existing_directory/existing_file.svg").execute();

        try (InputStream inputStream = downloadFile.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                downloadFileLength += bytesRead;
            }
        } catch (IOException e) {
            downloadFileLength = 0;
        }

        assertTrue(downloadFileLength == uploadFileLength);
    }

    @Test
    public void testDeleteExistingFile() {
        try {
            File file = new File("src/it/resources/maidsafe.svg");
            new CreateFile(webTarget, token, SafenetCommand.DRIVE, "delete_file", file).execute();
        } catch(SafenetBadRequestException e) {
            // PG: Already exists
        }

        String message = new DeleteFile(webTarget, token, SafenetCommand.DRIVE, "delete_file").execute();

        assertEquals("OK", message);
    }

    /*@Test
    public void testCreateLongNameAndService() {
        String message;
        String reason;
        String statusCode;

        try {
            Dns dns = new Dns();
            dns.setLongName("traktion0");
            dns.setServiceName("test");
            dns.setRootPath(SafenetCommand.DRIVE);
            dns.setServiceHomeDirPath("/");

            message = new CreateLongNameAndService(webTarget, token, dns).execute();
        } catch(HystrixBadRequestException e) {
            reason = e.getCause().getMessage();
            statusCode = e.getMessage();
            message = statusCode+": "+reason;
        }

        assertEquals("OK", message);
    }*/

    @Test
    public void testAddServiceToLongName() {
        Dns dns = new Dns();
        dns.setLongName("traktion0");
        dns.setServiceName("newservice"); // PG: mustn't contain underscores
        dns.setRootPath(SafenetCommand.DRIVE);
        dns.setServiceHomeDirPath("/existing_directory/");

        try {
            new DeleteServiceFromLongName(webTarget, token, dns).execute();
        } catch(SafenetBadRequestException e) {
            // PG:ASSERT: Already deleted
        }

        String message = new CreateService(webTarget, token, dns).execute();

        assertEquals("OK", message);
    }

    @Test
    public void testDeleteServiceFromLongName() {
        Dns dns = new Dns();
        dns.setLongName("traktion0");
        dns.setServiceName("existingservice"); // PG: mustn't contain underscores
        dns.setRootPath(SafenetCommand.DRIVE);
        dns.setServiceHomeDirPath("/existing_directory/");

        // PG: Setup existing directory to test
        try {
            new CreateService(webTarget, token, dns).execute();
        } catch(SafenetBadRequestException e) {
            // PG:ASSERT: Already exists
        }

        String message = new DeleteServiceFromLongName(webTarget, token, dns).execute();

        assertEquals("OK", message);
    }
}
