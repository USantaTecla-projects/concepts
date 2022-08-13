import { LoginDTO } from '../dtos/login.dto';
import { FormValues } from '../interfaces/form-values.interface';

export function mapFormValuesToData<T>(formValues: FormValues): T {
  const keys = Object.keys(formValues);
  const values = Object.values(formValues);
  return keys.reduce((acc, key, i) => ({ ...acc, [key.toLowerCase()]: values[i] }), {}) as T;
}
