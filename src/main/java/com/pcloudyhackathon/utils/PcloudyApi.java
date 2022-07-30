package com.pcloudyhackathon.utils;

import com.pcloudyhackathon.library.SessionData;
import com.ssts.pcloudy.exception.ConnectError;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.io.IOException;

import static io.restassured.RestAssured.given;


public class PcloudyApi {

    CustomLogger log = new CustomLogger(PcloudyApi.class);

    final private String getCoordinateUri = SessionData.getPCloudyUrl()+"";


    public void getCoordinate(String baseImageId, String text) throws IOException, ConnectError {
        JSONObject requestBody = new JSONObject();
        requestBody.put("imageId", baseImageId);
        requestBody.put("word", text);

        log.debug("[updatePosSaleSetting] - requestBody = "+requestBody);

        RequestSpecification requestSpec = given()
                .body(requestBody)
                .headers("token", new PcloudyUtils().getAuth())
                .when();

        log.debug("[updatePosSaleSetting]-[requestSpec]-Request to update pos sales setting " + requestSpec.log().all(false));

        ValidatableResponse response = requestSpec.get().then();
    }
}
