import { inspect, InspectOptions } from 'util';

export const print = (arg: any, opts?: InspectOptions) => console.log(inspect(arg, { depth: null, colors: true, getters: true, ...opts }));
