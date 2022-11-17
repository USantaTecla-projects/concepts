describe('Exam tests', () => {
  beforeEach(() => {
    cy.login('teacher', '1234');
    cy.get('[data-cy=navbar-exam-button]').click();
  });

  before(() => {
    cy.login('teacher', '1234');
    cy.get('[data-cy=navbar-knowledge-button]').click();

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
  });

  it('Replies and corrects an Exam', () => {
    cy.get('[data-cy=exam-init-input]').type('4');
    cy.get('[data-cy=exam-init-start-exam-button]').click();
    cy.get('app-exam-question-renderer').then(elements => expect(elements.length).to.eq(4));
    cy.get('[data-cy=exan-deliver-button]').click();
    cy.get('[data-cy=exam-end-button]').click();

    cy.get('[data-cy=navbar-correction-button]').click();
    cy.get('[data-cy=correction-correct-button]').first().click();
    cy.get('[data-cy=correct-question-button]').click({ multiple: true });
    cy.get('[data-cy=correction-correct-exam-button]').click();
    cy.assertSnackbarMessage('Exam corrected!');

    cy.get('[data-cy=navbar-profile-button]').click();
    cy.get('app-exam-history-item').should('exist');
    cy.get('.mark').contains('10').should('exist');
  });

  after(() => {
    cy.get('[data-cy=navbar-knowledge-button]').click();

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
});
