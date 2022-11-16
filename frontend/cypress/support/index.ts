export {};

declare global {
  namespace Cypress {
    interface Chainable {
      login(username: string, password: string): Chainable<void>;
      assertSnackbarMessage(message: string): Chainable<void>;
    }
  }
}
