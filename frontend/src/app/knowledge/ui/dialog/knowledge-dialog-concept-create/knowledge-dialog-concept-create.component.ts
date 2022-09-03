import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from '../../../utils/dialog-data.interface';

@Component({
  selector: 'app-knowledge-dialog-concept-create',
  templateUrl: './knowledge-dialog-concept-create.component.html',
  styleUrls: ['./knowledge-dialog-concept-create.component.scss'],
})
export class KnowledgeDialogCreateConceptComponent implements OnInit {
  conceptForm: FormGroup = new FormGroup({});

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogCreateConceptComponent>,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.conceptForm = this.formBuilder.group({
      text: [null, [Validators.required]],
    });
  }

  onSubmit() {
    const conceptFormValue = this.conceptForm.value;
    this.dialogRef.close(conceptFormValue);
  }

  onClose() {
    this.dialogRef.close();
  }
}
