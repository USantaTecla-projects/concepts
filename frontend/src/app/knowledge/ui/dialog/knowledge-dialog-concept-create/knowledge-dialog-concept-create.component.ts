import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Concept } from 'src/app/knowledge/types/concept.model';
import { DialogData } from '../../../utils/dialog-data.interface';

@Component({
  selector: 'app-knowledge-dialog-concept-create',
  templateUrl: './knowledge-dialog-concept-create.component.html',
  styleUrls: ['./knowledge-dialog-concept-create.component.scss'],
})
export class KnowledgeDialogCreateConceptComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogCreateConceptComponent>
  ) {}

  onConceptCreate(newConcept: Concept) {
    this.dialogRef.close(newConcept);
  }
}
