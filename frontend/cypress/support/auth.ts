Cypress.Commands.add('login', (username: string, password: string) => {
  cy.visit('https://localhost:4200/');
  cy.url().should('contain', 'auth');

  cy.get('[data-cy=login-username-input]').type(username);
  cy.get('[data-cy=login-password-input]').type(password);
  cy.get('[data-cy=login-button]').click();

  cy.url().should('contain', '/');
});
