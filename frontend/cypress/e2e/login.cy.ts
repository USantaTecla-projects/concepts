describe('Login tests', () => {
  beforeEach(() => {
    cy.visit('https://localhost:4200/');
    cy.url().should('contain', 'auth');
  });

  it('Logins with an existing user', () => {
    // Login
    cy.get('[data-cy=login-username-input]').type('teacher');
    cy.get('[data-cy=login-password-input]').type('1234');
    cy.get('[data-cy=login-button]').click();

    // Check that we are on the home page
    cy.url().should('contain', '/');
  });

  it('Throws error when login with non-existing user', () => {
    // Login
    cy.get('[data-cy=login-username-input]').type('noUser');
    cy.get('[data-cy=login-password-input]').type('noPass');
    cy.get('[data-cy=login-button]').click();

    // Chekc the error
    cy.assertSnackbarMessage('Invalid credentials');
    cy.url().should('contain', '/auth');
  });

  it('Logouts correctly deleting local storage data', () => {
    cy.login('teacher', '1234');

    // Check local storage data
    expect(localStorage.getItem('auth_data')).not.to.be.undefined;
    cy.get('[data-cy=logout-button]').click();
    expect(localStorage.getItem('auth_data')).to.be.null;
  });

  it("Tries to create a user if the email doesn't exit, else thorws an error", () => {
    // The system cannot delete users at the moment, so stub the response
    const stubbedUser = {
      id: 1,
      fullName: null,
      username: 'student',
      email: 'test@test.com',
      roles: ['STUDENT'],
    };
    cy.intercept('POST', '/users/', { statusCode: 201, body: stubbedUser }).as('createUser');

    // Register a new user
    cy.get('div[role=tab]').eq(1).click();
    cy.get('[data-cy=register-fullname-input]').type('test fullname');
    cy.get('[data-cy=register-username-input]').type('student');
    cy.get('[data-cy=register-email-input]').type('test@test.es');
    cy.get('[data-cy=register-password1-input]').type('1234');
    cy.get('[data-cy=register-password2-input]').type('1234');
    cy.get('[data-cy=register-button]').click();

    // Check that he has been registered and logged in
    cy.url().should('contain', '/');
  });

  it('Throws an error when trying to create a user with a used email', () => {
    // Register user with an already used email
    cy.get('div[role=tab]').eq(1).click();
    cy.get('[data-cy=register-fullname-input]').type('student fullname');
    cy.get('[data-cy=register-username-input]').type('student');
    cy.get('[data-cy=register-email-input]').type('student@gmail.com');
    cy.get('[data-cy=register-password1-input]').type('1234');
    cy.get('[data-cy=register-password2-input]').type('1234');
    cy.get('[data-cy=register-button]').click();

    // Check the error
    cy.assertSnackbarMessage('User with this email or username already exists');
    cy.url().should('contain', '/auth');
  });

  it("Doesn't allow Student users to visit Teacher pages", () => {
    cy.login('student', '1234');

    // Check that Student user cannot access to Teacher pages
    cy.get('navbar-knowledge-button').should('not.exist');
    cy.visit('https://localhost:4200/knowledge');
    cy.url().should('not.contain', '/knowledge');
    cy.get('navbar-correction-button').should('not.exist');
    cy.visit('https://localhost:4200/correction');
    cy.url().should('not.contain', '/correction');
  });
});
