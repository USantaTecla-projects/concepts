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
public class DataBaseInitializer implements CommandLineRunner {

    private final ConceptRepository conceptRepository;
    private final AnswerRepository answerRepository;
    private final JustificationRepository justificationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataBaseInitializer(ConceptRepository conceptRepository, AnswerRepository answerRepository, JustificationRepository justificationRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
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


        Concept softwareConcept = conceptRepository.save(new Concept("el software."));
        Concept recursividadConcept = conceptRepository.save(new Concept("la recursividad."));

        Answer softwareIncorrectAnswer1 = answerRepository.save(new Answer(
                "el conjunto de los programas.",
                false,
                softwareConcept.getId()));
        Answer softwareIncorrectAnswer2 = answerRepository.save(new Answer(
                "la parte lógica de un sistema informático, o sea sin contemplar el hardware.",
                false,
                softwareConcept.getId()));
        Answer softwareIncorrectAnswer3 = answerRepository.save(new Answer(
                "la información que suministra el desarrollador para manipular la información del usuario.",
                false,
                softwareConcept.getId()));

        Answer recursividadIncorrectAnswer = answerRepository.save(new Answer(
                "la característica de las funciones que se llaman a sí mismas.",
                false,
                recursividadConcept.getId()));
        Answer recursividadCorrectAnswer = answerRepository.save(new Answer(
                "la característica de definiciones que se autorreferencian, directa o indirectamente.",
                true,
                recursividadConcept.getId()));

        Justification softwareJustification1 = justificationRepository.save(new Justification(
                "no contempla los scripts de bases de datos (DDL, SQL), ficheros de configuración, imágenes (*.bmp, *.jpg, …) del interfaz gráfico de usuario, ficheros de datos (JSON, XML,DTD, XML Schema, …), de publicación (HTML, CSS, …), … y otros artefactos necesarios en el software que no son para programar, son configurar, publicar, …",
                false,
                softwareConcept.getId(),
                softwareIncorrectAnswer1.getId()
        ));

        Justification softwareJustification2 = justificationRepository.save(new Justification(
                "la definición es demasiado permisiva porque incluye firmware que no es software.",
                true,
                softwareConcept.getId(),
                softwareIncorrectAnswer2.getId()
        ));

        Justification softwareJustification3 = justificationRepository.save(new Justification(
                "la definición es demasiado permisiva porque los datos de usuario (todos los ficheros generados por el software), no son hardware ni son software y están siendo incluidos en el software.",
                true,
                softwareConcept.getId(),
                softwareIncorrectAnswer2.getId()
        ));

        Justification recursividadJustification1 = justificationRepository.save(new Justification(
                " no contempla la recursividad mutua.",
                false,
                recursividadConcept.getId(),
                recursividadIncorrectAnswer.getId()
        ));

        Justification recursividadJustification2 = justificationRepository.save(new Justification(
                "no contempla la recursividad de datos (listas, árboles, …), de imágenes (fractales), las fugas de Bach, las imágenes de Escher, los mantras budistas,...",
                false,
                recursividadConcept.getId(),
                recursividadIncorrectAnswer.getId()
        ));


        softwareIncorrectAnswer1.addJustification(softwareJustification1);
        softwareIncorrectAnswer2.addJustification(softwareJustification2);
        softwareIncorrectAnswer2.addJustification(softwareJustification3);
        recursividadIncorrectAnswer.addJustification(recursividadJustification1);
        recursividadIncorrectAnswer.addJustification(recursividadJustification2);

        softwareConcept.addAnswer(softwareIncorrectAnswer1);
        softwareConcept.addAnswer(softwareIncorrectAnswer2);
        softwareConcept.addAnswer(softwareIncorrectAnswer3);
        recursividadConcept.addAnswer(recursividadIncorrectAnswer);
        recursividadConcept.addAnswer(recursividadCorrectAnswer);

        conceptRepository.save(softwareConcept);
        conceptRepository.save(recursividadConcept);


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
