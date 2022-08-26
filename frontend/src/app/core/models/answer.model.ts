import { Justification } from './justification.model';

export interface Answer {
  id: number;
  text: string;
  correct: boolean;
  justifications: Justification[];
}
