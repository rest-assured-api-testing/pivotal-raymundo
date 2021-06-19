/**
 * Copyright (c) 2021 Fundacion Jala.
 *
 * This software is the confidential and proprietary information of Fundacion Jala
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Fundacion Jala
 *
 * @author Raymundo Guaraguara Sansusty
 */
package api;

import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * A class to set the fields for the API response
 */
public class ApiResponse {
    private Response response;

    public ApiResponse(Response response) {
        this.response = response;
    }

    /**
     * Gets the response
     * @return an object with the response
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Gets the status code
     * @return an int with the status code
     */
    public int getStatusCode() {
        return response.getStatusCode();
    }

    /**
     * Gets the API's response body
     * @param cls a generic class
     * @param <T> a generic object
     * @return a generic object with the body
     */
    public <T> T getBody(Class<T> cls) {
        return response.getBody().as(cls);
    }

    /**
     * Validates the schema of a request
     * @param schema a String with the schema's path
     */
    public void validateBodySchema(String schema) {
        response.then().log().body().assertThat().body(matchesJsonSchemaInClasspath(schema));
    }
}
