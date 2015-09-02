package com.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Custom unit tests for {@link CountersApi}
 *
 * @author matthew.lowe
 */
public class CountersApiTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
        Counters.clearCounters();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }


    @Test
    public void shouldIncreaseCounters() {
        // given
        Counters.loadPersistantData();
        Response responseMsg = target.path("counters/increase").request().header("key", "visits").post(Entity.text("POST"));
        String ent = responseMsg.readEntity(String.class);
        assertEquals("1", ent);

        // when
        responseMsg = target.path("counters/increase").request().header("key", "visits").post(Entity.text("POST"));
        ent = responseMsg.readEntity(String.class);

        // then
        assertEquals("2", ent);
    }


    @Test
    public void shouldNotIncreaseInvalidName() {
        // given

        // when
        Response responseMsg = target.path("counters/increase").request().header("key", "invalidname").post(Entity.text("POST"));
        String ent = responseMsg.readEntity(String.class);

        // then
        assertEquals("No counter called: invalidname", ent);

    }


    @Test
    public void shouldAddNewCounter() {
        // given

        // when
        Response responseMsg = target.path("counters/add").request().header("key", "counterb").post(Entity.text("POST"));

        // then
        String ent = responseMsg.readEntity(String.class);
        assertEquals("Successfully added counter name: 'counterb'.", ent);
    }


    @Test
    public void shouldNotAddAlreadyExistingName() {
        // given
        Counters.loadPersistantData();

        // when
        Response responseMsg = target.path("counters/add").request().header("key", "visits").post(Entity.text("POST"));

        // then
        String ent = responseMsg.readEntity(String.class);
        assertEquals("Counter name: 'visits' is already in use.", ent);
    }


    @Test
    public void shouldNotAddInvalidCounterNameEmpty() {
        // given

        // when
        Response responseMsg = target.path("counters/add").request().header("key", "").post(Entity.text("POST"));

        // then
        String ent = responseMsg.readEntity(String.class);
        assertEquals("Parameter can not be null or empty", ent);
    }


    @Test
    public void shouldNotAddInvalidCounterNameNull() {
        // given

        // when
        Response responseMsg = target.path("counters/add").request().header("key", null).post(Entity.text("POST"));

        // then
        String ent = responseMsg.readEntity(String.class);
        assertEquals("Parameter can not be null or empty", ent);
    }


    @Test
    public void shouldReturnAllCounters() {
        // given
        Counters.loadPersistantData();
        Response response = target.path("counters/add").request().header("key", "counterc").post(Entity.text("Code:200"));
        String ent = response.readEntity(String.class);
        assertEquals("Successfully added counter name: 'counterc'.", ent);

        response = target.path("counters/increase").request().header("key", "counterc").post(Entity.text("Code:200"));
        ent = response.readEntity(String.class);
        assertEquals("1", ent);

        // when
        String responseMsg = target.path("counters").request().get(String.class);

        // then
        assertTrue(responseMsg.contains("counterc=1"));
        assertTrue(responseMsg.contains("visits=0"));
    }


    @Test
    public void shouldReturnSpecificCounter() {
        // given
        Counters.loadPersistantData();

        // when
        String responseMsg = target.path("counters/get").queryParam("key", "visits").request().get(String.class);

        // then
        assertEquals("0", responseMsg);
    }


    @Test
    public void shouldReturnInvalidMessageOnGettingCounter() {
        // given
        Counters.loadPersistantData();

        // when
        String responseMsg = target.path("counters/get").queryParam("key", "invalidname").request().get(String.class);

        // then
        assertEquals("No counter called: invalidname", responseMsg);
    }


    @Ignore
    @Test
    public void singleThreadStressTest() {
        Counters.loadPersistantData();
        int iterations = 100000;

        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            Response responseMsg = target.path("counters/increase").request().header("key", "visits").post(Entity.text("POST"));
            String ent = responseMsg.readEntity(String.class);
            assertEquals(String.valueOf(i + 1), ent);
        }

        long finish = System.currentTimeMillis();
        long timeTaken =  finish - start;
        System.out.println("Processed " + iterations + " requests in: " + timeTaken + "ms");
    }
}