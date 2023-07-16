package com.frolf;

//added all imports from the admin app.java becuase i dont really know what i need yet
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.IOException;
//import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.Hashtable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileInputStream;

// Import the Spark package, so that we can make use of the "get" function to 
// create an HTTP GET route
import spark.Spark;

// Import Google's JSON library
import com.google.gson.*;

/**
 * For now, our app creates an HTTP server that can only get and add data.
 */
public class Server {
    /**
     * Get an integer environment varible if it exists, and otherwise return the
     * default value.
     * 
     * @envar The name of the environment variable to get.
     * @defaultVal The integer value to use as the default if envar isn't found
     * 
     * @returns The best answer we could come up with for a value for envar
     */
   
    public static void main(String[] args) {
        // get the Postgres configuration from the environment
        Map<String, String> env = System.getenv();

        // Get the port on which to listen for requests
        Spark.port(getIntFromEnv("PORT", 4567));

        // Set up the location for serving static files. If the STATIC_LOCATION
        // environment variable is set, we will serve from it. Otherwise, serve
        // from "/web"
        String static_location_override = System.getenv("STATIC_LOCATION");
        if (static_location_override == null) {
            Spark.staticFileLocation("/web");
        } else {
            Spark.staticFiles.externalLocation(static_location_override);
        }

        String cors_enabled = env.get("CORS_ENABLED");

        if ("True".equalsIgnoreCase(cors_enabled)) {
            final String acceptCrossOriginRequestsFrom = "*";
            final String acceptedCrossOriginRoutes = "GET,PUT,POST,DELETE,OPTIONS";
            final String supportedRequestHeaders = "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin";
            //enableCORS(acceptCrossOriginRequestsFrom, acceptedCrossOriginRoutes, supportedRequestHeaders);
        }

        /*try{
            Credentials creds = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\markZ\\Documents\\CSE216\\slackersv2\\backend\\src\\main\\resources\\google\\primal-prism-368817-6655162b2a41.json"));
            Storage storage = StorageOptions.newBuilder().setCredentials(creds).setProjectId("primal-prism-368817").build().getService();

            Blob blob = storage.get(BlobId.of("theslackersbucket", "prof.jpg"));
            blob.downloadTo(Paths.get(".\\prof.jpg"));
        }catch(Exception e){
            System.out.println("caught this error, "+ e.getMessage());
        }*/

        // gson provides us with a way to turn JSON into objects, and objects into JSON.
        final Gson gson = new Gson();


        final Hashtable<String, String> userTable = new Hashtable<String, String>();

        // get all ideas
        Spark.get("/ideas", (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            return gson.toJson("{\"Hello\": \"world\"}");//new StructuredResponse("ok", null, db.selectAllIdeas())); // dataStore.readAll
        });

    }

    static int getIntFromEnv(String envar, int defaultVal) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get(envar) != null) {
            return Integer.parseInt(processBuilder.environment().get(envar));
        }
        return defaultVal;
    }

}
