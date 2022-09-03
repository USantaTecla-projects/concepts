import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from 'src/app/knowledge/utils/dialog-data.interface';

@Component({
  selector: 'app-knowledge-dialog-answer-create',
  templateUrl: './knowledge-dialog-answer-create.component.html',
  styleUrls: ['./knowledge-dialog-answer-create.component.scss'],
})
export class KnowledgeDialogAnswerCreateComponent implements OnInit {
  answerForm: FormGroup = new FormGroup({});

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogAnswerCreateComponent>,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.answerForm = this.formBuilder.group({
      text: [null, [Validators.required]],
      correct: [false],
    });
  }

  onSubmit() {
    const answerFormValue = this.answerForm.value;
    this.dialogRef.close(answerFormValue);
  }

  onClose() {
    this.dialogRef.close();
  }
}
