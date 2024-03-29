import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from 'src/app/knowledge/utils/dialog-data.interface';
import { Justification } from 'src/app/shared/types/justification/justification.model';

@Component({
  selector: 'app-knowledge-dialog-justification-create',
  templateUrl: './knowledge-dialog-justification-create.component.html',
  styleUrls: ['./knowledge-dialog-justification-create.component.scss'],
})
export class KnowledgeDialogJustificationCreateComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogJustificationCreateComponent>
  ) {}

  onJustificationCreate(newJustification: Justification) {
    this.dialogRef.close(newJustification);
  }
}
