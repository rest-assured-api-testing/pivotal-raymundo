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

public class EpicsTests {
    private ApiRequest apiRequest;
    PivotalProject project = new PivotalProject();
    Epics epic = new Epics();

    @BeforeTest
    public void setTokenAndBaseUri() {
        apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.addHeader("X-TrackerToken", readPropertyFile.getToken());
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
    }

    @BeforeMethod(onlyForGroups = "getEpic")
    public void createSingleProjectAndEpic() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
        Epics newEpic = new Epics();
        newEpic.setName("My test epic");
        epic = createEpic(newEpic, project.getId().toString()).getBody(Epics.class);
    }

    @Test(groups = "getEpic")
    public void getSingleEpicOnProject() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("epicId", epic.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @AfterMethod(onlyForGroups = "getEpic")
    public void deleteCreatedProjectAndEpic() {
        ApiResponse apiResponse = deleteEpic(epic.getId().toString(), project.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    @Test(groups = "createEpic")
    public void createSingleEpicTest() throws JsonProcessingException {
        Epics epic = new Epics();
        epic.setName("My test epic");
        ApiResponse apiResponse = createEpic(epic, project.getId().toString());
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "deleteEpic")
    public void deleteSingleEpic() {
        ApiResponse apiResponse = deleteEpic("4790988", "2505903");
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

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
