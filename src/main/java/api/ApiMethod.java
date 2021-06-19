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

/**
 * An enum class to specify the API's methods
 */
public enum ApiMethod {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE");
    private String name;

    ApiMethod(final String name) {
        this.name = name;
    }

    /**
     * Gets the name
     * @return a String with the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * @param name a String with the name
     */
    public void setName(final String name) {
        this.name = name;
    }
}
