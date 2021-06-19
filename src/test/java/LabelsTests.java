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
import entities.Labels;
import entities.PivotalProject;
import entities.ReadPropertyFile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * A class to make the tests for the Labels
 */
public class LabelsTests {
    private ApiRequest apiRequest;
    PivotalProject project = new PivotalProject();
    Labels label = new Labels();

    @BeforeTest
    public void setTokenAndBaseUri() {
        apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.addHeader("X-TrackerToken", readPropertyFile.getToken());
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
    }

    @AfterMethod
    public void clearPathParamValues() {
        apiRequest.cleanPathParam();
        apiRequest.cleanQueryParam();
    }

    @BeforeMethod(onlyForGroups = "getLabel")
    public void createSingleProjectAndLabel() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
        Labels newLabel = new Labels();
        newLabel.setName("My test Label");
        label = createLabel(newLabel, project.getId().toString()).getBody(Labels.class);
    }

    @Test(groups = "getLabel")
    public void getSingleLabelOnProject() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("labelId", label.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getLabel")
    public void getSingleLabelOnProjectWithEpicType() {
        apiRequest.setEndpoint("/projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addQueryParam("with_type", "epic");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getLabel")
    public void getSingleLabelOnProjectWithStandardType() {
        apiRequest.setEndpoint("/projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addQueryParam("with_type", "standard");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getLabel")
    public void getSingleLabelOnProjectWithInvalidType() {
        apiRequest.setEndpoint("/projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addQueryParam("with_type", "InvalidValue");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test(groups = "getLabel")
    public void getSingleLabelOnProjectWithActiveStatus() {
        apiRequest.setEndpoint("/projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addQueryParam("with_status", "active");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getLabel")
    public void getSingleLabelOnProjectWithInactiveStatus() {
        apiRequest.setEndpoint("/projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addQueryParam("with_status", "inactive");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getLabel")
    public void getSingleLabelOnProjectWithInvalidStatus() {
        apiRequest.setEndpoint("/projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addQueryParam("with_status", "InvalidValue");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test(groups = "getLabel")
    public void getSingleLabelOnProjectWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "InvalidProjectId");
        apiRequest.addPathParam("labelId", label.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test(groups = "getLabel")
    public void getSingleLabelOnProjectWithInvalidLabelId() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("labelId", "InvalidLabelId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test(groups = "getLabel")
    public void checkLabelSchema() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("labelId", label.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/label.json");
    }

    @AfterMethod(onlyForGroups = "getLabel")
    public void deleteCreatedProjectAndLabel() {
        ApiResponse apiResponse = deleteLabel(label.getId().toString(), project.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    @BeforeMethod(onlyForGroups = "createLabel")
    public void createProject() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
    }

    @Test(groups = "createLabel")
    public void createSingleLabelTest() throws JsonProcessingException {
        Labels newLabel = new Labels();
        newLabel.setName("My test label");
        ApiResponse apiResponse = createLabel(newLabel, project.getId().toString());
        label = apiResponse.getBody(Labels.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @AfterMethod(onlyForGroups = "createLabel")
    public void deleteCreatedProjectAndEpicOnGroupCreateEpic() {
        ApiResponse apiResponse = deleteLabel(label.getId().toString(), project.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    @BeforeMethod(onlyForGroups = "deleteLabel")
    public void createProjectAndLabelOnGroupDeleteLabel() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
        Labels newLabel = new Labels();
        newLabel.setName("My test label");
        label = createLabel(newLabel, project.getId().toString()).getBody(Labels.class);
    }

    @Test(groups = "deleteLabel")
    public void deleteSingleLabel() {
        ApiResponse apiResponse = deleteLabel(label.getId().toString(), project.getId().toString());
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "deleteLabel")
    public void createLabelWithEmptyName() throws JsonProcessingException {
        Labels newLabel = new Labels();
        newLabel.setName("");
        ApiResponse apiResponse = createLabel(newLabel, project.getId().toString());
        label = apiResponse.getBody(Labels.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test(groups = "deleteLabel")
    public void createLabelWithNullName() throws JsonProcessingException {
        Labels newLabel = new Labels();
        newLabel.setName(null);
        ApiResponse apiResponse = createLabel(newLabel, project.getId().toString());
        label = apiResponse.getBody(Labels.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test(groups = "deleteLabel")
    public void createLabelWithRepeatedName() throws JsonProcessingException {
        Labels newLabel = new Labels();
        newLabel.setName("My test label");
        ApiResponse apiResponse = createLabel(newLabel, project.getId().toString());
        label = apiResponse.getBody(Labels.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test(groups = "deleteLabel")
    public void createLabelWithNameLongerThan255Characters() throws JsonProcessingException {
        Labels newLabel = new Labels();
        newLabel.setName("My test labels example to test more than 255 characters. Apart from counting words and " +
                "characters, our online editor can help you to improve word choice and writing style, and, " +
                "optionally, help you to detect grammar mistakes and plagiarism.  To check words.");
        ApiResponse apiResponse = createLabel(newLabel, project.getId().toString());
        label = apiResponse.getBody(Labels.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @AfterMethod(onlyForGroups = "deleteLabel")
    public void deleteCreatedProjectAndLabelOnGroupDeleteLabel() {
        ApiResponse apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    /**
     * Makes the post request to create a label
     * @param label an object with the label's fields
     * @param projectId a String with the project's id
     * @return the API's response
     * @throws JsonProcessingException
     */
    public static ApiResponse createLabel(Labels label, String projectId) throws JsonProcessingException {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest = PivotalProjectsTests.configureTokenAndBaseUri(apiRequest);
        apiRequest.setEndpoint("/projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", projectId);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(label));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        return apiResponse;
    }

    /**
     * Makes the delete request to delete a label
     * @param labelId a String with the label's id
     * @param projectId a String with the project's id
     * @return the API's response
     */
    public static ApiResponse deleteLabel(String labelId, String projectId) {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest = PivotalProjectsTests.configureTokenAndBaseUri(apiRequest);
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", projectId);
        apiRequest.addPathParam("labelId", labelId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        return  apiResponse;
    }
}
