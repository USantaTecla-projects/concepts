package com.example.backend.api.resources.exam;

import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.exam.dto.ReplyExamDTO;
import com.example.backend.api.resources.exam.model.Exam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Exam create(@RequestBody final CreateExamDTO createExamDTO) {
        return examService.create(createExamDTO);
    }
    @PostMapping("/replies")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void reply(@RequestBody final ReplyExamDTO replyExamDTO){
        examService.reply(replyExamDTO);
    }
}
