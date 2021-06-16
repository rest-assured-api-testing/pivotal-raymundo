package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiManager {
    private static RequestSpecification buildRequest(ApiRequest request)
    {
        return given().headers(request.getHeaders())
                .queryParams(request.getQueryParams())
                .pathParams(request.getPathParams())
                .baseUri(request.getBaseUri())
                .contentType(ContentType.JSON)
                .auth().oauth2(request.getToken())
                .log().all();
    }
    public static Response execute(ApiRequest request){
        Response response = buildRequest(request)
                .request(request.getMethod().name()
                        ,request.getEndpoint());
        return response;
    }
}
