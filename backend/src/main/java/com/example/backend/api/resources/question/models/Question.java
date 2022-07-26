package com.example.backend.api.resources.question.models;

import com.example.backend.api.resources.question.visitor.Visitor;

public abstract class Question {

    public abstract void accept(Visitor visitor);
}
