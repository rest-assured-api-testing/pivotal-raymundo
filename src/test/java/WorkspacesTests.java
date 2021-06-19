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

    @AfterMethod
    public void clearPathParamValues() {
        apiRequest.cleanPathParam();
        apiRequest.cleanQueryParam();
    }

    @BeforeMethod(onlyForGroups = "getWorkspace")
    public void createTwoProjects() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        PivotalProject secondPivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        secondPivotalProject.setName("My Second Test Project");
        firstProject = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
        secondProject = PivotalProjectsTests.createProject(secondPivotalProject).getBody(PivotalProject.class);
        Workspaces workspaces = new Workspaces();
        workspaces.setName("My test workspace");
        workspaces.addProject_id(firstProject.getId());
        workspaces.addProject_id(secondProject.getId());
        ApiResponse apiResponse = createWorkspace(workspaces);
        myWorkspace = apiResponse.getBody(Workspaces.class);
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

    @Test(groups = "getWorkspace")
    public void getSingleWorkspaceWithInvalidWorkspaceId() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("workspaceId", "InvalidId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test(groups = "getWorkspace")
    public void createSingleWorkspaceWithEmptyName() throws JsonProcessingException {
        Workspaces workspaces = new Workspaces();
        workspaces.setName("");
        workspaces.addProject_id(firstProject.getId());
        workspaces.addProject_id(secondProject.getId());
        ApiResponse apiResponse = createWorkspace(workspaces);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test(groups = "getWorkspace")
    public void createSingleWorkspaceWithNullName() throws JsonProcessingException {
        Workspaces workspaces = new Workspaces();
        workspaces.setName(null);
        workspaces.addProject_id(firstProject.getId());
        workspaces.addProject_id(secondProject.getId());
        ApiResponse apiResponse = createWorkspace(workspaces);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test(groups = "getWorkspace")
    public void createSingleWorkspaceWithRepeatedName() throws JsonProcessingException {
        Workspaces workspaces = new Workspaces();
        workspaces.setName("My test workspace");
        workspaces.addProject_id(firstProject.getId());
        workspaces.addProject_id(secondProject.getId());
        ApiResponse apiResponse = createWorkspace(workspaces);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test(groups = "emptyWorkspace")
    public void createSingleWorkspaceWithoutProjects() throws JsonProcessingException {
        Workspaces workspaces = new Workspaces();
        workspaces.setName("My test workspace");
        ApiResponse apiResponse = createWorkspace(workspaces);
        myWorkspace = apiResponse.getBody(Workspaces.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @AfterMethod(onlyForGroups = "emptyWorkspace")
    public void deleteWorkspaceGroupEmptyWorkspace() {
        ApiResponse apiResponse = deleteWorkspace(myWorkspace.getId().toString());
    }

    @Test(groups = "getWorkspace")
    public void checkWorkspaceSchema() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("workspaceId", myWorkspace.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/workspace.json");
    }

    @BeforeMethod(onlyForGroups = "createWorkspace")
    public void createProject() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My First Test Project");
        PivotalProject secondPivotalProject = new PivotalProject();
        secondPivotalProject.setName("My Second Test Project");
        firstProject = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
        secondProject = PivotalProjectsTests.createProject(secondPivotalProject).getBody(PivotalProject.class);
    }

    @Test(groups = "createWorkspace")
    public void createWorkspace() throws JsonProcessingException {
        Workspaces workspaces = new Workspaces();
        workspaces.setName("My workspace test");
        workspaces.addProject_id(firstProject.getId());
        workspaces.addProject_id(secondProject.getId());
        ApiResponse apiResponse = createWorkspace(workspaces);
        myWorkspace = apiResponse.getBody(Workspaces.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @AfterMethod(onlyForGroups = "createWorkspace")
    public void deleteCreatedProjectAndWorkspaceOnGroupCreateEpic() {
        ApiResponse apiResponse = deleteWorkspace(myWorkspace.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(firstProject.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(secondProject.getId().toString());
    }

    @AfterMethod(onlyForGroups = "getWorkspace")
    public void deleteCreatedWorkspacesAndProjects() {
        ApiResponse apiResponse = deleteWorkspace(myWorkspace.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(firstProject.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(secondProject.getId().toString());
    }

    @BeforeMethod(onlyForGroups = "deleteWorkspace")
    public void createTwoProjectsAndWorkspaceGroupDeleteWorkspace() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        PivotalProject secondPivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        secondPivotalProject.setName("My Second Test Project");
        firstProject = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
        secondProject = PivotalProjectsTests.createProject(secondPivotalProject).getBody(PivotalProject.class);
        Workspaces workspaces = new Workspaces();
        workspaces.setName("My test workspace");
        workspaces.addProject_id(firstProject.getId());
        workspaces.addProject_id(secondProject.getId());
        ApiResponse apiResponse = createWorkspace(workspaces);
        myWorkspace = apiResponse.getBody(Workspaces.class);
    }

    @Test(groups = "deleteWorkspace")
    public void deleteSingleWorkspace() {
        ApiResponse apiResponse = deleteWorkspace(myWorkspace.getId().toString());
    }

    @AfterMethod(onlyForGroups = "deleteWorkspace")
    public void deleteCreatedWorkspacesAndProjectsGroupDeleteWorkspace() {
        ApiResponse apiResponse = PivotalProjectsTests.deleteProject(firstProject.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(secondProject.getId().toString());
    }

    public static ApiResponse createWorkspace(Workspaces workspace) throws JsonProcessingException {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest = PivotalProjectsTests.configureTokenAndBaseUri(apiRequest);
        apiRequest.setEndpoint("/my/workspaces");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(workspace));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        return apiResponse;
    }

    public static ApiResponse deleteWorkspace(String workspaceId) {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest = PivotalProjectsTests.configureTokenAndBaseUri(apiRequest);
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("workspaceId", workspaceId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        return  apiResponse;
    }
}
