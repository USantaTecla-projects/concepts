package com.example.backend.api.database;


import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
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
    private final DefinitionRepository definitionRepository;
    private final JustificationRepository justificationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataBaseInitializer(ConceptRepository conceptRepository, DefinitionRepository definitionRepository, JustificationRepository justificationRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
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

        Definition softwareIncorrectDefinition1 = definitionRepository.save(new Definition(
                "el conjunto de los programas.",
                false,
                softwareConcept.getId()));
        Definition softwareIncorrectDefinition2 = definitionRepository.save(new Definition(
                "la parte lógica de un sistema informático, o sea sin contemplar el hardware.",
                false,
                softwareConcept.getId()));
        Definition softwareIncorrectDefinition3 = definitionRepository.save(new Definition(
                "la información que suministra el desarrollador para manipular la información del usuario.",
                false,
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
                "no contempla los scripts de bases de datos (DDL, SQL), ficheros de configuración, imágenes (*.bmp, *.jpg, …) del interfaz gráfico de usuario, ficheros de datos (JSON, XML,DTD, XML Schema, …), de publicación (HTML, CSS, …), … y otros artefactos necesarios en el software que no son para programar, son configurar, publicar, …",
                false,
                softwareConcept.getId(),
                softwareIncorrectDefinition1.getId()
        ));

        Justification softwareJustification2 = justificationRepository.save(new Justification(
                "la definición es demasiado permisiva porque incluye firmware que no es software.",
                true,
                softwareConcept.getId(),
                softwareIncorrectDefinition2.getId()
        ));

        Justification softwareJustification3 = justificationRepository.save(new Justification(
                "la definición es demasiado permisiva porque los datos de usuario (todos los ficheros generados por el software), no son hardware ni son software y están siendo incluidos en el software.",
                true,
                softwareConcept.getId(),
                softwareIncorrectDefinition2.getId()
        ));

        Justification recursividadJustification1 = justificationRepository.save(new Justification(
                " no contempla la recursividad mutua.",
                false,
                recursividadConcept.getId(),
                recursividadIncorrectDefinition.getId()
        ));

        Justification recursividadJustification2 = justificationRepository.save(new Justification(
                "no contempla la recursividad de datos (listas, árboles, …), de imágenes (fractales), las fugas de Bach, las imágenes de Escher, los mantras budistas,...",
                false,
                recursividadConcept.getId(),
                recursividadIncorrectDefinition.getId()
        ));


        softwareIncorrectDefinition1.addJustification(softwareJustification1);
        softwareIncorrectDefinition2.addJustification(softwareJustification2);
        softwareIncorrectDefinition2.addJustification(softwareJustification3);
        recursividadIncorrectDefinition.addJustification(recursividadJustification1);
        recursividadIncorrectDefinition.addJustification(recursividadJustification2);

        softwareConcept.addDefinition(softwareIncorrectDefinition1);
        softwareConcept.addDefinition(softwareIncorrectDefinition2);
        softwareConcept.addDefinition(softwareIncorrectDefinition3);
        recursividadConcept.addDefinition(recursividadIncorrectDefinition);
        recursividadConcept.addDefinition(recursividadCorrectDefinition);

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
