package com.demoqa.tests;

import com.demoqa.models.BookInfoModel;
import com.demoqa.models.MistakesResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.demoqa.specs.RequestSpecs.bookRequestSpecification;
import static com.demoqa.specs.ResponseSpecs.*;
import static com.demoqa.tests.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookStoreTests {
    @Test
    @Tag("books")
    @DisplayName("Get information about all books in the book store")
    void checkAllBooksInfo() {
        given(bookRequestSpecification)
                .when()
                .get(requestCheckAllBooksURN)
                .then()
                .spec(checkAllBooksInfoResponseSpec200);
    }
    @Test
    @Tag("books")
    @DisplayName("Get information about 1 book in the book store")
    void checkBookInfoByExistingISBN() {
        BookInfoModel bookInfo = step("Make request", () -> {
            return given(bookRequestSpecification)
                    .when()
                    .get(requestCheckBookInfoURN + gitPocketGuideISBN)
                    .then()
                    .spec(checkBookInfoResponseSpec200)
                    .extract().as(BookInfoModel.class);
        });
        step("Check response 200", () -> {
            assertEquals(gitPocketGuideISBN, bookInfo.getIsbn());
            assertEquals(gitPocketGuideTitle, bookInfo.getTitle());
            assertEquals(gitPocketGuideSubtitle, bookInfo.getSubTitle());
            assertEquals(gitPocketGuideAuthor, bookInfo.getAuthor());
            assertEquals(gitPocketGuidePublishDate, bookInfo.getPublish_date());
            assertEquals(gitPocketGuidePublisher, bookInfo.getPublisher());
            assertEquals(gitPocketGuideNumberOfPages, bookInfo.getPages());
            assertEquals(gitPocketGuideDescription, bookInfo.getDescription());
            assertEquals(gitPocketGuideWebsite, bookInfo.getWebsite());
        });
    }
    @Test
    @Tag("books")
    @DisplayName("Get information about book with non-existent ISBN (failure)")
    void checkBookInfoByNonExistentISBN() {
        MistakesResponseModel mistakesResponse = step("Make request", () -> {
            return given(bookRequestSpecification)
                    .when()
                    .get(requestCheckBookInfoURN + nonExistentISBN)
                    .then()
                    .spec(checkBookInfoByNonExistentISBNResponseSpec400)
                    .extract().as(MistakesResponseModel.class);
        });
        step("Check response 400", () -> {
            assertEquals(responseCode1205, mistakesResponse.getCode());
            assertEquals(responseCode1205Message, mistakesResponse.getMessage());
        });
    }
}
