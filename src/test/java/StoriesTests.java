import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Labels;
import entities.PivotalProject;
import entities.ReadPropertyFile;
import entities.Stories;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class StoriesTests {
    private ApiRequest apiRequest;
    PivotalProject project = new PivotalProject();
    Stories story = new Stories();

    @BeforeTest
    public void setTokenAndBaseUri() {
        apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.addHeader("X-TrackerToken", readPropertyFile.getToken());
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
    }

    @BeforeMethod(onlyForGroups = "getStory")
    public void createSingleProjectAndStory() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        project = PivotalProjectsTests.createProject(pivotalProject).getBody(PivotalProject.class);
        Stories newStory = new Stories();
        newStory.setName("My test Story");
        story = createStory(newStory, project.getId().toString()).getBody(Stories.class);
    }

    @Test(groups = "getStory")
    public void getSingleStoryOnProject() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("storyId", story.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @AfterMethod(onlyForGroups = "getStory")
    public void deleteCreatedProjectAndStory() {
        ApiResponse apiResponse = deleteStory(story.getId().toString(), project.getId().toString());
        apiResponse = PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    public static ApiResponse createStory(Stories story, String projectId) throws JsonProcessingException {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest = PivotalProjectsTests.configureTokenAndBaseUri(apiRequest);
        apiRequest.setEndpoint("/projects/{projectId}/stories");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", projectId);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(story));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        return apiResponse;
    }

    public static ApiResponse deleteStory(String storyId, String projectId) {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest = PivotalProjectsTests.configureTokenAndBaseUri(apiRequest);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", projectId);
        apiRequest.addPathParam("storyId", storyId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        return  apiResponse;
    }
}
