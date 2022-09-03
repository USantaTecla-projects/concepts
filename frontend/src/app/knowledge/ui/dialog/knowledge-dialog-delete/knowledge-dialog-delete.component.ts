import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from 'src/app/knowledge/utils/dialog-data.interface';

@Component({
  selector: 'app-knowledge-dialog-delete',
  templateUrl: './knowledge-dialog-delete.component.html',
  styleUrls: ['./knowledge-dialog-delete.component.scss'],
})
export class KnowledgeDialogDeleteComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogDeleteComponent>
  ) {}

  ngOnInit(): void {}

  onClose(deleteItem: boolean) {
    this.dialogRef.close(deleteItem);
  }
}
