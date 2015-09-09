package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * API for accessing the {@link Counters} container via GET and POST
 *
 * @author matthew.lowe
 */
@Path("counters")
public class CountersApi {

    /**
     * <b>Post method.</b><br>
     * Returns the count mapped to {@code key} increased by one.<br>
     * If no {@code key} was found then an invalid message will
     * be returned.
     *
     * @param key the name of the counter to increase
     * @return the count mapped to {@code key} increased by one,
     * or an invalid message.
     */
    @POST
    @Path("/increase")
    @Produces(MediaType.APPLICATION_JSON)
    public Response increaseCounter(@HeaderParam("key") String key) {
        if (!isValidParameter(key)) {
            return Response.serverError().entity("The counter name can not be null or empty").build();
        }

        if (Counters.get(key) != null) {
            Counters.put(key, Counters.get(key).getCount() + 1);
            return Response.ok(Integer.toString(Counters.get(key).getCount()), MediaType.APPLICATION_JSON).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("No counter called: " + key).build();
    }


    /**
     * <b>Post method.</b><br/>
     * Adds a new counter with the provided {@code key} name. <br>
     * If the provided {@code key} is already in use then a message
     * will inform you.
     *
     * @param key the name of the counter to increase
     * @return a success or failure message to add the new counter.
     */
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewCounter(@HeaderParam("key") String key) {
        if (!isValidParameter(key)) {
            return Response.status(Response.Status.FORBIDDEN).entity("The counter name can not be null or empty").build();
        }

        if (!Counters.containsKey(key)) {
            Counters.put(key, 0);
            return Response.ok("Successfully added counter name: '" + key + "'.", MediaType.APPLICATION_JSON).build();
        }

        return Response.status(Response.Status.FORBIDDEN).entity("Counter name: '" + key + "' is already in use.").build();
    }


    /**
     * Returns string values for all the counters stored
     *
     * @return string values for all the counters stored
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCounters() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(Counters.getCounterBeans());

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


    @Path("/get/{key}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCounter(@PathParam("key") String key) {
        if (!isValidParameter(key)) {
            return Response.serverError().entity("The counter name can not be null or empty").build();
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(Counters.get(key));

        if (json == null || json.trim().length() == 0 || "null".equals(json)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Could not find counter name " + key).build();
        }

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


    public static boolean isValidParameter(String value) {
        return value != null && !value.isEmpty();
    }
}
