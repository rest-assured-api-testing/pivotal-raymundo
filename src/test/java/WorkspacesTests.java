import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.PivotalProject;
import entities.ReadPropertyFile;
import entities.Workspaces;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WorkspacesTests {
    private ApiRequest apiRequest;
    PivotalProject firstProject = new PivotalProject();
    PivotalProject secondProject = new PivotalProject();
    Workspaces myWorkspace = new Workspaces();

    @BeforeTest
    public void setTokenAndBaseUri() {
        apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.addHeader("X-TrackerToken", readPropertyFile.getToken());
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
    }

    @BeforeMethod(onlyForGroups = "getWorkspace")
    public void createTwoProjects() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        PivotalProject secondPivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        secondPivotalProject.setName("My Second Test Project");
        apiRequest.setEndpoint("/projects");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(pivotalProject));
        firstProject = ApiManager.executeWithBody(apiRequest).getBody(PivotalProject.class);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(secondPivotalProject));
        secondProject = ApiManager.executeWithBody(apiRequest).getBody(PivotalProject.class);
        apiRequest.setEndpoint("/my/workspaces");
        apiRequest.setMethod(ApiMethod.POST);
        Workspaces workspaces = new Workspaces();
        workspaces.setName("My test workspace");
        workspaces.addProject_id(firstProject.getId());
        workspaces.addProject_id(secondProject.getId());
        apiRequest.setBody(new ObjectMapper().writeValueAsString(workspaces));
        myWorkspace = ApiManager.executeWithBody(apiRequest).getBody(Workspaces.class);
    }

    @Test(groups = "getWorkspace")
    public void getAllWorkspaces() {
        apiRequest.setEndpoint("/my/workspaces");
        apiRequest.setMethod(ApiMethod.GET);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getWorkspace")
    public void getSingleWorkspace() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("workspaceId", myWorkspace.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createWorkspace() throws JsonProcessingException {
        Workspaces workspaces = new Workspaces();
        workspaces.setName("My workspace please");
//        workspaces.setProject_ids([2505719L,2505720L]);
        workspaces.addProject_id(2505719L);
        workspaces.addProject_id(2505720L);
        apiRequest.setEndpoint("/my/workspaces");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(workspaces));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @AfterMethod(onlyForGroups = "getWorkspace")
    public void deleteCreatedWorkspacesAndProjects() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("workspaceId", myWorkspace.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse = PivotalProjectsTests.deleteProject(firstProject.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(secondProject.getId().toString());
    }

    @Test
    public void deleteWorkspace() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("workspaceId", "876203");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }
}
