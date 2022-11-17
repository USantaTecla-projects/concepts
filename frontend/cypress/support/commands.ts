Cypress.Commands.add('assertSnackbarMessage', (message: string) => {
  cy.get('simple-snack-bar').should('exist');
  cy.get('simple-snack-bar span').contains(message);
  cy.get('simple-snack-bar button').click();
});

Cypress.Commands.add('login', (username: string, password: string) => {
  cy.visit('https://localhost:4200/auth');
  cy.url().should('contain', 'auth');

  cy.get('[data-cy=login-username-input]').type(username);
  cy.get('[data-cy=login-password-input]').type(password);
  cy.get('[data-cy=login-button]').click();

  cy.url().should('contain', '/');
});

Cypress.Commands.add('createConcept', (text: string) => {
  cy.get('[data-cy=knowledge-create-concept-button]').click();
  cy.get('app-knowledge-concept-create-form').should('exist');
  cy.get('[data-cy=knowledge-concept-text-input]').should('be.empty');
  cy.get('[data-cy=knowledge-concept-save-button]').should('be.disabled');
  cy.get('[data-cy=knowledge-concept-text-input]').type(text);
  cy.get('[data-cy=knowledge-concept-save-button]').click();
  cy.assertSnackbarMessage('Concept created.');
});

Cypress.Commands.add('createDefinition', (text: string, isCorrect: boolean) => {
  cy.get('[data-cy=knowledge-create-definition-button]').click();
  cy.get('app-knowledge-definition-create-form').should('exist');
  cy.get('[data-cy=knowledge-definition-text-input]').should('be.empty');
  cy.get('[data-cy=knowledge-definition-save-button]').should('be.disabled');
  cy.get('[data-cy=knowledge-definition-text-input]').type(text);
  if (isCorrect) cy.get('[data-cy=knowledge-definition-correct-checkbox] input').check();
  cy.get('[data-cy=knowledge-definition-save-button]').click();
  cy.assertSnackbarMessage('Definition created.');
});
Cypress.Commands.add('createJustification', (text: string, isCorrect: boolean) => {
  cy.get('[data-cy=knowledge-create-justification-button]').click();
  cy.get('app-knowledge-justification-create-form').should('exist');
  cy.get('[data-cy=knowledge-justification-text-input]').should('be.empty');
  cy.get('[data-cy=knowledge-justification-save-button]').should('be.disabled');
  cy.get('[data-cy=knowledge-justification-text-input]').type(text);

  if (isCorrect) {
    cy.get('mat-dialog-container mat-checkbox').first().click();
  } else {
    cy.get('[data-cy=knowledge-justifiction-error-input]').type('Justification Error');
  }

  cy.get('[data-cy=knowledge-justification-save-button]').click();
  cy.assertSnackbarMessage('Justification created.');
});
Cypress.Commands.add('deleteConcept', (text: string) => {
  cy.get('[data-cy=knowledge-concept-title]').contains(text).click();
  cy.get('[data-cy=knowledge-delete-concept-button]').click();
  cy.get('[data-cy=dialog-confirm-button]').click();
  cy.assertSnackbarMessage('Concept deleted.');
});
