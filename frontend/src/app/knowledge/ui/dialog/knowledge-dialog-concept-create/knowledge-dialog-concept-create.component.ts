import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Concept } from 'src/app/knowledge/data-access/concept.store';
import { DialogData } from '../../../utils/dialog-data.interface';

@Component({
  selector: 'app-knowledge-dialog-concept-create',
  templateUrl: './knowledge-dialog-concept-create.component.html',
  styleUrls: ['./knowledge-dialog-concept-create.component.scss'],
})
export class KnowledgeDialogCreateConceptComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogCreateConceptComponent>
  ) {}

  ngOnInit(): void {}

  onConceptCreate(newConcept: Concept) {
    this.dialogRef.close(newConcept);
  }
}
