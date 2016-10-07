/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client;

import org.glassfish.jersey.client.ClientProperties;
import org.junit.*;
import org.traktion0.safenet.client.beans.*;
import org.traktion0.safenet.client.commands.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author paul
 */
public class SafenetIntegrationTest {

    private static final String LAUNCHER_URL = "http://localhost:8100";

    private static final String APP_NAME = "Java Library Unit Tests";
    private static final String APP_ID = "traktion0";
    private static final String APP_VERSION = "0.0.1";
    private static final String APP_VENDOR = "Paul Green";

    private static Auth auth;
    private static WebTarget webTarget;
    private static SafenetFactory safenet;

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
        client.register(ErrorResponseFilter.class);
        client.property(ClientProperties.REQUEST_ENTITY_PROCESSING, "CHUNKED");
        webTarget = client.target(LAUNCHER_URL);

        safenet = SafenetFactory.getInstance(webTarget, auth);

        safenet.makeCreateDirectoryCommand("app/existing_directory").execute();
    }

    @AfterClass
    public static void tearDownClass() {
        safenet.makeDeleteDirectoryCommand("app/existing_directory").execute();
        safenet.makeDeleteAuthTokenCommand().execute();
    }

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    @Test
    public void testDeleteMissingAuthToken() {
        String reason = "";
        int statusCode = 0;

        try {
            Auth missingToken = new Auth();
            missingToken.setToken("1234567890");

            new DeleteAuthToken(webTarget, missingToken).execute();
        } catch (SafenetBadRequestException e) {
            reason = e.getReason();
            statusCode = e.getStatusCode();
        }

        assertEquals(401, statusCode);
        assertEquals("Unauthorized", reason);
    }

    @Test
    public void testDeleteExistingAuthToken() {
        auth = new Auth(
                new App(
                        APP_NAME + " Temp",
                        APP_ID,
                        APP_VERSION,
                        APP_VENDOR
                )
        );

        SafenetFactory safenetTemp = SafenetFactory.getInstance(webTarget, auth);
        String authMessage = safenetTemp.makeDeleteAuthTokenCommand().execute();

        assertEquals("OK", authMessage);
    }

    @Test
    public void testValidateExistingAuthToken() {
        String authMessage = safenet.makeGetAuthTokenCommand().execute();

        assertEquals("OK", authMessage);
    }

    @Test
    public void testGetMissingDirectory() {
        String reason = "";
        int statusCode = 0;

        try {
            safenet.makeGetDirectoryCommand("app/missing_directory").execute();
        } catch (SafenetBadRequestException e) {
            reason = e.getReason();
            statusCode = e.getStatusCode();
        }

        assertEquals(404, statusCode);
        assertEquals("Not Found", reason);
    }

    @Test
    public void testCreateNewDirectory() {
        // PG: Remove new directory if it already exists
        try {
            safenet.makeDeleteDirectoryCommand("app/new_directory").execute();
        } catch (SafenetBadRequestException e) {
            // PG:ASSERT: Already deleted
        }

        String message = safenet.makeCreateDirectoryCommand("app/new_directory").execute();

        assertEquals("OK", message);
    }

    @Test
    public void testGetExistingDirectory() {
        // PG: Setup existing directory to test
        try {
            safenet.makeCreateDirectoryCommand("app/existing_directory").execute();
        } catch (SafenetBadRequestException e) {
            // PG:ASSERT: Already exists
        }

        SafenetDirectory existingSafenetDirectory = safenet.makeGetDirectoryCommand("app/existing_directory").execute();

        assertEquals("existing_directory", existingSafenetDirectory.getInfo().getName());
    }

    @Test
    public void testDeleteExistingDirectory() {
        // PG: Setup existing directory to test
        try {
            safenet.makeCreateDirectoryCommand("app/delete_directory").execute();
        } catch (SafenetBadRequestException e) {
            // PG:ASSERT: Already exists
        }

        String message = safenet.makeDeleteDirectoryCommand("app/delete_directory").execute();

        assertEquals("OK", message);
    }

    // PG:TODO: Test subdirectories

    @Test
    public void testCreateNewFile() {
        try {
            safenet.makeDeleteFileCommand("app/new_file").execute();
        } catch (SafenetBadRequestException e) {
            // PG:ASSERT: Already deleted
        }

        File file = new File("src/it/resources/maidsafe.svg");
        String message = safenet.makeCreateFileCommand("app/new_file", file).execute();

        assertEquals("OK", message);
    }

    @Test
    public void testCreateNewFileFromFileInputStream() throws IOException {
        try {
            safenet.makeDeleteFileCommand("app/new_file").execute();
        } catch (SafenetBadRequestException e) {
            // PG:ASSERT: Already deleted
        }

        String message;
        File file = new File("src/it/resources/maidsafe.svg");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            message = safenet.makeCreateFileCommand("app/new_file", fileInputStream).execute();
        } catch (IOException e) {
            throw new IOException(e);
        }

        assertEquals("OK", message);
    }

    @Test(expected = SafenetBadRequestException.class)
    public void testCreateNewFileSourceMissing() {
        try {
            safenet.makeDeleteFileCommand("app/new_file").execute();
        } catch (SafenetBadRequestException e) {
            // PG:ASSERT: Already deleted
        }

        File file = new File("src/it/resources/missingfile.txt");
        safenet.makeCreateFileCommand("app/new_file", file).execute();
    }

    @Test
    public void testGetExistingFile() {
        long uploadFileLength = 0;
        long downloadFileLength = 0;

        try {
            File uploadFile = new File("src/it/resources/maidsafe.svg");
            uploadFileLength = uploadFile.length();
            safenet.makeCreateFileCommand("app/existing_directory/existing_file.svg", uploadFile).execute();
        } catch (SafenetBadRequestException e) {
            // PG: Already exists
        }

        SafenetFile downloadFile = safenet.makeGetFileCommand("app/existing_directory/existing_file.svg").execute();

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
            safenet.makeCreateFileCommand("app/delete_file", file).execute();
        } catch (SafenetBadRequestException e) {
            // PG: Already exists
        }

        String message = safenet.makeDeleteFileCommand("app/delete_file").execute();

        assertEquals("OK", message);
    }

    @Test
    public void testGetExistingFileAttributes() {
        try {
            File uploadFile = new File("src/it/resources/maidsafe.svg");
            safenet.makeCreateFileCommand("app/existing_directory/existing_file.svg", uploadFile).execute();
        } catch (SafenetBadRequestException e) {
            // PG: Already exists
        }

        SafenetFile downloadFileAttributes = safenet.makeGetFileAttributesCommand("app/existing_directory/existing_file.svg").execute();

        assertTrue(downloadFileAttributes.getCreatedOn() != null);
        assertTrue(downloadFileAttributes.getLastModified() != null);
    }

    @Test
    public void testCreateAndDeleteLongName() {
        String createMessage = safenet.makeCreateLongNameCommand("traktion123").execute();
        String deleteMessage = safenet.makeDeleteLongNameCommand("traktion123").execute();

        assertEquals("OK", createMessage);
        assertEquals("OK", deleteMessage);
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

            message = new CreateLongNameAndService(webTarget, auth, dns).execute();
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
        dns.setRootPath("app");
        dns.setServiceHomeDirPath("/existing_directory/");

        try {
            safenet.makeDeleteServiceFromLongNameCommand(dns).execute();
        } catch (SafenetBadRequestException e) {
            // PG:ASSERT: Already deleted
        }

        String message = safenet.makeCreateServiceCommand(dns).execute();

        assertEquals("OK", message);
    }

    @Test
    public void testDeleteServiceFromLongName() {
        Dns dns = new Dns();
        dns.setLongName("traktion0");
        dns.setServiceName("existingservice"); // PG: mustn't contain underscores
        dns.setRootPath("app");
        dns.setServiceHomeDirPath("/existing_directory");

        // PG: Setup existing directory to test
        try {
            safenet.makeCreateServiceCommand(dns).execute();
        } catch (SafenetBadRequestException e) {
            // PG:ASSERT: Already exists
        }

        String message = safenet.makeDeleteServiceFromLongNameCommand(dns).execute();

        assertEquals("OK", message);
    }
}
