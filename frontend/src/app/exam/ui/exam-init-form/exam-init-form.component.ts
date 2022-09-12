import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-exam-init-form',
  templateUrl: './exam-init-form.component.html',
  styleUrls: ['./exam-init-form.component.scss'],
})
export class ExamInitFormComponent implements OnInit {
  @Output() initExam: EventEmitter<number> = new EventEmitter();

  initExamForm: FormGroup = new FormGroup({});

  constructor() {}

  ngOnInit(): void {}

  onSubmit() {}
}
