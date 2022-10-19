package com.example.backend.api.resources.exam;

import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.exam.dto.ReplyExamDTO;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.exam.service.CreateExamService;
import com.example.backend.api.resources.exam.service.GetUserExamService;
import com.example.backend.api.resources.exam.service.ReplyExamService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exams")
public class ExamController {

    private final CreateExamService createExamService;
    private final ReplyExamService replyExamService;
    private final GetUserExamService getUserExamService;

    public ExamController(
            CreateExamService createExamService,
            ReplyExamService replyExamService,
            GetUserExamService getUserExamService
    ) {
        this.createExamService = createExamService;
        this.replyExamService = replyExamService;
        this.getUserExamService = getUserExamService;
    }

    @GetMapping("/{userID}")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<Exam> getUserExams(@PathVariable final Long userID, @RequestParam final Integer page) {
        return getUserExamService.getUserExams(userID, page);
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Exam create(@RequestBody final CreateExamDTO createExamDTO) {
        return createExamService.create(createExamDTO);
    }

    @PatchMapping("/")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void reply(@RequestBody final ReplyExamDTO replyExamDTO) {
        replyExamService.reply(replyExamDTO);
    }
}
