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

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

/**
 * A class to manage the API's methods
 */
public class ApiManager {
    /**
     * Builds the API request
     * @param apiRequest an Object with all the API fields
     * @return a request specification with previous information
     */
    private static RequestSpecification buildRequest(final ApiRequest apiRequest)
    {
        return given().headers(apiRequest.getHeaders())
                .queryParams(apiRequest.getQueryParams())
                .pathParams(apiRequest.getPathParams())
                .baseUri(apiRequest.getBaseUri())
                .contentType(ContentType.JSON)
                .log().all();
    }

    /**
     * Executes the API request
     * @param apiRequest an Object with all the API fields
     * @return the API response
     */
    public static ApiResponse execute(final ApiRequest apiRequest){
        Response response = buildRequest(apiRequest)
                .request(apiRequest.getMethod().name()
                        ,apiRequest.getEndpoint());
        return new ApiResponse(response);
    }

    /**
     * Executes the API request with a body
     * @param apiRequest an Object with all the API fields
     * @return the API response
     */
    public static ApiResponse executeWithBody(final ApiRequest apiRequest){
        Response response = buildRequest(apiRequest)
                .body(apiRequest.getBody())
                .request(apiRequest.getMethod().name()
                        ,apiRequest.getEndpoint());
        return new ApiResponse(response);
    }
}
