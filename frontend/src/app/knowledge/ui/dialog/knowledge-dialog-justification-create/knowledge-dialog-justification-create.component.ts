import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Justification } from 'src/app/knowledge/types/justification.model';
import { DialogData } from 'src/app/knowledge/utils/dialog-data.interface';

@Component({
  selector: 'app-knowledge-dialog-justification-create',
  templateUrl: './knowledge-dialog-justification-create.component.html',
  styleUrls: ['./knowledge-dialog-justification-create.component.scss'],
})
export class KnowledgeDialogJustificationCreateComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogJustificationCreateComponent>
  ) {}

  ngOnInit(): void {}

  onJustificationCreate(newJustification: Justification) {
    this.dialogRef.close(newJustification);
  }
}
