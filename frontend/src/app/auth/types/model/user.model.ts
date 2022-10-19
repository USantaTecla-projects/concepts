import { Role } from '../enum/role.enum';

export interface User {
  id: number;
  fullName: string;
  email: string;
  username: string;
  roles: Role[];
}
