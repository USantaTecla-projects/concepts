import { Justification } from './justification.model';

export interface Answer {
  id: number;
  text: string;
  isCorrect: boolean;
  justifications: Justification[];
}
