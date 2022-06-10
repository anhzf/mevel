interface FactoryParams<T> {
  [k: string]: unknown;
  _?: T;
}

export type Factory<T = unknown, Params extends FactoryParams<T> = FactoryParams<T>> = (params?: Params) => T;

export type Providers<T = unknown> = Record<string, Factory<T>>;
