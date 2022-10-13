package com.example.backend.api.resources.exam;

import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.exam.dto.QuestionAndAnswerDTO;
import com.example.backend.api.resources.exam.dto.ReplyExamDTO;
import com.example.backend.api.resources.exam.exception.specific.CreateExamDTOBadRequestException;
import com.example.backend.api.resources.exam.exception.specific.ExamNotFoundException;
import com.example.backend.api.resources.exam.exception.specific.ReplyExamDTOBadRequestException;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.exam.module.answer.dto.AnswerDTO;
import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.service.AnswerService;
import com.example.backend.api.resources.exam.module.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.module.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.service.QuestionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    private final QuestionService questionService;

    private final AnswerService answerService;

    public ExamService(
            ExamRepository examRepository,
            QuestionService questionService,
            AnswerService answerService
    ) {
        this.examRepository = examRepository;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    public Exam create(final CreateExamDTO createExamDTO) {

        final Long userID = createExamDTO
                .getUserIDOptional(createExamDTO.getUserID())
                .orElseThrow(() -> new ReplyExamDTOBadRequestException("Field userID in CreateExam DTO is mandatory"));

        final int numberOfQuestions = createExamDTO
                .getNumberOfQuestionsOptional(createExamDTO.getNumberOfQuestions())
                .orElseThrow(() -> new CreateExamDTOBadRequestException("Field numberOfQuestions in CreateExam DTO is mandatory"));

        final List<Question> questions = questionService.createQuestions(numberOfQuestions);

        return createExamOnDatabase(userID, questions);
    }


    public void reply(final ReplyExamDTO replyExamDTO) {
        
        final Long userID = replyExamDTO
                .getUserIDOptional(replyExamDTO.getUserID())
                .orElseThrow(() -> new ReplyExamDTOBadRequestException("Field userID in ReplyExam DTO is mandatory"));

        checkExamExistOnDatabase(replyExamDTO);

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

        final List<Question> questions = questionService.mapQuestionDTOToQuestion(questionDTOList);
        final List<Answer> answers = answerService.saveAndGetAnswers(answerDTOList);

        System.out.println(answers);

        saveAnswersOnQuestions(questions, answers);

        replyExamOnDatabase(userID);
    }

    private void checkExamExistOnDatabase(final ReplyExamDTO replyExamDTO) {
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
    }

    private Exam createExamOnDatabase(final Long userID,final List<Question> questionList) {
        final Exam exam = new Exam(questionList, userID, new Timestamp(System.currentTimeMillis()));
        Exam savedExam = examRepository.save(exam);
        System.out.println(savedExam);
        return savedExam;
    }

    private void replyExamOnDatabase(final Long userID) {
        final Exam exam = examRepository
                .findTopByUserIDOrderByCreationDate(userID)
                .orElseThrow(() -> new ExamNotFoundException("The exam that you have replied wasn't found"));

        exam.setReplyDate(new Timestamp(System.currentTimeMillis()));
        examRepository.save(exam);
    }

    private void saveAnswersOnQuestions(final List<Question> questions, final List<Answer> answers) {
        final Iterator<Question> questionIterator = questions.iterator();
        final Iterator<Answer> answerIterator = answers.iterator();

        while (questionIterator.hasNext() && answerIterator.hasNext()) {
            final Question question = questionIterator.next();
            final Answer answer = answerIterator.next();
            question.addAnswer(answer);
            questionService.saveQuestion(question);
        }


    }
}
