describe('Knowledge tests', () => {
  beforeEach(() => {
    cy.login('teacher', '1234');
    cy.get('[data-cy=navbar-knowledge-button]').click();
  });

  before(() => {
    cy.login('teacher', '1234');
    cy.get('[data-cy=navbar-knowledge-button]').click();

    // Delete all data
    cy.get('body').then(body => {
      if (body.find('[data-cy=knowledge-concept-title]').length > 0) {
        cy.get('[data-cy=knowledge-concept-title]').then(concepts => {
          let countOfElements = concepts.length;
          while (countOfElements) {
            cy.get('[data-cy=knowledge-concept-title]').eq(0).click();
            cy.get('[data-cy=knowledge-delete-concept-button]').click();
            cy.get('[data-cy=dialog-confirm-button]').click();
            cy.assertSnackbarMessage('Concept deleted.');
            countOfElements--;
          }
        });
      }
    });
  });

  it('Creates a concept', () => {
    // Create concept
    cy.createConcept('Concept Test');
    cy.get('[data-cy=knowledge-concept-title]').contains('Test').should('exist');
  });

  it('Updates a concept', () => {
    // Create concept
    cy.createConcept('Concept Test');
    cy.get('[data-cy=knowledge-concept-title]').contains('Concept Test').should('exist');

    //Update concept
    cy.get('[data-cy=knowledge-concept-title]').contains('Concept Test').click();
    cy.get('[data-cy=knowledge-concept-update-input]').last().clear();
    cy.get('[data-cy=knowledge-concept-update-input]').type('Updated Concept');
    cy.get('[data-cy=knowledge-concept-update-button]').click();
    cy.get('[data-cy=knowledge-concept-title]').contains('Updated Concept').should('exist');
    cy.assertSnackbarMessage('Concept updated.');

    // Redo
    cy.get('[data-cy=knowledge-concept-title]').contains('Updated Concept').click();
    cy.get('[data-cy=knowledge-concept-update-input]').last().clear();
    cy.get('[data-cy=knowledge-concept-update-input]').type('Concept Test');
    cy.get('[data-cy=knowledge-concept-update-button]').click();
    cy.get('[data-cy=knowledge-concept-title]').contains('Concept Test').should('exist');
    cy.assertSnackbarMessage('Concept updated.');
  });

  it('Creates a definition', () => {
    // Create concept
    cy.createConcept('Concept Test');
    cy.get('[data-cy=knowledge-concept-title]').contains('Concept Test').should('exist');

    // Create definition
    cy.get('[data-cy=knowledge-concept-title]').contains('Concept Test').click();
    cy.createDefinition('Definition Test', false);
    cy.get('[data-cy=knowledge-definition-title]').contains('Definition Test').should('exist');
  });

  it('Updates a definition', () => {
    // Create concept
    cy.createConcept('Concept Test');
    cy.get('[data-cy=knowledge-concept-title]').contains('Concept Test').should('exist');

    // Create definition
    cy.get('[data-cy=knowledge-concept-title]').contains('Concept Test').click();
    cy.createDefinition('Definition Test', false);
    cy.get('[data-cy=knowledge-definition-title]').contains('Definition Test').should('exist');

    // Update definition
    cy.get('[data-cy=knowledge-definition-title]').contains('Definition Test').click();
    cy.get('[data-cy=knowledge-definition-update-input]').clear();
    cy.get('[data-cy=knowledge-definition-update-input]').type('Updated Definition');
    cy.get('[data-cy=knowledge-definition-update-button]').click();
    cy.get('[data-cy=knowledge-definition-title]').contains('Updated Definition').should('exist');
    cy.assertSnackbarMessage('Definition updated.');
    cy.get('[data-cy=knowledge-definition-title]').contains('Updated Definition').click();
  });

  it('Creates a justification', () => {
    // Create concept
    cy.createConcept('Concept Test');
    cy.get('[data-cy=knowledge-concept-title]').contains('Concept Test').should('exist');

    // Create definition
    cy.get('[data-cy=knowledge-concept-title]').contains('Concept Test').click();
    cy.createDefinition('Definition Test', false);
    cy.get('[data-cy=knowledge-definition-title]').contains('Definition Test').should('exist');

    // Create Jusitification
    cy.get('[data-cy=knowledge-definition-title]').contains('Definition Test').click();
    cy.createJustification('Justification Test', false);
  });

  it('Updates a justification', () => {
    // Create concept
    cy.createConcept('Concept Test');
    cy.get('[data-cy=knowledge-concept-title]').contains('Concept Test').should('exist');

    // Create definition
    cy.get('[data-cy=knowledge-concept-title]').contains('Concept Test').click();
    cy.createDefinition('Definition Test', false);
    cy.get('[data-cy=knowledge-definition-title]').contains('Definition Test').should('exist');

    // Create jusitification
    cy.get('[data-cy=knowledge-definition-title]').contains('Definition Test').click();
    cy.createJustification('Justification Test', false);

    // Update justification
    cy.get('[data-cy=knowledge-justification-title]').contains('Justification Test').click();
    cy.get('[data-cy=knowledge-justification-update-input]').clear();
    cy.get('[data-cy=knowledge-justification-update-input]').type('Updated Justification');
    cy.get('[data-cy=knowledge-justification-update-button]').click();
    cy.get('[data-cy=knowledge-justification-title]').contains('Updated Justification').should('exist');
    cy.assertSnackbarMessage('Justification updated.');
    cy.get('[data-cy=knowledge-justification-title]').contains('Updated Justification').click();
  });

  afterEach(() => {
    cy.deleteConcept('Concept Test');
  });
});
