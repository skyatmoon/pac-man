package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.adapter.DispatchAdapter;
import edu.rice.comp504.model.response.ResponseGhosts;
import edu.rice.comp504.model.response.ResponseMap;
import edu.rice.comp504.model.response.ResponsePacMan;

import static spark.Spark.*;

public class PacManWorldController {

    private static final int DEFAULT_PORT = 4567;
    private static final Gson GSON = new Gson();
    private static final DispatchAdapter DISPATCH_ADAPTER = new DispatchAdapter();

    /**
     * The main entry point into the program.
     *
     */
    public static void main(String[] args) {
        System.out.println(args);
        initializeServer();
        setupEndpoints();
    }

    /**
     * Initialize the server.
     */
    private static void initializeServer() {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());
    }

    /**
     * Set up the endpoints for the server.
     */
    private static void setupEndpoints() {

        post("/update", (request, response) -> {
            ResponseMap map = DISPATCH_ADAPTER.update();
            response.type("application/json");
            return GSON.toJson(map);
        });

        post("/pacman", (request, response) -> {
            String operation = request.queryParams("direction");
            ResponsePacMan responsePacMan = DISPATCH_ADAPTER.updatePacMan(operation);
            response.type("application/json");
            return GSON.toJson(responsePacMan);
        });


        post("/ghost", (request, response) -> {
//            System.out.println(test);
            ResponseGhosts ghosts = DISPATCH_ADAPTER.updateGhosts();
            response.type("application/json");
//            test--;
//            if (test > 0) {
//                return GSON.toJson(ghosts);
//            } else {
//                return null;
//            }
            return GSON.toJson(ghosts);
        });



        post("/set", (request, response) -> {
            int points = Integer.parseInt(request.queryParams("pointNum"));
            int ghosts = Integer.parseInt(request.queryParams("ghostNum"));
            int life = Integer.parseInt(request.queryParams("lifeNum"));
            DISPATCH_ADAPTER.setSettings(points, ghosts, life);

            response.type("application/json");
            return GSON.toJson("Already saved sttings.");
        });

        get("/level", (request, response) -> {
            response.type("application/json");
            int level = Integer.parseInt(request.queryParams("level"));
            DISPATCH_ADAPTER.setLevel(level);
            return GSON.toJson(level);
        });

        get("/clear", (request, response) -> {
            response.type("application/json");
            DISPATCH_ADAPTER.init();
            return GSON.toJson("Already clear.");
        });

        get("*", (request, response) -> handle404Error(response));
    }

    /**
     * Handle 404 error.
     *
     * @param response the response
     * @return the error message
     */
    private static String handle404Error(spark.Response response) {
        response.status(404);
        return "<html>" +
                "<head><title>404 Not Found</title></head>" +
                "<body>" +
                "<h1>404 - You are try an unsupported option</h1>" +
                "<p>The object option you're looking for is not support now.</p>" +
                "<button onclick=\"window.location.href='/'\">Back to Home</button>" +
                "</body>" +
                "</html>";
    }

    /**
     * Get the port assigned by Heroku.
     *
     * @return the port
     */
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return DEFAULT_PORT;
    }

}