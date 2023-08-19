package com.demoqa.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ResponseSpecs {
    public static ResponseSpecification successfulLoginResponseSpec200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody(is(notNullValue()))
            .build();

    public static ResponseSpecification loginAndGenerateTokenMissingDataResponseSpec400 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(400)
            .expectBody("code", notNullValue())
            .expectBody("message", notNullValue())
            .build();

    public static ResponseSpecification loginWithWrongDataResponseSpec404 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(404)
            .expectBody("code", notNullValue())
            .expectBody("message", notNullValue())
            .build();

    public static ResponseSpecification successfulGenerateTokenResponseSpec200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("token", notNullValue())
            .expectBody("expires", notNullValue())
            .expectBody("status", notNullValue())
            .expectBody("result", notNullValue())
            .build();

    public static ResponseSpecification generateTokenWithWrongDataResponseSpec200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("token", nullValue())
            .expectBody("expires", nullValue())
            .expectBody("status", notNullValue())
            .expectBody("result", notNullValue())
            .build();

    public static ResponseSpecification successfulRegistrationResponseSpec201 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(201)
            .expectBody("userID", notNullValue())
            .expectBody("username", notNullValue())
            .expectBody("books", notNullValue())
            .build();

    public static ResponseSpecification registrationWithInvalidPasswordResponseSpec400 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(400)
            .expectBody("code", notNullValue())

            .expectBody("message", notNullValue())
            .build();

    public static ResponseSpecification registrationOfExistingUserResponseSpec406 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(406)
            .expectBody("code", notNullValue())
            .expectBody("message", notNullValue())
            .build();

    public static ResponseSpecification checkAllBooksInfoResponseSpec200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemas/all_books_response.json"))
            .build();

    public static ResponseSpecification checkBookInfoResponseSpec200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("isbn", (notNullValue()))
            .expectBody("title", (notNullValue()))
            .expectBody("subTitle", (notNullValue()))
            .expectBody("author", (notNullValue()))
            .expectBody("publish_date", (notNullValue()))
            .expectBody("publisher", (notNullValue()))
            .expectBody("pages", (notNullValue()))
            .expectBody("description", (notNullValue()))
            .expectBody("website", (notNullValue()))
            .build();

    public static ResponseSpecification checkBookInfoByNonExistentISBNResponseSpec400 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(400)
            .expectBody("code", notNullValue())
            .expectBody("message", notNullValue())
            .build();
}
