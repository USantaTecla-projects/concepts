import { Answer } from './answer.model';

export interface Concept {
  id: number;
  text: string;
  answers?: Answer[];
}
