import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import entities.PivotalProject;
import entities.ReadPropertyFile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AccountTests {
    private ApiRequest apiRequest;
    private String accountId;
    private PivotalProject project = new PivotalProject();

    @BeforeTest
    public void setTokenAndBaseUri() {
        apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.addHeader("X-TrackerToken", readPropertyFile.getToken());
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
        accountId = readPropertyFile.getAccountId();
    }

    @AfterMethod
    public void clearPathParamValues() {
        apiRequest.cleanPathParam();
        apiRequest.cleanQueryParam();
    }

    @Test
    public void getAccount() {
        apiRequest.setEndpoint("/accounts/{accountId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getAllAccounts() {
        apiRequest.setEndpoint("/accounts");
        apiRequest.setMethod(ApiMethod.GET);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getAccountSummaries() {
        apiRequest.setEndpoint("/account_summaries");
        apiRequest.setMethod(ApiMethod.GET);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getAccountSummariesWithPermissionParamNone() {
        apiRequest.setEndpoint("/account_summaries");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("with_permission", "none");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getAccountSummariesWithPermissionParamProjectCreation() {
        apiRequest.setEndpoint("/account_summaries");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("with_permission", "project_creation");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getAccountSummariesWithPermissionParamAdministration() {
        apiRequest.setEndpoint("/account_summaries");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("with_permission", "administration");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getAccountSummariesWithInvalidPermissionParam() {
        apiRequest.setEndpoint("/account_summaries");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("with_permission", "InvalidValue");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void getAccountWithInvalidToken() {
        ApiRequest apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.addHeader("X-TrackerToken", "InvalidToken");
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
        accountId = readPropertyFile.getAccountId();
        apiRequest.setEndpoint("/accounts/{accountId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 403);
    }

    @Test
    public void getAccountWithOutTokenHeader() {
        ApiRequest apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
        accountId = readPropertyFile.getAccountId();
        apiRequest.setEndpoint("/accounts/{accountId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 403);
    }

    @Test
    public void getAccountWithInvalidId() {
        apiRequest.setEndpoint("/accounts/{accountId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", "InvalidId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test
    public void checkAccountSchema() {
        apiRequest.setEndpoint("/accounts/{accountId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/account.json");
    }

    @Test
    public void getMe() {
        apiRequest.setEndpoint("/me");
        apiRequest.setMethod(ApiMethod.GET);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void checkMeSchema() {
        apiRequest.setEndpoint("/me");
        apiRequest.setMethod(ApiMethod.GET);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/me.json");
    }

    @BeforeMethod(onlyForGroups = "project")
    public void createProjects() throws JsonProcessingException {
        PivotalProject pivotalProject = new PivotalProject();
        pivotalProject.setName("My Test Project");
        ApiResponse apiResponse = PivotalProjectsTests.createProject(pivotalProject);
        project = apiResponse.getBody(PivotalProject.class);
    }

    @AfterMethod(onlyForGroups = "project")
    public void deleteCreatedProject() {
        PivotalProjectsTests.deleteProject(project.getId().toString());
    }

    @Test(groups = "project")
    public void getMyPeople() {
        apiRequest.setEndpoint("/my/people");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("project_id", project.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getMyPeopleWithInvalidProjectId() {
        apiRequest.setEndpoint("/my/people");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("project_id", "InvalidProjectId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test(groups = "project")
    public void checkMyPeopleSchema() {
        apiRequest.setEndpoint("/my/people");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addQueryParam("project_id", project.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/mypeople.json");
    }
}
