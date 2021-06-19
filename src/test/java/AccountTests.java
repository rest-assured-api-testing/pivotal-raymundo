import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import entities.ReadPropertyFile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AccountTests {
    private ApiRequest apiRequest;
    private String accountId;

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
}
