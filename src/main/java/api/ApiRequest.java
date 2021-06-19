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

import io.restassured.http.Header;
import io.restassured.http.Headers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to set the fields for the API request
 */
public class ApiRequest {
    private String baseUri;
    private String endpoint;
    private String body;
    private Enum<ApiMethod> method;
    private List<Header> headers;
    private Map<String, String> queryParams;
    private Map<String, String> pathParams;

    public ApiRequest() {
        headers = new ArrayList<>();
        queryParams = new HashMap<>();
        pathParams = new HashMap<>();
    }

    /**
     * Gets the Base URI
     * @return a String with the BASE URI
     */
    public String getBaseUri() {
        return baseUri;
    }

    /**
     * Sets the Base Uri
     * @param baseUri a String with the BASE URI
     */
    public void setBaseUri(final String baseUri) {
        this.baseUri = baseUri;
    }

    /**
     * Gets the Endpoint
     * @return a String with the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Sets the Endpoint
     * @param endpoint a String with the endpoint
     */
    public void setEndpoint(final String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Gets the body
     * @return a String with the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body
     * @param body a String with the body
     */
    public void setBody(final String body) {
        this.body = body;
    }

    /**
     * Gets the API's Method
     * @return an Enum with the API's method
     */
    public Enum<ApiMethod> getMethod() {
        return method;
    }

    /**
     * Sets the API's method
     * @param method a Enum with the API's method
     */
    public void setMethod(final Enum<ApiMethod> method) {
        this.method = method;
    }

    /**
     * Gets the header list
     * @return a List with the headers
     */
    public Headers getHeaders() {
        return new Headers(headers);
    }

    /**
     * Adds a Header to the header list
     * @param header a String with the header's name
     * @param value a String with the header's value
     */
    public void addHeader(final String header, final String value) {
        headers.add(new Header(header, value));
    }

    /**
     * Gets the QueryParam map
     * @return a Map with the Query parameters
     */
    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    /**
     * Adds a value to the QueryParam map
     * @param param a String with the key
     * @param value a String with the value
     */
    public void addQueryParam(final String param, final String value) {
        queryParams.put(param, value);
    }

    /**
     * Gets the PathParam map
     * @return a Map with the Path parameters
     */
    public Map<String, String> getPathParams() {
        return pathParams;
    }

    /**
     * Adds a value to the PathParam map
     * @param param a String with the key
     * @param value a String with the value
     */
    public void addPathParam(final String param, final String value) {
        pathParams.put(param, value);
    }

    /**
     * Clear the PathParam map's values
     */
    public void cleanPathParam() {
        pathParams.clear();
    }

    /**
     * Clear the QueryParam map's values
     */
    public void cleanQueryParam() {
        queryParams.clear();
    }
}
