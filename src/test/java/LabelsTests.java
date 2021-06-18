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

    @AfterMethod(onlyForGroups = "getLabel")
    public void deleteCreatedProjectAndLabel() {
        ApiResponse apiResponse = deleteLabel(label.getId().toString(), project.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    @Test(groups = "createLabel")
    public void createSingleLabelTest() throws JsonProcessingException {
        Labels label = new Labels();
        label.setName("My test epic");
        ApiResponse apiResponse = createLabel(label, project.getId().toString());
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "deleteLabel")
    public void deleteSingleLabel() {
        ApiResponse apiResponse = deleteLabel("4790988", "2505903");
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

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
