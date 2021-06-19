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

public class ActivityTests {
    private ApiRequest apiRequest;
    private PivotalProject project;
    private Epics epic;

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
    }

    @Test
    public void getAllActivity() {
        apiRequest.setEndpoint("/my/activity");
        apiRequest.setMethod(ApiMethod.GET);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @BeforeMethod(onlyForGroups = "getProjectActivity")
    public void createSingleProject() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
    }

    @Test(groups = "getProjectActivity")
    public void getActivityOnProject() {
        apiRequest.setEndpoint("/projects/{projectId}/activity");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getActivityOnInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/activity");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "InvalidProjectId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test(groups = "getProjectActivity")
    public void checkProjectActivitySchema() {
        apiRequest.setEndpoint("/projects/{projectId}/activity");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/activity.json");
    }

    @AfterMethod(onlyForGroups = "getProjectActivity")
    public void deleteCreatedProject() {
        ApiResponse apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    @BeforeMethod(onlyForGroups = "getEpicActivity")
    public void createSingleProjectAndEpic() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
        Epics newEpic = new Epics();
        newEpic.setName("My test epic");
        epic = EpicsTests.createEpic(newEpic, project.getId().toString()).getBody(Epics.class);
    }

    @Test(groups = "getEpicActivity")
    public void getActivityOnEpic() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}/activity");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("epicId", epic.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getEpicActivity")
    public void getActivityWithInvalidEpicId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}/activity");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("epicId", "InvalidEpicId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @AfterMethod(onlyForGroups = "getEpicActivity")
    public void deleteCreatedProjectAndEpic() {
        ApiResponse apiResponse = EpicsTests.deleteEpic(epic.getId().toString(), project.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }
}
