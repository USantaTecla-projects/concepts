package com.example.backend.api.resources.exam.resources.type.factory;

import com.example.backend.api.resources.exam.resources.type.factory.concrete.Type3Factory;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.exam.resources.type.factory.concrete.Type0Factory;
import com.example.backend.api.resources.exam.resources.type.factory.concrete.Type1Factory;
import com.example.backend.api.resources.exam.resources.type.factory.concrete.Type2Factory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeFactoryProvider {

    private final ConceptRepository conceptRepository;

    private final DefinitionRepository definitionRepository;

    private final JustificationRepository justificationRepository;

    public TypeFactoryProvider(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            JustificationRepository justificationRepository
    ) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.justificationRepository = justificationRepository;
    }

    public List<TypeFactory> getTypeAbstractFactories() {
        return List.of(
                new Type0Factory(conceptRepository),
                new Type1Factory(conceptRepository, definitionRepository),
                new Type2Factory(conceptRepository, definitionRepository),
                new Type3Factory(conceptRepository, definitionRepository, justificationRepository)
        );
    }

}
