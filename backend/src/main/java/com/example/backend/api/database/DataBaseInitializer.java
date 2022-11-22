package com.example.backend.api.database;


import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import com.example.backend.api.resources.exam.ExamRepository;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.user.UserRepository;
import com.example.backend.api.resources.user.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DataBaseInitializer implements CommandLineRunner {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;
    private final JustificationRepository justificationRepository;

    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataBaseInitializer(ConceptRepository conceptRepository, DefinitionRepository definitionRepository, JustificationRepository justificationRepository, ExamRepository examRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.justificationRepository = justificationRepository;
        this.examRepository = examRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {




        userRepository
                .save(new User(
                        1L,
                        "Cristian de Gracia Nuero",
                        "teacher",
                        "teacher@gmail.com",
                        passwordEncoder.encode("1234"),
                        List.of("TEACHER", "STUDENT"))
                );


        userRepository
                .save(new User(2L, "Cristian de Gracia Nuero", "student", "student@gmail.com", passwordEncoder.encode("1234"), List.of("STUDENT")));


        Concept softwareConcept = conceptRepository.save(new Concept("el software."));

//        for (int i = 0; i <5 ; i++) {
//            Definition definition = new Definition();
//            softwareConcept.addDefinition(definition);
//            definitionRepository.save(definition);
//            conceptRepository.save(softwareConcept);
//
//        }

        Concept recursividadConcept = conceptRepository.save(new Concept("la recursividad."));

        Definition softwareIncorrectDefinition = definitionRepository.save(new Definition(
                "el conjunto de los programas.",
                false,
                softwareConcept.getId()));
        Definition softwareCorrectDefinition = definitionRepository.save(new Definition(
                "la parte lógica de un sistema informático, o sea sin contemplar el hardware.",
                true,
                softwareConcept.getId()));


        Definition recursividadIncorrectDefinition = definitionRepository.save(new Definition(
                "la característica de las funciones que se llaman a sí mismas.",
                false,
                recursividadConcept.getId()));
        Definition recursividadCorrectDefinition = definitionRepository.save(new Definition(
                "la característica de definiciones que se autorreferencian, directa o indirectamente.",
                true,
                recursividadConcept.getId()));

        Justification softwareJustification1 = justificationRepository.save(new Justification(
                "(HTML, CSS, …), … y otros artefactos necesarios en el software…",
                false,
                softwareConcept.getId(),
                softwareIncorrectDefinition.getId()
        ));

        Justification softwareJustification2 = justificationRepository.save(new Justification(
                "la definición es demasiado permisiva porque incluye firmware que no es software.",
                true,
                softwareConcept.getId(),
                softwareCorrectDefinition.getId()
        ));


        Justification recursividadJustification1 = justificationRepository.save(new Justification(
                " no contempla la recursividad mutua.",
                false,
                recursividadConcept.getId(),
                recursividadIncorrectDefinition.getId()
        ));

        Justification recursividadJustification2 = justificationRepository.save(new Justification(
                "no contempla la recursividad de datos (listas, árboles, …)",
                false,
                recursividadConcept.getId(),
                recursividadIncorrectDefinition.getId()
        ));


        softwareIncorrectDefinition.addJustification(softwareJustification1);
        softwareCorrectDefinition.addJustification(softwareJustification2);

        recursividadIncorrectDefinition.addJustification(recursividadJustification1);
        recursividadIncorrectDefinition.addJustification(recursividadJustification2);

        softwareConcept.addDefinition(softwareIncorrectDefinition);
        softwareConcept.addDefinition(softwareCorrectDefinition);

        recursividadConcept.addDefinition(recursividadIncorrectDefinition);
        recursividadConcept.addDefinition(recursividadCorrectDefinition);

        conceptRepository.save(softwareConcept);
        conceptRepository.save(recursividadConcept);


//        for (int i = 0; i <99 ; i++) {
//            Exam exam = new Exam();
//            exam.setUserID(1L);
//            examRepository.save(exam);
//        }
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
        Definition correctDefinition = createAnswer(correctAnswerText, true, conceptId);
        Definition incorrectDefinition = createAnswer(incorrectAnswerText, false, conceptId);

        // Create justifications for correct Answer
        long correctAnswerId = correctDefinition.getId();
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
        long incorrectAnswerId = incorrectDefinition.getId();
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
        correctDefinition.addJustification(correctJustificationForCorrectAnswer);
        correctDefinition.addJustification(incorrectJustificationForCorrectAnswer);

        incorrectDefinition.addJustification(correctJustificationForIncorrectAnswer);
        incorrectDefinition.addJustification(incorrectJustificationForIncorrectAnswer);

        definitionRepository.save(correctDefinition);
        definitionRepository.save(incorrectDefinition);

        // Add answers to concept
        concept.addDefinition(correctDefinition);
        concept.addDefinition(incorrectDefinition);

        conceptRepository.save(concept);
    }

    private Definition createAnswer(String answerText, boolean isCorrect, long conceptId) {
        Definition definition = new Definition(answerText, isCorrect);
        definition.setConceptID(conceptId);
        return definitionRepository.save(definition);
    }

    private Justification createJustification(String justificationText, boolean isCorrect, long conceptId, long answerId) {
        Justification justification = new Justification(justificationText, isCorrect, "explanation");
        justification.setConceptID(conceptId);
        justification.setDefinitionID(answerId);
        return justificationRepository.save(justification);
    }


}
