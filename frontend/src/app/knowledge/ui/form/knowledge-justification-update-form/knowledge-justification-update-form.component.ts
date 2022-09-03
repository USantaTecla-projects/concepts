import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Justification, JustificationStore } from 'src/app/knowledge/data-access/justification.store';
import { KnowledgeDialogDeleteComponent } from 'src/app/knowledge/ui/dialog/knowledge-dialog-delete/knowledge-dialog-delete.component';
import { ActionType } from 'src/app/shared/utils/enums/action-type.enum';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';

@Component({
  selector: 'app-knowledge-justification-update-form',
  templateUrl: './knowledge-justification-update-form.component.html',
  styleUrls: ['./knowledge-justification-update-form.component.scss'],
})
export class KnowledgeJustificationUpdateFormComponent implements OnInit {
  @Input() justification!: Justification;

  justificationForm: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private snackbarService: SnackbarService,
    public dialog: MatDialog,
    private justificationStore: JustificationStore
  ) {}

  ngOnInit(): void {
    this.justificationForm = this.formBuilder.group({
      text: [this.justification.text, [Validators.required]],
      correct: [this.justification.correct],
      error: [
        { value: !this.justification.correct ? this.justification.error : '', disabled: this.justification.correct },
      ],
    });
  }

  onSubmit(action: string, justificationID: number) {
    if (action === ActionType.UPDATE) this.updateJustification(justificationID);
    if (action === ActionType.DELETE) this.deleteJustification(justificationID);
  }

  updateJustification(justificationID: number) {
    const justificationFormValue = this.justificationForm.value;

    this.justificationStore.updateJustification(justificationID, justificationFormValue).subscribe({
      next: () => this.snackbarService.openSnackBar('Justification updated.'),
      error: () => this.snackbarService.openSnackBar('Error updating justification.'),
    });
  }

  deleteJustification(justificationID: number) {
    this.justificationStore.deleteJustification(justificationID).subscribe({
      next: () => this.snackbarService.openSnackBar('Concept deleted.'),
      error: () => this.snackbarService.openSnackBar('Error deleting concept.'),
    });
  }

  toggleErrorField(checked: boolean) {
    const errorControl = this.justificationForm.controls['error'];
    checked ? errorControl.disable() : errorControl.enable();
    this.justificationForm.controls['error'].setValue('');
  }

  openDeleteDialog(justificationID: number) {
    const dialogRef = this.dialog.open(KnowledgeDialogDeleteComponent, {
      width: '20rem',
      data: {
        knowledgeItem: 'justification',
      },
    });

    dialogRef.afterClosed().subscribe(deleteConcept => {
      if (deleteConcept) this.deleteJustification(justificationID);
    });
  }
}
