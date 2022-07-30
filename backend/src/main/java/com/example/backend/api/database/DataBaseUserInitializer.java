package com.example.backend.api.database;


import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import com.example.backend.api.resources.user.UserRepository;
import com.example.backend.api.resources.user.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DataBaseUserInitializer implements CommandLineRunner {

    private final ConceptRepository conceptRepository;
    private final AnswerRepository answerRepository;
    private final JustificationRepository justificationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataBaseUserInitializer(ConceptRepository conceptRepository, AnswerRepository answerRepository, JustificationRepository justificationRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
        this.justificationRepository = justificationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        userRepository
                .save(new User(1L, "teacher", passwordEncoder.encode("1234"), List.of("TEACHER", "STUDENT")));
        userRepository
                .save(new User(2L, "student", passwordEncoder.encode("1234"), List.of("STUDENT")));


        generateKnowledge(
                "software",
                "is a set of instructions, data or programs used to operate computers and execute specific tasks",
                "software correct justification",
                "software incorrect justification",
                "is used to prepare ice cream",
                "software correct justification",
                "software incorrect justification"
        );

        generateKnowledge(
                "hardware",
                "is the physical parts of a computer and related devices",
                "hardware correct justification",
                "hardware incorrect justification",
                "is software in the hard way",
                "hardware correct justification",
                "hardware incorrect justification"
        );

        generateKnowledge(
                "Linux",
                "is an operative system based on UNIX",
                "Linux correct justification",
                "Linux incorrect justification",
                "is a Microsoft product",
                "Linux correct justification",
                "Linux incorrect justification"
        );

        generateKnowledge(
                "Haskell",
                "is a pure functional language",
                "Linux correct justification",
                "Linux incorrect justification",
                "is better than Java",
                "Linux correct justification",
                "Linux incorrect justification"
        );


    }

    private void generateKnowledge(
            String conceptText,
            String correctAnswerText,
            String correctJustificationTextForCorrectAnswer,
            String incorrectJustificationTextForCorrectAnswer,
            String incorrectAnswerText,
            String correctJustificationTextForIncorrectAnswer,
            String incorrectJustificationTextForIncorrectAnswer
    ) {
        // Create concept
        Concept concept = new Concept(conceptText);
        conceptRepository.save(concept);

        // Create answers
        long conceptId = concept.getId();
        Answer correctAnswer = createAnswer(correctAnswerText, true, conceptId);
        Answer incorrectAnswer = createAnswer(incorrectAnswerText, false, conceptId);

        // Create justifications for correct Answer
        long correctAnswerId = correctAnswer.getId();
        Justification correctJustificationForCorrectAnswer = createJustification(
                correctJustificationTextForCorrectAnswer,
                true,
                conceptId,
                correctAnswerId);
        Justification incorrectJustificationForCorrectAnswer = createJustification(
                incorrectJustificationTextForCorrectAnswer,
                false,
                conceptId,
                correctAnswerId);

        // Create justifications for incorrect Answer
        long incorrectAnswerId = incorrectAnswer.getId();
        Justification correctJustificationForIncorrectAnswer = createJustification(
                correctJustificationTextForIncorrectAnswer,
                true,
                conceptId,
                incorrectAnswerId);
        Justification incorrectJustificationForIncorrectAnswer = createJustification(
                incorrectJustificationTextForIncorrectAnswer,
                false,
                conceptId,
                incorrectAnswerId);

        // Add justification to answers
        correctAnswer.addJustification(correctJustificationForCorrectAnswer);
        correctAnswer.addJustification(incorrectJustificationForCorrectAnswer);

        incorrectAnswer.addJustification(correctJustificationForIncorrectAnswer);
        incorrectAnswer.addJustification(incorrectJustificationForIncorrectAnswer);

        answerRepository.save(correctAnswer);
        answerRepository.save(incorrectAnswer);

        // Add answers to concept
        concept.addAnswer(correctAnswer);
        concept.addAnswer(incorrectAnswer);

        conceptRepository.save(concept);
    }

    private Answer createAnswer(String answerText, boolean isCorrect, long conceptId) {
        Answer answer = new Answer(answerText, isCorrect);
        answer.setConceptId(conceptId);
        return answerRepository.save(answer);
    }

    private Justification createJustification(String justificationText, boolean isCorrect, long conceptId, long answerId) {
        Justification justification = new Justification(justificationText, isCorrect, "explanation");
        justification.setConceptId(conceptId);
        justification.setAnswerId(answerId);
        return justificationRepository.save(justification);
    }


}
