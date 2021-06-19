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

import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import entities.ReadPropertyFile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * A class to make the tests for the Account Memberships
 */
public class AccountMembershipTests {
    private ApiRequest apiRequest;
    private String accountId;
    private String personId;

    /**
     * Sets the token and base URI for the Requests
     */
    @BeforeTest
    public void setTokenAndBaseUri() {
        apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.addHeader("X-TrackerToken", readPropertyFile.getToken());
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
        accountId = readPropertyFile.getAccountId();
        personId = readPropertyFile.getPersonId();
    }

    /**
     * Cleans the previous values between tests
     */
    @AfterMethod
    public void clearPathParamValues() {
        apiRequest.cleanPathParam();
    }

    /**
     * Checks if a get request on all the membership accounts is ok
     */
    @Test
    public void getAllMembershipsAccounts() {
        apiRequest.setEndpoint("/accounts/{accountId}/memberships");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if a get request on a single membership account is ok
     */
    @Test
    public void getSingleMembershipAccount() {
        apiRequest.setEndpoint("/accounts/{accountId}/memberships/{personId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        apiRequest.addPathParam("personId", personId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if a get request with an invalid account id is not found
     */
    @Test
    public void getSingleMembershipAccountWithInvalidAccountId() {
        apiRequest.setEndpoint("/accounts/{accountId}/memberships/{personId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", "InvalidAccountId");
        apiRequest.addPathParam("personId", personId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    /**
     * Checks if a get request with an invalid person id is not found
     */
    @Test
    public void getSingleMembershipAccountWithInvalidPersonId() {
        apiRequest.setEndpoint("/accounts/{accountId}/memberships/{personId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        apiRequest.addPathParam("personId", "InvalidPersonId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    /**
     * Checks if the Membership Account Schema is the same as the get request
     */
    @Test
    public void checkAccountMembershipSchema() {
        apiRequest.setEndpoint("/accounts/{accountId}/memberships/{personId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        apiRequest.addPathParam("personId", personId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/accountmembership.json");
    }
}
