export {};

declare global {
  namespace Cypress {
    interface Chainable {
      login(username: string, password: string): Chainable<void>;
      assertSnackbarMessage(message: string): Chainable<void>;
      createConcept(text: string): Chainable<void>;
      createDefinition(text: string, isCorrect: boolean): Chainable<void>;
      createJustification(text: string, isCorrect: boolean): Chainable<void>;
      deleteConcept(text: string): Chainable<void>;
    }
  }
}
