package com.demoqa.tests;

public class TestData {
    public static String
            accountUsername = "kate_belova",
            accountValidPassword = "$B00k_1over$",
            accountInvalidPassword = "$B00k-1over$",
            validPasswordForRegistration = "j&PSJ-Yz6D",
            invalidPasswordForRegistration = "qwerty12",

            requestLoginURN = "Authorized",
            requestGenerateTokenURN = "GenerateToken",
            requestRegistrationURN = "User",
            requestCheckAllBooksURN = "Books",
            requestCheckBookInfoURN = "Book?ISBN=",

            responseGenerateTokenWithWrongPasswordStatus = "Failed",
            responseGenerateTokenWithWrongPasswordResult = "User authorization failed.",

            responseCode200Status = "Success",
            responseCode200Result = "User authorized successfully.",

            responseCode1200 = "1200",
            responseCode1200Message = "UserName and Password required.",

            responseCode1204 = "1204",
            responseCode1204Message = "User exists!",

            responseCode1205 = "1205",
            responseCode1205Message = "ISBN supplied is not available in Books Collection!",

            responseCode1207 = "1207",
            responseCode1207Message = "User not found!",

            responseCode1300 = "1300",
            responseCode1300Message = "Passwords must have at least one non alphanumeric character, " +
                    "one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), " +
                    "one special character and Password must be eight characters or longer.",

            gitPocketGuideISBN = "9781449325862",
            gitPocketGuideTitle = "Git Pocket Guide",
            gitPocketGuideSubtitle = "A Working Introduction",
            gitPocketGuideAuthor = "Richard E. Silverman",
            gitPocketGuidePublishDate = "2020-06-04T08:48:39.000Z",
            gitPocketGuidePublisher = "O'Reilly Media",
            gitPocketGuideDescription = "This pocket guide is the perfect on-the-job companion to Git, " +
                    "the distributed version control system. It provides a compact, readable introduction to Git " +
                    "for new users, as well as a reference to common commands and procedures for those of you " +
                    "with Git exp",
            gitPocketGuideWebsite = "http://chimera.labs.oreilly.com/books/1230000000561/index.html",

            nonExistentISBN = "123456789";

    public static int
            gitPocketGuideNumberOfPages = 234;


}
