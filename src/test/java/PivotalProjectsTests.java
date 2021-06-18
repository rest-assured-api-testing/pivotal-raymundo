import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import entities.PivotalProject;
import entities.ReadPropertyFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PivotalProjectsTests {
    private ApiRequest apiRequest;
    PivotalProject project = new PivotalProject();

    @BeforeTest
    public void setTokenAndBaseUri() {
        apiRequest = new ApiRequest();
        apiRequest = configureTokenAndBaseUri(apiRequest);
    }

    @BeforeMethod(onlyForGroups = "getProject")
    public void createProjects() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        apiRequest.setEndpoint("/projects");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(pivotalProject));
        project = ApiManager.executeWithBody(apiRequest).getBody(PivotalProject.class);
    }

    @Test(groups = "getProject")
    public void getSingleProjectTest() {
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @AfterMethod(onlyForGroups = "getProject")
    public void deleteCreatedProject() {
        deleteProject(project.getId().toString());
    }

    @Test(groups = "createProject")
    public void createSingleProjectTest() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My test project");
        ApiResponse apiResponse = createProject(pivotalProject);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "deleteProject")
    public void deleteSingleProjectTest() {
        ApiResponse apiResponse = deleteProject("2505916");
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    public static ApiRequest configureTokenAndBaseUri(ApiRequest apiRequest) {
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.addHeader("X-TrackerToken", readPropertyFile.getToken());
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
        return apiRequest;
    }

    public static ApiResponse createProject(PivotalProject pivotalProject) throws JsonProcessingException {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest = configureTokenAndBaseUri(apiRequest);
        apiRequest.setEndpoint("/projects");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(pivotalProject));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        return apiResponse;
    }

    public static ApiResponse deleteProject(String projectId) {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest = configureTokenAndBaseUri(apiRequest);
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", projectId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        return  apiResponse;
    }
}
