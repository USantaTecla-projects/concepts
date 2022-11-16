Cypress.Commands.add('assertSnackbarMessage', (message: string) => {
  cy.get('simple-snack-bar').should('exist');
  cy.get('simple-snack-bar span').contains(message);
});
