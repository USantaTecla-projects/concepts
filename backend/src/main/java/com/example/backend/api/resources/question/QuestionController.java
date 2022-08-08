package com.example.backend.api.resources.question;

import com.example.backend.api.resources.question.dto.GetQuestionsDTO;
import com.example.backend.api.resources.question.models.Question;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public List<Question> create(@RequestBody final GetQuestionsDTO getQuestionsDTO) {
        return questionService.create(getQuestionsDTO);
    }
}
