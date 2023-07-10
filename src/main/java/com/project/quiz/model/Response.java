package com.project.quiz.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;

@Data
@RequiredArgsConstructor
public class Response {

    private Integer id;
    private String response;
}
