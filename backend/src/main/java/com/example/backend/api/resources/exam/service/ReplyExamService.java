package com.example.backend.api.resources.exam.service;

import com.example.backend.api.resources.exam.ExamRepository;
import com.example.backend.api.resources.exam.dto.QuestionAndAnswerDTO;
import com.example.backend.api.resources.exam.dto.ReplyExamDTO;
import com.example.backend.api.resources.exam.exception.specific.ExamNotFoundException;
import com.example.backend.api.resources.exam.exception.specific.ReplyExamDTOBadRequestException;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.exam.domain.family.answer.dto.AnswerDTO;
import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.service.SaveAnswerService;
import com.example.backend.api.resources.exam.domain.family.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.domain.family.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.service.MapQuestionService;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.SaveQuestionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

@Service
public class ReplyExamService {

    private final ExamRepository examRepository;
    private final MapQuestionService mapQuestionService;
    private final SaveQuestionService saveQuestionService;
    private final SaveAnswerService answerService;

    public ReplyExamService(
            ExamRepository examRepository,
            MapQuestionService mapQuestionService,
            SaveQuestionService saveQuestionService, SaveAnswerService answerService
    ) {
        this.examRepository = examRepository;
        this.mapQuestionService = mapQuestionService;
        this.saveQuestionService = saveQuestionService;
        this.answerService = answerService;
    }


    public void reply(final ReplyExamDTO replyExamDTO) {

        final Long userID = replyExamDTO
                .getUserIDOptional(replyExamDTO.getUserID())
                .orElseThrow(() -> new ReplyExamDTOBadRequestException("Field userID in ReplyExam DTO is mandatory"));

        Long examID = checkExamExistOnDatabase(replyExamDTO);

        final List<QuestionAndAnswerDTO> questionAndAnswerDTOList = replyExamDTO
                .getQuestionAndAnswerDTOListOptional(replyExamDTO.getQuestionAndAnswerDTOList())
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
        final List<Answer> answers = answerService.saveAndGetAnswers(answerDTOList);

        saveAnswersOnQuestions(questions, answers);

        replyExamOnDatabase(examID, userID);
    }

    private Long checkExamExistOnDatabase(final ReplyExamDTO replyExamDTO) {
        final Long examID = replyExamDTO
                .getExamIDOptional(replyExamDTO.getExamID())
                .orElseThrow(() -> new ReplyExamDTOBadRequestException("Field examID in ReplyExam DTO is mandatory"));

        final Timestamp creationDate = replyExamDTO
                .getCreationDateOptional(replyExamDTO.getCreationDate())
                .orElseThrow(() -> new ReplyExamDTOBadRequestException("Field creationDate in ReplyExam DTO is mandatory"));

        final Exam exam = examRepository
                .findById(examID)
                .orElseThrow(() -> new ExamNotFoundException("Exam with this ID wasn't found in database"));

        if (!exam.getCreationDate().equals(creationDate)) {
            throw new ExamNotFoundException("Exam with this creation date wasn't found");
        }

        return examID;
    }

    private void saveAnswersOnQuestions(final List<Question> questions, final List<Answer> answers) {
        final Iterator<Question> questionIterator = questions.iterator();
        final Iterator<Answer> answerIterator = answers.iterator();

        while (questionIterator.hasNext() && answerIterator.hasNext()) {
            final Question question = questionIterator.next();
            final Answer answer = answerIterator.next();
            question.addAnswer(answer);
            saveQuestionService.saveQuestion(question);
        }
    }

    private void replyExamOnDatabase(final Long examID, final Long userID) {
        final Exam exam = examRepository
                .findByIdAndUserID(examID, userID)
                .orElseThrow(() -> new ExamNotFoundException("The exam that you have replied wasn't found"));

        exam.setReplyDate(new Timestamp(System.currentTimeMillis()));
        exam.calculateTimeSpend();
        examRepository.save(exam);
    }
}