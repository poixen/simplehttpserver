package com.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Simple {@link HttpServer} for testing GET/POST
 *
 * @author matthew.lowe
 */
public class Main {
    public static final String BASE_URI = "http://0.0.0.0:8080/"; // available to all IPs

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers in com.example package
        final ResourceConfig rc = new ResourceConfig().packages("com.example");

        // create and start a new instance of grizzly http server exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        System.out.println("[INFO] Loading Persistant Data...");
        Counters.loadPersistentData();
        System.out.println("[INFO] Persistent Data loaded.");

        System.out.println("[INFO] Creating Grizzly Server...");
        final HttpServer server = startServer();
        System.out.println("[INFO] Created Grizzly Server.");
        System.out.println("[INFO] Server is " + (server.isStarted() ? "" : "not ") + "running.");

        System.out.println(String.format("[INFO] Jersey app started with WADL available at " + "%sapplication.wadl", BASE_URI));
        System.out.println("[INFO] Hit enter to stop it...");

        System.in.read();
        server.stop();

        System.out.println("[INFO] Saving Persistent Data...");
        Counters.savePersistentData();
        System.out.println("[INFO] Persistent Data Saved.");
    }
}

