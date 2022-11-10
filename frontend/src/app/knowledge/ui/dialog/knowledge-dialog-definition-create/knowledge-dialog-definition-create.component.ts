import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from 'src/app/knowledge/utils/dialog-data.interface';
import { Definition } from 'src/app/shared/types/definition/definition.model';

@Component({
  selector: 'app-knowledge-dialog-definition-create',
  templateUrl: './knowledge-dialog-definition-create.component.html',
  styleUrls: ['./knowledge-dialog-definition-create.component.scss'],
})
export class KnowledgeDialogDefinitionCreateComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogDefinitionCreateComponent>
  ) {}

  onDefinitionCreate(newDefinition: Definition): void {
    this.dialogRef.close(newDefinition);
  }
}
