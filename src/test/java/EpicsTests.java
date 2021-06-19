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
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Epics;
import entities.PivotalProject;
import entities.ReadPropertyFile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * A class to make the tests for the Epics
 */
public class EpicsTests {
    private ApiRequest apiRequest;
    PivotalProject project = new PivotalProject();
    Epics epic = new Epics();

    /**
     * Cleans the previous values between tests
     */
    @AfterMethod
    public void clearPathParamValues() {
        apiRequest.cleanPathParam();
    }

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
     * Creates a project and an epic for testing
     * @throws JsonProcessingException
     */
    @BeforeMethod(onlyForGroups = "getEpic")
    public void createSingleProjectAndEpic() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
        Epics newEpic = new Epics();
        newEpic.setName("My test epic");
        epic = createEpic(newEpic, project.getId().toString()).getBody(Epics.class);
    }

    /**
     * Checks if a get request on a single epic is ok
     */
    @Test(groups = "getEpic")
    public void getSingleEpicOnProject() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("epicId", epic.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Checks if a get request on a single epic with invalid epic id is invalid
     */
    @Test(groups = "getEpic")
    public void getSingleEpicOnProjectWithInvalidEpicId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("epicId", "InvalidEpicId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    /**
     * Checks if a get request on a single epic with invalid project id is not found
     */
    @Test(groups = "getEpic")
    public void getSingleEpicOnProjectWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "InvalidProjectId");
        apiRequest.addPathParam("epicId", epic.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    /**
     * Deletes the created project and epic for the test after its done
     */
    @AfterMethod(onlyForGroups = "getEpic")
    public void deleteCreatedProjectAndEpic() {
        ApiResponse apiResponse = deleteEpic(epic.getId().toString(), project.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    /**
     * Creates a project for testing
     * @throws JsonProcessingException
     */
    @BeforeMethod(onlyForGroups = "createEpic")
    public void createProject() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
    }

    /**
     * Checks if a post request to create an epic is ok
     * @throws JsonProcessingException
     */
    @Test(groups = "createEpic")
    public void createSingleEpicTest() throws JsonProcessingException {
        Epics newEpic = new Epics();
        newEpic.setName("My test epic");
        ApiResponse apiResponse = createEpic(newEpic, project.getId().toString());
        epic = apiResponse.getBody(Epics.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    /**
     * Creates a project for testing
     * @throws JsonProcessingException
     */
    @BeforeMethod(onlyForGroups = "onlyProject")
    public void createProjectForOnlyProjectGroup() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
    }

    /**
     * Checks if a post request is invalid when providing empty epic name
     * @throws JsonProcessingException
     */
    @Test(groups = "onlyProject")
    public void createSingleEpicWithEmptyTittle() throws JsonProcessingException {
        Epics newEpic = new Epics();
        newEpic.setName("");
        ApiResponse apiResponse = createEpic(newEpic, project.getId().toString());
        epic = apiResponse.getBody(Epics.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    /**
     * Checks if a post request is invalid when providing null epic name
     * @throws JsonProcessingException
     */
    @Test(groups = "onlyProject")
    public void createSingleEpicWithNullTittle() throws JsonProcessingException {
        Epics newEpic = new Epics();
        newEpic.setName(null);
        ApiResponse apiResponse = createEpic(newEpic, project.getId().toString());
        epic = apiResponse.getBody(Epics.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    /**
     * Deletes created project after test is done
     */
    @AfterMethod(onlyForGroups = "onlyProject")
    public void deleteCreatedProjectOnGroupOnlyProject() {
        ApiResponse apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    /**
     * Deletes created project and epic after test is done
     */
    @AfterMethod(onlyForGroups = "createEpic")
    public void deleteCreatedProjectAndEpicOnGroupCreateEpic() {
        ApiResponse apiResponse = deleteEpic(epic.getId().toString(), project.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    /**
     * Creates a project and an epic for testing
     * @throws JsonProcessingException
     */
    @BeforeMethod(onlyForGroups = "deleteEpic")
    public void createProjectAndEpicOnGroupDeleteEpic() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
        Epics newEpic = new Epics();
        newEpic.setName("My test epic");
        epic = createEpic(newEpic, project.getId().toString()).getBody(Epics.class);
    }

    /**
     * Checks if a delete request on an epic is ok
     */
    @Test(groups = "deleteEpic")
    public void deleteSingleEpic() {
        ApiResponse apiResponse = deleteEpic(epic.getId().toString(), project.getId().toString());
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    /**
     * Deletes the created project after the test is done
     */
    @AfterMethod(onlyForGroups = "deleteEpic")
    public void deleteCreatedProjectAndEpicOnGroupDeleteEpic() {
        ApiResponse apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    /**
     * Checks if the Epic Schema is the same as the get request
     */
    @Test(groups = "getEpic")
    public void checkEpicSchema() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("epicId", epic.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/epic.json");
    }

    /**
     * Makes the post request to create an epic
     * @param epic an object with the epic params
     * @param projectId a String with the project's id
     * @return the API's response
     * @throws JsonProcessingException
     */
    public static ApiResponse createEpic(Epics epic, String projectId) throws JsonProcessingException {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest = PivotalProjectsTests.configureTokenAndBaseUri(apiRequest);
        apiRequest.setEndpoint("/projects/{projectId}/epics");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", projectId);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(epic));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        return apiResponse;
    }

    /**
     * Makes the delete request to delete an epic
     * @param epicId a String with the epic's id
     * @param projectId a String with the project's id
     * @return the API's response
     */
    public static ApiResponse deleteEpic(String epicId, String projectId) {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest = PivotalProjectsTests.configureTokenAndBaseUri(apiRequest);
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", projectId);
        apiRequest.addPathParam("epicId", epicId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        return  apiResponse;
    }
}
