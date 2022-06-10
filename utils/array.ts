export const chunk = <T>(arr: T[], size: number) => Array.from(
  { length: size },
  (v, i) => arr.slice(i * size, i * size + size),
);
