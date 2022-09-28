import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Definition } from 'src/app/knowledge/types/definition.model';
import { DialogData } from 'src/app/knowledge/utils/dialog-data.interface';

@Component({
  selector: 'app-knowledge-dialog-definition-create',
  templateUrl: './knowledge-dialog-definition-create.component.html',
  styleUrls: ['./knowledge-dialog-definition-create.component.scss'],
})
export class KnowledgeDialogDefinitionCreateComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogDefinitionCreateComponent>
  ) {}

  ngOnInit(): void {}

  onDefinitionCreate(newDefinition: Definition): void {
    this.dialogRef.close(newDefinition);
  }
}
