import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from 'src/app/knowledge/utils/dialog-data.interface';

@Component({
  selector: 'app-knowledge-dialog-delete',
  templateUrl: './knowledge-dialog-delete.component.html',
  styleUrls: ['./knowledge-dialog-delete.component.scss'],
})
export class KnowledgeDialogDeleteComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogDeleteComponent>
  ) {}

  onClose(deleteItem: boolean) {
    this.dialogRef.close(deleteItem);
  }
}
