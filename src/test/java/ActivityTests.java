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
import entities.Epics;
import entities.PivotalProject;
import entities.ReadPropertyFile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * A class to make the tests for the Activities
 */
public class ActivityTests {
    private ApiRequest apiRequest;
    private PivotalProject project;
    private Epics epic;

    /**
     * Sets the token and base URI for the Requests
     */
    @BeforeTest
    public void setTokenAndBaseUri() {
        apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.addHeader("X-TrackerToken", readPropertyFile.getToken());
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
    }

    /**
     * Cleans the previous values between tests
     */
    @AfterMethod
    public void clearPathParamValues() {
        apiRequest.cleanPathParam();
    }

    /**
     * Checks if a get request on all the activities is ok
     */
    @Test
    public void getAllActivity() {
        apiRequest.setEndpoint("/my/activity");
        apiRequest.setMethod(ApiMethod.GET);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Creates a project for testing
     * @throws JsonProcessingException
     */
    @BeforeMethod(onlyForGroups = "getProjectActivity")
    public void createSingleProject() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
    }

    /**
     * Checks if the get request on the project activity is ok
     */
    @Test(groups = "getProjectActivity")
    public void getActivityOnProject() {
        apiRequest.setEndpoint("/projects/{projectId}/activity");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if the get request on the project activity with an invalid project id is invalid
     */
    @Test
    public void getActivityOnInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/activity");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "InvalidProjectId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    /**
     * Checks if the project activity Schema is the same as the get request
     */
    @Test(groups = "getProjectActivity")
    public void checkProjectActivitySchema() {
        apiRequest.setEndpoint("/projects/{projectId}/activity");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/activity.json");
    }

    /**
     * Deletes the created project after the test is done
     */
    @AfterMethod(onlyForGroups = "getProjectActivity")
    public void deleteCreatedProject() {
        ApiResponse apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    /**
     * Creates a project and an epic for testing
     * @throws JsonProcessingException
     */
    @BeforeMethod(onlyForGroups = "getEpicActivity")
    public void createSingleProjectAndEpic() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
        Epics newEpic = new Epics();
        newEpic.setName("My test epic");
        epic = EpicsTests.createEpic(newEpic, project.getId().toString()).getBody(Epics.class);
    }

    /**
     * Checks if the get request on the epic activity is ok
     */
    @Test(groups = "getEpicActivity")
    public void getActivityOnEpic() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}/activity");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("epicId", epic.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if the get request on the epic activity with an invalid epic is invalid
     */
    @Test(groups = "getEpicActivity")
    public void getActivityWithInvalidEpicId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}/activity");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("epicId", "InvalidEpicId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    /**
     * Deletes the project and epic created for the test after its done
     */
    @AfterMethod(onlyForGroups = "getEpicActivity")
    public void deleteCreatedProjectAndEpic() {
        ApiResponse apiResponse = EpicsTests.deleteEpic(epic.getId().toString(), project.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }
}
