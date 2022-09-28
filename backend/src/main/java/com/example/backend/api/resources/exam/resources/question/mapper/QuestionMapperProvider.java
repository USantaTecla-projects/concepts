package com.example.backend.api.resources.exam.resources.question.mapper;

import com.example.backend.api.resources.exam.resources.question.mapper.specific.QuestionT0Mapper;
import com.example.backend.api.resources.exam.resources.question.mapper.specific.QuestionT1Mapper;
import com.example.backend.api.resources.exam.resources.question.mapper.specific.QuestionT2Mapper;
import com.example.backend.api.resources.exam.resources.question.mapper.specific.QuestionT3Mapper;

public enum QuestionMapperProvider {

    TYPE0 {
        @Override
        public QuestionMapper getQuestionMapper() {
            return new QuestionT0Mapper();
        }
    },
    TYPE1 {
        @Override
        public QuestionMapper getQuestionMapper() {
            return new QuestionT1Mapper();
        }
    },
    TYPE2 {
        @Override
        public QuestionMapper getQuestionMapper() {
            return new QuestionT2Mapper();
        }
    },
    TYPE3 {
        @Override
        public QuestionMapper getQuestionMapper() {
            return new QuestionT3Mapper();
        }
    };

    public abstract QuestionMapper getQuestionMapper();


}
