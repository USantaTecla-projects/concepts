import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Answer } from 'src/app/knowledge/data-access/model/answer.model';
import { DialogData } from 'src/app/knowledge/utils/dialog-data.interface';

@Component({
  selector: 'app-knowledge-dialog-answer-create',
  templateUrl: './knowledge-dialog-answer-create.component.html',
  styleUrls: ['./knowledge-dialog-answer-create.component.scss'],
})
export class KnowledgeDialogAnswerCreateComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogAnswerCreateComponent>
  ) {}

  ngOnInit(): void {}

  onAnswerCreate(newAnswer: Answer): void {
    this.dialogRef.close(newAnswer);
  }
}
