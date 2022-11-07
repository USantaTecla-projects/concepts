package com.example.backend.api.resources.exam;

import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.exam.dto.ReplyExamDTO;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.exam.service.CreateExamService;
import com.example.backend.api.resources.exam.service.FindExamsService;
import com.example.backend.api.resources.exam.service.ReplyExamService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{userID}")
public class ExamController {

    private final CreateExamService createExamService;
    private final ReplyExamService replyExamService;
    private final FindExamsService findExamsService;

    public ExamController(
            CreateExamService createExamService,
            ReplyExamService replyExamService,
            FindExamsService findExamsService
    ) {
        this.createExamService = createExamService;
        this.replyExamService = replyExamService;
        this.findExamsService = findExamsService;
    }

    @GetMapping("/exams")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<Exam> getUserExams(@PathVariable final Long userID, @RequestParam final Integer page) {
        return findExamsService.findAllByUserID(userID, page);
    }

    @GetMapping("/exam/{examID}")
    @ResponseStatus(code = HttpStatus.OK)
    public Exam getUserExam(@PathVariable final Long userID, @PathVariable final Long examID) {
        return findExamsService.findByExamID(userID,examID);
    }

    @PostMapping("/exam")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Exam create(@RequestBody final CreateExamDTO createExamDTO) {
        return createExamService.create(createExamDTO);
    }

    @PatchMapping("/exam")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void reply(@RequestBody final ReplyExamDTO replyExamDTO) {
        replyExamService.reply(replyExamDTO);
    }
}
