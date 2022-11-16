beforeEach(() => {
  cy.visit('https://localhost:4200/');
  cy.url().should('contain', 'auth');
});

describe('Login tests', () => {
  it('Logins with an existing user', () => {
    cy.get('[data-cy=login-username-input]').type('teacher');
    cy.get('[data-cy=login-password-input]').type('1234');
    cy.get('[data-cy=login-button]').click();

    cy.url().should('contain', '/');
  });

  it('Throws error when login with non-existing user', () => {
    cy.get('[data-cy=login-username-input]').type('noUser');
    cy.get('[data-cy=login-password-input]').type('noPass');
    cy.get('[data-cy=login-button]').click();

    cy.assertSnackbarMessage('Invalid credentials');

    cy.url().should('contain', '/auth');
  });

  it("Tries to create a user if the email doesn't exit, else thorws an error", () => {
    const stubbedUser = {
      id: 1,
      fullName: null,
      username: 'student',
      email: 'test@test.com',
      roles: ['STUDENT'],
    };
    cy.intercept('POST', '/users/', { statusCode: 201, body: stubbedUser }).as('createUser');

    cy.get('div[role=tab]').eq(1).click();

    cy.get('[data-cy=register-fullname-input]').type('test fullname');
    cy.get('[data-cy=register-username-input]').type('student');
    cy.get('[data-cy=register-email-input]').type('test@test.es');
    cy.get('[data-cy=register-password1-input]').type('1234');
    cy.get('[data-cy=register-password2-input]').type('1234');
    cy.get('[data-cy=register-button]').click();

    cy.url().should('contain', '/');
  });

  it('Throws an error when trying to create a user with a used email', () => {
    cy.get('div[role=tab]').eq(1).click();

    cy.get('[data-cy=register-fullname-input]').type('student fullname');
    cy.get('[data-cy=register-username-input]').type('student');
    cy.get('[data-cy=register-email-input]').type('student@gmail.com');
    cy.get('[data-cy=register-password1-input]').type('1234');
    cy.get('[data-cy=register-password2-input]').type('1234');
    cy.get('[data-cy=register-button]').click();

    cy.assertSnackbarMessage('User with this email or username already exists');
  });
});
