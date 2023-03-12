package com.example.backend.api.resources.exam.service;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.ExamRepository;
import com.example.backend.api.resources.exam.domain.family.answer.dto.AnswerDTO;
import com.example.backend.api.resources.exam.domain.family.answer.model.CorrectionStatus;
import com.example.backend.api.resources.exam.domain.family.answer.service.SaveAnswerService;
import com.example.backend.api.resources.exam.domain.family.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.service.MapQuestionService;
import com.example.backend.api.resources.exam.dto.UpdateExamDTO;
import com.example.backend.api.resources.exam.exception.specific.ExamNotFoundException;
import com.example.backend.api.resources.exam.exception.specific.UpdateExamDTOBadRequestException;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.user.UserRepository;
import com.example.backend.api.resources.user.exception.model.UserNotFoundException;
import com.example.backend.api.resources.user.model.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateExamService {

    private final ExamRepository examRepository;

    private final UserRepository userRepository;
    private final MapQuestionService mapQuestionService;
    private final SaveAnswerService saveAnswerService;


    public UpdateExamService(
            ExamRepository examRepository,
            UserRepository userRepository,
            MapQuestionService mapQuestionService,
            SaveAnswerService saveAnswerService
    ) {
        this.examRepository = examRepository;
        this.userRepository = userRepository;
        this.mapQuestionService = mapQuestionService;
        this.saveAnswerService = saveAnswerService;
    }


    public void update(final UpdateExamDTO updateExamDTO, String action) {

        final Long userID = updateExamDTO
                .getUserIDOptional(updateExamDTO.getUserID())
                .orElseThrow(() -> new UpdateExamDTOBadRequestException("Field userID in UpdateExam DTO is mandatory"));


        final List<QuestionDTO> questionDTOList = updateExamDTO
                .getQuestionDTOListOptional(updateExamDTO.getQuestionDTOList())
                .orElseThrow(() -> new UpdateExamDTOBadRequestException("Field questionDTOList in UpdateExam DTO is mandatory"));


        final List<AnswerDTO> answerDTOList = updateExamDTO
                .getAnswerDTOListOptional(updateExamDTO.getAnswerDTOList())
                .orElseThrow(() -> new UpdateExamDTOBadRequestException("Field answerDTOList in UpdateExam DTO is mandatory"));

        final List<Question> questions = mapQuestionService.mapQuestionDTOToQuestion(questionDTOList);
        final List<Answer> answers = saveAnswerService.saveManyAnswers(answerDTOList);


        updateExamOnDatabase(updateExamDTO, questions, answers, userID, action);

    }


    private void updateExamOnDatabase(
            final UpdateExamDTO updateExamDTO,
            final List<Question> questions,
            final List<Answer> answers,
            final Long userID,
            final String action
    ) {

        Exam exam = checkExamExistOnDatabase(updateExamDTO);

        if (exam == null) {
            exam = new Exam(questions, userID, updateExamDTO.getCreationDate());
        }

        exam.setReplyDate(new Timestamp(System.currentTimeMillis()));

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

        if (action == "reply") {
            referenceExamToUser(userID, exam);

        }

    }

    private void referenceExamToUser(Long userID, Exam exam) {
        User user = userRepository
                .findById(userID)
                .orElseThrow(() -> new UserNotFoundException("The user with ID =" + userID + " was not found"));

        user.getExamList().add(exam);
        userRepository.save(user);
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
