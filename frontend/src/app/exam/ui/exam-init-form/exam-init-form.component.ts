import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GenerateExamData } from '../../../shared/types/exam/dto/create-exam.dto';

@Component({
  selector: 'app-exam-init-form',
  templateUrl: './exam-init-form.component.html',
  styleUrls: ['./exam-init-form.component.scss'],
})
export class ExamInitFormComponent implements OnInit {
  @Output() startExam: EventEmitter<GenerateExamData> = new EventEmitter();

  startExamForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.startExamForm = this.formBuilder.group({
      numberOfQuestions: [null, [Validators.required, Validators.min(1)]],
    });
  }

  onSubmit() {
    const startExamFormValue = this.startExamForm.value;
    if (startExamFormValue) {
      const generateExamData: GenerateExamData = { ...startExamFormValue };
      this.startExam.emit(generateExamData);
    }
  }
}
