import { Role } from '../enum/role.enum';

export interface User {
  id: number;
  username: string;
  email: string;
  roles: Role[];
}
