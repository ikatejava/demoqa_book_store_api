package com.demoqa.models;

import lombok.Data;

@Data
public class BookInfoModel {
    String isbn, title, subTitle, author, publish_date, publisher, description, website;
    int pages;
}
