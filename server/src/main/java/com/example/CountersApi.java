package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
    @Produces(MediaType.TEXT_PLAIN)
    public String increaseCounter(@HeaderParam("key") String key) {
        if (!isValidParameter(key)) {
            return "Key can not be null or empty";
        }

        if (Counters.get(key) != null) {
            Counters.put(key, Counters.get(key).getCount() + 1);
            return Integer.toString(Counters.get(key).getCount());
        }

        return "No counter called: " + key;
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
    @Produces(MediaType.TEXT_PLAIN)
    public String addNewCounter(@HeaderParam("key") String key) {
        if (!isValidParameter(key)) {
            return "Parameter can not be null or empty";
        }

        if (!Counters.containsKey(key)) {
            Counters.put(key, 0);
            return "Successfully added counter name: '" + key + "'.";
        }

        return "Counter name: '" + key + "' is already in use.";
    }


    /**
     * Returns string values for all the counters stored
     *
     * @return string values for all the counters stored
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCounters() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(Counters.getCounterBeans());
    }


    @Path("/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCounter(@QueryParam("key") String key) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(Counters.get(key));
    }


    public static boolean isValidParameter(String value) {
        return value != null && !value.isEmpty();
    }
}
