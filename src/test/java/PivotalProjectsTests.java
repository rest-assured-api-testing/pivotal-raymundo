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

    @Test(groups = "getProject")
    public void createProjectWithRepeatedTittle() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        ApiResponse apiResponse = createProject(pivotalProject);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
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
        project = apiResponse.getBody(PivotalProject.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @AfterMethod(onlyForGroups = "createProject")
    public void deleteCreatedProjectForGroupCreateProject() {
        deleteProject(project.getId().toString());
    }

    @Test
    public void createSingleProjectWithEmptyName() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("");
        ApiResponse apiResponse = createProject(pivotalProject);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createSingleProjectWithNullName() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName(null);
        ApiResponse apiResponse = createProject(pivotalProject);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createSingleProjectWithIterationLengthAsNegative() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My test project");
        pivotalProject.setIteration_length(-1);
        ApiResponse apiResponse = createProject(pivotalProject);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createSingleProjectWithNumberOfDoneIterationsToShowAsNegative() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My test project");
        pivotalProject.setNumber_of_done_iterations_to_show(-1);
        ApiResponse apiResponse = createProject(pivotalProject);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createSingleProjectWithInvalidWeekStartDay() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My test project");
        pivotalProject.setWeek_start_day("Mon");
        ApiResponse apiResponse = createProject(pivotalProject);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createSingleProjectWithVelocityAveragedOverAsNegative() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My test project");
        pivotalProject.setVelocity_averaged_over(-1);
        ApiResponse apiResponse = createProject(pivotalProject);
        Assert.assertEquals(apiResponse.getStatusCode(), 500);
    }

    @Test
    public void createSingleProjectWithInitialVelocityAsNegative() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My test project");
        pivotalProject.setInitial_velocity(-1);
        ApiResponse apiResponse = createProject(pivotalProject);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createSingleProjectWithLargeName() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("Long tittle example to test more than 50 characters");
        ApiResponse apiResponse = createProject(pivotalProject);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createSingleProjectWithLargePointScale() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My test project");
        pivotalProject.setPoint_scale("Long point scale example to test more than 255 characters. Apart from " +
                "counting words and characters, our online editor can help you to improve word choice and writing " +
                "style, and, optionally, help you to detect grammar mistakes and plagiarism.  To check word ");
        ApiResponse apiResponse = createProject(pivotalProject);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @BeforeMethod(onlyForGroups = "deleteProject")
    public void createProjectForDeleteProjectGroup() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        apiRequest.setEndpoint("/projects");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(pivotalProject));
        project = ApiManager.executeWithBody(apiRequest).getBody(PivotalProject.class);
    }

    @Test(groups = "deleteProject")
    public void deleteSingleProjectTest() {
        ApiResponse apiResponse = deleteProject(project.getId().toString());
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
