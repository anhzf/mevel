declare module 'query-types' {
  import type { Handler } from 'express';
  export const middleware: () => Handler;
}
