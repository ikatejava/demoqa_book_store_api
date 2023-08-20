package com.demoqa.tests;

import com.demoqa.models.LoginAndRegistrationModel;
import com.github.javafaker.Faker;

import static com.demoqa.tests.TestData.*;

public class TestBase {
    Faker faker = new Faker();

    LoginAndRegistrationModel validAuthData = new LoginAndRegistrationModel(accountUsername, accountValidPassword);
    LoginAndRegistrationModel loginOnly = new LoginAndRegistrationModel(accountUsername, null);
    LoginAndRegistrationModel invalidPasswordAuthData = new LoginAndRegistrationModel(accountUsername,
            accountInvalidPassword);

    String fakerUsername = faker.name().username();
    LoginAndRegistrationModel validRegData = new LoginAndRegistrationModel(fakerUsername, validPasswordForRegistration);
    LoginAndRegistrationModel invalidRegData = new LoginAndRegistrationModel(fakerUsername,
            invalidPasswordForRegistration);
}
