package com.demoqa.specs;

import io.restassured.specification.RequestSpecification;

import static com.demoqa.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class RequestSpecs {
    public static RequestSpecification accountRequestSpecification = with()
            .log().uri()
            .log().method()
            .log().body()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .baseUri("https://demoqa.com/")
            .basePath("Account/v1/");
    public static RequestSpecification bookRequestSpecification = with()
            .log().uri()
            .log().method()
            .log().body()
            .filter(withCustomTemplates())
            .baseUri("https://demoqa.com/")
            .basePath("BookStore/v1/");
}
