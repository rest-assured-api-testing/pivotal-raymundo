import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import entities.ReadPropertyFile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AccountMembershipTests {
    private ApiRequest apiRequest;
    private String accountId;
    private String personId;

    @BeforeTest
    public void setTokenAndBaseUri() {
        apiRequest = new ApiRequest();
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        apiRequest.addHeader("X-TrackerToken", readPropertyFile.getToken());
        apiRequest.setBaseUri(readPropertyFile.getBaseUri());
        accountId = readPropertyFile.getAccountId();
        personId = readPropertyFile.getPersonId();
    }

    @AfterMethod
    public void clearPathParamValues() {
        apiRequest.cleanPathParam();
    }

    @Test
    public void getAllMembershipsAccounts() {
        apiRequest.setEndpoint("/accounts/{accountId}/memberships");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getSingleMembershipAccount() {
        apiRequest.setEndpoint("/accounts/{accountId}/memberships/{personId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        apiRequest.addPathParam("personId", personId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getSingleMembershipAccountWithInvalidAccountId() {
        apiRequest.setEndpoint("/accounts/{accountId}/memberships/{personId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", "InvalidAccountId");
        apiRequest.addPathParam("personId", personId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test
    public void getSingleMembershipAccountWithInvalidPersonId() {
        apiRequest.setEndpoint("/accounts/{accountId}/memberships/{personId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        apiRequest.addPathParam("personId", "InvalidPersonId");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test
    public void checkAccountMembershipSchema() {
        apiRequest.setEndpoint("/accounts/{accountId}/memberships/{personId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("accountId", accountId);
        apiRequest.addPathParam("personId", personId);
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/accountmembership.json");
    }
}
