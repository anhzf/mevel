export const once = <T>(fn: () => T) => {
  let result: T | undefined;
  return () => result === undefined ?
    (result = fn()) :
    result;
};
