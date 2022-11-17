import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    supportFile: 'cypress/support/commands.ts',
    viewportHeight: 1000,
    viewportWidth: 1600,
  },
});
