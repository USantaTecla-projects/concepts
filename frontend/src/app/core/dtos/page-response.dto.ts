import { Answer } from '../models/answer.model';
import { Concept } from '../models/concept.model';
import { Justification } from '../models/justification.model';

export interface Page<T> {
  content: T[];
}
