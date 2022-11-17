package com.example.backend.api.resources.exam.service;

import com.example.backend.api.resources.exam.ExamRepository;
import com.example.backend.api.resources.exam.domain.family.answer.dto.AnswerDTO;
import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.CorrectionStatus;
import com.example.backend.api.resources.exam.domain.family.answer.tools.saver.AnswerSaver;
import com.example.backend.api.resources.exam.domain.family.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.domain.family.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.service.MapQuestionService;
import com.example.backend.api.resources.exam.dto.QuestionAndAnswerDTO;
import com.example.backend.api.resources.exam.dto.UpdateExamDTO;
import com.example.backend.api.resources.exam.exception.specific.ExamNotFoundException;
import com.example.backend.api.resources.exam.exception.specific.UpdateExamDTOBadRequestException;
import com.example.backend.api.resources.exam.model.Exam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateExamService {

    private final ExamRepository examRepository;
    private final MapQuestionService mapQuestionService;
    private final AnswerSaver answerSaver;
    private final AnswerSaver answerService;

    private static final Logger LOG = LoggerFactory.getLogger(UpdateExamService.class);


    public UpdateExamService(
            ExamRepository examRepository,
            MapQuestionService mapQuestionService,
            AnswerSaver answerSaver, AnswerSaver answerService
    ) {
        this.examRepository = examRepository;
        this.mapQuestionService = mapQuestionService;
        this.answerSaver = answerSaver;
        this.answerService = answerService;
    }


    public void update(final UpdateExamDTO updateExamDTO) {

        final Long userID = updateExamDTO
                .getUserIDOptional(updateExamDTO.getUserID())
                .orElseThrow(() -> new UpdateExamDTOBadRequestException("Field userID in UpdateExam DTO is mandatory"));


        final List<QuestionAndAnswerDTO> questionAndAnswerDTOList = updateExamDTO
                .getQuestionAndAnswerDTOListOptional(updateExamDTO.getQuestionAndAnswerDTOList())
                .orElseThrow(() -> new QuestionDTOBadRequestException("Field questionDTOList in ReplyExam DTO is mandatory"));

        final List<QuestionDTO> questionDTOList = questionAndAnswerDTOList
                .stream()
                .map(QuestionAndAnswerDTO::getQuestionDTO)
                .toList();

        final List<AnswerDTO> answerDTOList = questionAndAnswerDTOList
                .stream()
                .map(QuestionAndAnswerDTO::getAnswerDTO)
                .toList();

        final List<Question> questions = mapQuestionService.mapQuestionDTOToQuestion(questionDTOList);
        final List<Answer> answers = answerService.saveManyAnswers(answerDTOList);


        updateExamOnDatabase(updateExamDTO, questions, answers, userID);
    }


    private void updateExamOnDatabase(
            final UpdateExamDTO updateExamDTO,
            final List<Question> questions,
            final List<Answer> answers,
            final Long userID
    ) {

        Exam exam = checkExamExistOnDatabase(updateExamDTO);

        if (exam == null) {
            exam = new Exam(questions, userID, updateExamDTO.getCreationDate());
        }
        
        exam.setReplyDate(new Timestamp(System.currentTimeMillis()));
        exam.calculateTimeSpend();

        Boolean corrected = updateExamDTO
                .getCorrectedOptional(updateExamDTO.getCorrected())
                .orElse(false);

        if (corrected) {
            String mark = calculateMark(answers);
            exam.setMark(mark);
        }

        exam.setCorrected(corrected);
        exam.setAnswerList(new ArrayList<>(answers));

        examRepository.save(exam);
    }

    private Exam checkExamExistOnDatabase(final UpdateExamDTO updateExamDTO) {
        final Long examID = updateExamDTO
                .getExamIDOptional(updateExamDTO.getExamID())
                .orElse(null);

        if (examID == null) {
            return null;
        }

        final Timestamp creationDate = updateExamDTO
                .getCreationDateOptional(updateExamDTO.getCreationDate())
                .orElseThrow(() -> new UpdateExamDTOBadRequestException("Field creationDate in UpdateExam DTO is mandatory"));

        final Exam exam = examRepository
                .findById(examID)
                .orElse(null);

        if (exam == null) {
            return null;
        }

        if (!exam.getCreationDate().equals(creationDate)) {
            throw new ExamNotFoundException("Exam with this creation date wasn't found");
        }

        return exam;
    }


    private String calculateMark(List<Answer> answerList) {
        float correctAnswers = answerList
                .stream()
                .filter(answer -> answer.getCorrectionStatus() == CorrectionStatus.CORRECT)
                .toList()
                .size();
        float totalAnswers = answerList.size();

        DecimalFormat df = new DecimalFormat("#.##");

        return df.format((correctAnswers / totalAnswers) * 10);
    }
}
