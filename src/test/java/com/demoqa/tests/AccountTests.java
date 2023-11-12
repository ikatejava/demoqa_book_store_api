package com.demoqa.tests;

import com.demoqa.models.GenerateTokenResponseModel;
import com.demoqa.models.MistakesResponseModel;
import com.demoqa.models.RegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.demoqa.specs.RequestSpecs.accountRequestSpecification;
import static com.demoqa.specs.ResponseSpecs.*;
import static com.demoqa.tests.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AccountTests extends TestBase {
    @Test
    @Tag("user")
    @Tag("positive")
    @DisplayName("Successful authorization of existing user")
    void successfulLoginTest() {
        given(accountRequestSpecification)
                .body(validAuthData)
                .when()
                .post(requestLoginURN)
                .then()
                .spec(successfulLoginResponseSpec200)
                .body(equalTo("true"));
    }

    @Test
    @Tag("user")
    @Tag("negative")
    @DisplayName("Failed authorization with password missing")
    void loginTestWithMissingPassword() {
        MistakesResponseModel mistakesResponse = step("Make request", () ->
                given(accountRequestSpecification)
                .body(loginOnly)
                .when()
                .post(requestLoginURN)
                .then()
                .spec(loginAndGenerateTokenMissingDataResponseSpec400)
                .extract().as(MistakesResponseModel.class));
        step("Check response 400", () -> {
            assertEquals(responseCode1200, mistakesResponse.getCode());
            assertEquals(responseCode1200Message, mistakesResponse.getMessage());
        });
    }

    @Test
    @Tag("user")
    @Tag("negative")
    @DisplayName("Failed authorization with wrong password")
    void loginTestWithWrongPassword() {
        MistakesResponseModel mistakesResponse = step("Make request", () ->
                given(accountRequestSpecification)
                .body(invalidPasswordAuthData)
                .when()
                .post(requestLoginURN)
                .then()
                .spec(loginWithWrongDataResponseSpec404)
                .extract().as(MistakesResponseModel.class));
        step("Check response 404", () -> {
            assertEquals(responseCode1207, mistakesResponse.getCode());
            assertEquals(responseCode1207Message, mistakesResponse.getMessage());
        });
    }

    @Test
    @Tag("user")
    @Tag("positive")
    @DisplayName("Successful authorization and token generation")
    void successfulGenerateTokenTest() {
        GenerateTokenResponseModel generateTokenResponse = step("Make request", () ->
                given(accountRequestSpecification)
                .body(validAuthData)
                .when()
                .post(requestGenerateTokenURN)
                .then()
                .spec(successfulGenerateTokenResponseSpec200)
                .extract().as(GenerateTokenResponseModel.class));
        step("Check response 200", () -> {
            assertEquals(responseCode200Status, generateTokenResponse.getStatus());
            assertEquals(responseCode200Result, generateTokenResponse.getResult());
        });
    }

    @Test
    @Tag("user")
    @Tag("negative")
    @DisplayName("Failed authorization and token generation with password missing")
    void generateTokenWithMissingPasswordTest() {
        MistakesResponseModel mistakesResponse = step("Make request", () ->
                given(accountRequestSpecification)
                .body(loginOnly)
                .when()
                .post(requestGenerateTokenURN)
                .then()
                .spec(loginAndGenerateTokenMissingDataResponseSpec400)
                .extract().as(MistakesResponseModel.class));
        step("Check response 400", () -> {
            assertEquals(responseCode1200, mistakesResponse.getCode());
            assertEquals(responseCode1200Message, mistakesResponse.getMessage());
        });
    }

    @Test
    @Tag("user")
    @Tag("negative")
    @DisplayName("Failed authorization and token generation with wrong password")
    void generateTokenWithWrongPasswordTest() {
        GenerateTokenResponseModel generateTokenResponse = step("Make request", () ->
                given(accountRequestSpecification)
                .body(invalidPasswordAuthData)
                .when()
                .post(requestGenerateTokenURN)
                .then()
                .spec(generateTokenWithWrongDataResponseSpec200)
                .extract().as(GenerateTokenResponseModel.class));
        step("Check response 200", () -> {
            assertNull(generateTokenResponse.getToken());
            assertNull(generateTokenResponse.getExpires());
            assertEquals(responseGenerateTokenWithWrongPasswordStatus, generateTokenResponse.getStatus());
            assertEquals(responseGenerateTokenWithWrongPasswordResult, generateTokenResponse.getResult());
        });
    }

    @Test
    @Tag("user")
    @Tag("positive")
    @DisplayName("New user successful registration")
    void successfulRegistrationTest() {
        RegistrationResponseModel registrationResponse = step("Make request", () ->
                given(accountRequestSpecification)
                .body(validRegData)
                .when()
                .post(requestRegistrationURN)
                .then()
                .spec(successfulRegistrationResponseSpec201)
                .extract().as(RegistrationResponseModel.class));
        step("Check response 201", () -> {
            assertEquals(fakerUsername, registrationResponse.getUsername());
        });
    }

    @Test
    @Tag("user")
    @Tag("negative")
    @DisplayName("New user failed registration due to incorrect password")
    void registrationWithInvalidPasswordTest() {
        MistakesResponseModel mistakesResponse = step("Make request", () ->
                given(accountRequestSpecification)
                .body(invalidRegData)
                .when()
                .post(requestRegistrationURN)
                .then()
                .spec(registrationWithInvalidPasswordResponseSpec400)
                .extract().as(MistakesResponseModel.class));
        step("Check response 400", () -> {
            assertEquals(responseCode1300, mistakesResponse.getCode());
            assertEquals(responseCode1300Message, mistakesResponse.getMessage());
        });
    }

    @Test
    @Tag("user")
    @Tag("negative")
    @DisplayName("Failed registration of already existing user")
    void registrationOfExistingUserTest() {
        MistakesResponseModel mistakesResponse = step("Make request", () ->
                given(accountRequestSpecification)
                .body(validAuthData)
                .when()
                .post(requestRegistrationURN)
                .then()
                .spec(registrationOfExistingUserResponseSpec406)
                .extract().as(MistakesResponseModel.class));
        step("Check response 406", () -> {
            assertEquals(responseCode1204, mistakesResponse.getCode());
            assertEquals(responseCode1204Message, mistakesResponse.getMessage());
        });
    }
}
