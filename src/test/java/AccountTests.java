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
import com.fasterxml.jackson.core.JsonProcessingException;
import entities.PivotalProject;
import entities.ReadPropertyFile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * A class to make the tests for the Accounts
 */
public class AccountTests {
    private ApiRequest apiRequest;
    private String accountId;
    private PivotalProject project = new PivotalProject();

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
    }

    /**
     * Cleans the previous values between tests
     */
    @AfterMethod
    public void clearPathParamValues() {
        apiRequest.cleanPathParam();
        apiRequest.cleanQueryParam();
    }

    /**
     * Checks if a get request on an account is ok
     */
    @Test
    public void getAccount() {
        apiRequest.setEndpoint("/accounts/{accountId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if a get request on all the accounts is ok
     */
    @Test
    public void getAllAccounts() {
        apiRequest.setEndpoint("/accounts");
        apiRequest.setMethod(ApiMethod.GET);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if the get request on the account summaries is ok
     */
    @Test
    public void getAccountSummaries() {
        apiRequest.setEndpoint("/account_summaries");
        apiRequest.setMethod(ApiMethod.GET);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if the get request on the account summaries is ok with permission set as none
     */
    @Test
    public void getAccountSummariesWithPermissionParamNone() {
        apiRequest.setEndpoint("/account_summaries");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("with_permission", "none");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if the get request on the account summaries is ok with permission set as project_creation
     */
    @Test
    public void getAccountSummariesWithPermissionParamProjectCreation() {
        apiRequest.setEndpoint("/account_summaries");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("with_permission", "project_creation");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if the get request on the account summaries is ok with permission set as administration
     */
    @Test
    public void getAccountSummariesWithPermissionParamAdministration() {
        apiRequest.setEndpoint("/account_summaries");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("with_permission", "administration");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if the get request on the account summaries is invalid with an invalid value
     */
    @Test
    public void getAccountSummariesWithInvalidPermissionParam() {
        apiRequest.setEndpoint("/account_summaries");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("with_permission", "InvalidValue");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    /**
     * Checks if the get request on the account is forbidden with an invalid token
     */
    @Test
    public void getAccountWithInvalidToken() {
        ApiRequest apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.addHeader("X-TrackerToken", "InvalidToken");
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
        accountId = readPropertyFile.getAccountId();
        apiRequest.setEndpoint("/accounts/{accountId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 403);
    }

    /**
     * Checks if the get request on the account is forbidden without a token
     */
    @Test
    public void getAccountWithOutTokenHeader() {
        ApiRequest apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
        accountId = readPropertyFile.getAccountId();
        apiRequest.setEndpoint("/accounts/{accountId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 403);
    }

    /**
     * Checks if the get request with an invalid id is not found
     */
    @Test
    public void getAccountWithInvalidId() {
        apiRequest.setEndpoint("/accounts/{accountId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", "InvalidId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    /**
     * Checks if the Account Schema is the same as the get request
     */
    @Test
    public void checkAccountSchema() {
        apiRequest.setEndpoint("/accounts/{accountId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/account.json");
    }

    /**
     * Checks if the get request on the me endpoint is ok
     */
    @Test
    public void getMe() {
        apiRequest.setEndpoint("/me");
        apiRequest.setMethod(ApiMethod.GET);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if the me schema is equal to the get request
     */
    @Test
    public void checkMeSchema() {
        apiRequest.setEndpoint("/me");
        apiRequest.setMethod(ApiMethod.GET);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/me.json");
    }

    /**
     * Creates a project for testing
     * @throws JsonProcessingException
     */
    @BeforeMethod(onlyForGroups = "project")
    public void createProjects() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        ApiResponse apiResponse = PivotalProjectsTests.createProject(pivotalProject);
        project = apiResponse.getBody(PivotalProject.class);
    }

    /**
     * Deletes the created project after the test is done
     */
    @AfterMethod(onlyForGroups = "project")
    public void deleteCreatedProject() {
        PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    /**
     * Checks the get request on the my people endpoint is ok
     */
    @Test(groups = "project")
    public void getMyPeople() {
        apiRequest.setEndpoint("/my/people");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("project_id", project.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if a get request on the my people endpoint with invalid project id is invalid
     */
    @Test
    public void getMyPeopleWithInvalidProjectId() {
        apiRequest.setEndpoint("/my/people");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("project_id", "InvalidProjectId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    /**
     * Checks if the my people Schema is the same as the get request
     */
    @Test(groups = "project")
    public void checkMyPeopleSchema() {
        apiRequest.setEndpoint("/my/people");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("project_id", project.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/mypeople.json");
    }
}
