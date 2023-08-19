package com.demoqa.tests;

import com.demoqa.models.GenerateTokenResponseModel;
import com.demoqa.models.LoginAndRegistrationModel;
import com.demoqa.models.MistakesResponseModel;
import com.demoqa.models.RegistrationResponseModel;
import com.github.javafaker.Faker;
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

public class AccountTests {
    Faker faker = new Faker();
    @Test
    @Tag("user")
    @DisplayName("Successful authorization of existing user")
    void successfulLoginTest() {

        LoginAndRegistrationModel authData = new LoginAndRegistrationModel();
        authData.setUserName(accountUsername);
        authData.setPassword(accountValidPassword);

        given(accountRequestSpecification)
                .body(authData)
                .when()
                .post(requestLoginURN)
                .then()
                .spec(successfulLoginResponseSpec200)
                .body(equalTo("true"));
    }
    @Test
    @Tag("user")
    @DisplayName("Failed authorization with password missing")
    void loginTestWithMissingPassword() {

        LoginAndRegistrationModel authData = new LoginAndRegistrationModel();
        authData.setUserName(accountUsername);

        MistakesResponseModel mistakesResponse = step("Make request", () -> {
            return given(accountRequestSpecification)
                    .body(authData)
                    .when()
                    .post(requestLoginURN)
                    .then()
                    .spec(loginAndGenerateTokenMissingDataResponseSpec400)
                    .extract().as(MistakesResponseModel.class);
        });
        step("Check response 400", () -> {
            assertEquals(responseCode1200, mistakesResponse.getCode());
            assertEquals(responseCode1200Message, mistakesResponse.getMessage());
        });
    }
    @Test
    @Tag("user")
    @DisplayName("Failed authorization with wrong password")
    void loginTestWithWrongPassword() {

        LoginAndRegistrationModel authData = new LoginAndRegistrationModel();
        authData.setUserName(accountUsername);
        authData.setPassword(accountInvalidPassword);

        MistakesResponseModel mistakesResponse = step("Make request", () -> {
            return given(accountRequestSpecification)
                    .body(authData)
                    .when()
                    .post(requestLoginURN)
                    .then()
                    .spec(loginWithWrongDataResponseSpec404)
                    .extract().as(MistakesResponseModel.class);
        });
        step("Check response 404", () -> {
            assertEquals(responseCode1207, mistakesResponse.getCode());
            assertEquals(responseCode1207Message, mistakesResponse.getMessage());
        });
    }
    @Test
    @Tag("user")
    @DisplayName("Successful authorization and token generation")
    void successfulGenerateTokenTest() {

        LoginAndRegistrationModel authData = new LoginAndRegistrationModel();
        authData.setUserName(accountUsername);
        authData.setPassword(accountValidPassword);

        GenerateTokenResponseModel generateTokenResponse = step("Make request", () -> {
            return given(accountRequestSpecification)
                    .body(authData)
                    .when()
                    .post(requestGenerateTokenURN)
                    .then()
                    .spec(successfulGenerateTokenResponseSpec200)
                    .extract().as(GenerateTokenResponseModel.class);
        });
        step("Check response 200", () -> {
            generateTokenResponse.getToken();
            generateTokenResponse.getExpires();
            assertEquals(responseCode200Status, generateTokenResponse.getStatus());
            assertEquals(responseCode200Result, generateTokenResponse.getResult());
        });
    }
    @Test
    @Tag("user")
    @DisplayName("Failed authorization and token generation with password missing")
    void generateTokenWithMissingPasswordTest() {

        LoginAndRegistrationModel authData = new LoginAndRegistrationModel();
        authData.setUserName(accountUsername);

        MistakesResponseModel mistakesResponse = step("Make request", () -> {
            return given(accountRequestSpecification)
                    .body(authData)
                    .when()
                    .post(requestGenerateTokenURN)
                    .then()
                    .spec(loginAndGenerateTokenMissingDataResponseSpec400)
                    .extract().as(MistakesResponseModel.class);
        });
        step("Check response 400", () -> {
            assertEquals(responseCode1200, mistakesResponse.getCode());
            assertEquals(responseCode1200Message, mistakesResponse.getMessage());
        });
    }
    @Test
    @Tag("user")
    @DisplayName("Failed authorization and token generation with wrong password")
    void generateTokenWithWrongPasswordTest() {

        LoginAndRegistrationModel authData = new LoginAndRegistrationModel();
        authData.setUserName(accountUsername);
        authData.setPassword(accountInvalidPassword);

        GenerateTokenResponseModel generateTokenResponse = step("Make request", () -> {
            return given(accountRequestSpecification)
                    .body(authData)
                    .when()
                    .post(requestGenerateTokenURN)
                    .then()
                    .spec(generateTokenWithWrongDataResponseSpec200)
                    .extract().as(GenerateTokenResponseModel.class);
        });
        step("Check response 200", () -> {
            assertEquals(null, generateTokenResponse.getToken());
            assertEquals(null, generateTokenResponse.getExpires());
            assertEquals(responseGenerateTokenWithWrongPasswordStatus, generateTokenResponse.getStatus());
            assertEquals(responseGenerateTokenWithWrongPasswordResult, generateTokenResponse.getResult());
        });
    }
    @Test
    @Tag("user")
    @DisplayName("New user successful registration")
    void successfulRegistrationTest() {

        LoginAndRegistrationModel regData = new LoginAndRegistrationModel();
        String userName = faker.name().username();
        regData.setUserName(userName);
        regData.setPassword(validPasswordForRegistration);

        RegistrationResponseModel registrationResponse = step("Make request", () -> {
            return given(accountRequestSpecification)
                    .body(regData)
                    .when()
                    .post(requestRegistrationURN)
                    .then()
                    .spec(successfulRegistrationResponseSpec201)
                    .extract().as(RegistrationResponseModel.class);
        });
        step("Check response 201", () -> {
            registrationResponse.getUserID();
            assertEquals(userName, registrationResponse.getUsername());
            registrationResponse.getBooks();
        });
    }
    @Test
    @Tag("user")
    @DisplayName("New user failed registration due to incorrect password")
    void registrationWithInvalidPasswordTest() {

        LoginAndRegistrationModel regData = new LoginAndRegistrationModel();
        String userName = faker.name().username();
        regData.setUserName(userName);
        regData.setPassword(invalidPasswordForRegistration);

        MistakesResponseModel mistakesResponse = step("Make request", () -> {
            return given(accountRequestSpecification)
                    .body(regData)
                    .when()
                    .post(requestRegistrationURN)
                    .then()
                    .spec(registrationWithInvalidPasswordResponseSpec400)
                    .extract().as(MistakesResponseModel.class);
        });
        step("Check response 400", () -> {
            assertEquals(responseCode1300, mistakesResponse.getCode());
            assertEquals(responseCode1300Message, mistakesResponse.getMessage());
        });
    }
    @Test
    @Tag("user")
    @DisplayName("Failed registration of already existing user")
    void registrationOfExistingUserTest() {

        LoginAndRegistrationModel regData = new LoginAndRegistrationModel();
        regData.setUserName(accountUsername);
        regData.setPassword(accountValidPassword);

        MistakesResponseModel mistakesResponse = step("Make request", () -> {
            return given(accountRequestSpecification)
                    .body(regData)
                    .when()
                    .post(requestRegistrationURN)
                    .then()
                    .spec(registrationOfExistingUserResponseSpec406)
                    .extract().as(MistakesResponseModel.class);
        });
        step("Check response 406", () -> {
            assertEquals(responseCode1204, mistakesResponse.getCode());
            assertEquals(responseCode1204Message, mistakesResponse.getMessage());
        });
    }
}
