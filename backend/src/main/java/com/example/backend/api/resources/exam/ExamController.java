package com.example.backend.api.resources.exam;

import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.exam.dto.UpdateExamDTO;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.exam.service.CreateExamService;
import com.example.backend.api.resources.exam.service.FindExamsService;
import com.example.backend.api.resources.exam.service.UpdateExamService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{userID}")
public class ExamController {

    private final CreateExamService createExamService;
    private final UpdateExamService updateExamService;
    private final FindExamsService findExamsService;

    public ExamController(
            CreateExamService createExamService,
            UpdateExamService updateExamService,
            FindExamsService findExamsService
    ) {
        this.createExamService = createExamService;
        this.updateExamService = updateExamService;
        this.findExamsService = findExamsService;
    }

    @GetMapping("/exams")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<Exam> findAll(
            @PathVariable final Long userID,
            @RequestParam final Integer page,
            @RequestParam final Boolean isCorrected
    ) {
        return findExamsService.findAllByUserID(userID, page, isCorrected);
    }

    @GetMapping("/exam/{examID}")
    @ResponseStatus(code = HttpStatus.OK)
    public Exam findOne(@PathVariable final Long userID, @PathVariable final Long examID) {
        return findExamsService.findByExamID(userID, examID);
    }

    @PostMapping("/exam")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Exam create(@RequestBody final CreateExamDTO createExamDTO) {
        return createExamService.create(createExamDTO);
    }

    @PatchMapping("/exam")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(@RequestBody final UpdateExamDTO updateExamDTO) {
        updateExamService.update(updateExamDTO);
    }
}
