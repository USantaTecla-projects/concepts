import { Component, Inject, Input, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface DialogData {
  knowledgeItem: 'concept' | 'answer' | 'justification';
}

@Component({
  selector: 'app-knowledge-delete-dialog',
  templateUrl: './knowledge-delete-dialog.component.html',
  styleUrls: ['./knowledge-delete-dialog.component.scss'],
})
export class KnowledgeDeleteDialogComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDeleteDialogComponent>
  ) {}

  ngOnInit(): void {}

  onClose(deleteItem: boolean) {
    this.dialogRef.close(deleteItem);
  }
}
