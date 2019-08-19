import {Roles} from './roles.model';

export interface User {
  firstname: string;
  lastname: string;
  age: number;
  role: Roles[];
}

export interface TestUser {
  firstname: string;
  lastname: string;
}
