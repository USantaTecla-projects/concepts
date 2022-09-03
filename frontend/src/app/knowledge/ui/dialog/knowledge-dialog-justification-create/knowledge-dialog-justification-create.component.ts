import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from 'src/app/knowledge/utils/dialog-data.interface';

@Component({
  selector: 'app-knowledge-dialog-justification-create',
  templateUrl: './knowledge-dialog-justification-create.component.html',
  styleUrls: ['./knowledge-dialog-justification-create.component.scss'],
})
export class KnowledgeDialogJustificationCreateComponent implements OnInit {
  justificationForm: FormGroup = new FormGroup({});

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<KnowledgeDialogJustificationCreateComponent>,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.justificationForm = this.formBuilder.group({
      text: [null, [Validators.required]],
      correct: [null],
      error: [null, [Validators.required]],
    });
  }

  toggleErrorField(checked: boolean) {
    const errorControl = this.justificationForm.controls['error'];
    if (checked) {
      errorControl.disable();
      this.justificationForm.controls['error'].clearValidators();
    } else {
      errorControl.enable();
      this.justificationForm.controls['error'].setValidators(Validators.required);
    }
    this.justificationForm.controls['error'].setValue('');
  }

  onSubmit() {
    const justificationFormValue = this.justificationForm.value;
    this.dialogRef.close(justificationFormValue);
  }

  onClose() {
    this.dialogRef.close();
  }
}
