import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActionType } from 'src/app/shared/utils/enums/action-type.enum';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { AnswerStore } from '../../../data-access/answer.store';
import { Concept, ConceptStore } from '../../../data-access/concept.store';
import { MatDialog } from '@angular/material/dialog';
import { KnowledgeDialogDeleteComponent } from '../../../ui/dialog/knowledge-dialog-delete/knowledge-dialog-delete.component';

@Component({
  selector: 'app-knowledge-concept-update-form',
  templateUrl: './knowledge-concept-update-form.component.html',
  styleUrls: ['./knowledge-concept-update-form.component.scss'],
})
export class KnowledgeConceptUpdateFormComponent implements OnInit {
  @Input() concept!: Concept;

  conceptForm: FormGroup = new FormGroup({});

  action: ActionType = ActionType.NONE;

  constructor(
    private formBuilder: FormBuilder,
    private snackbarService: SnackbarService,
    public dialog: MatDialog,
    private conceptStore: ConceptStore,
    private answerStore: AnswerStore
  ) {}

  ngOnInit(): void {
    this.conceptForm = this.formBuilder.group({
      text: [this.concept.text, [Validators.required]],
    });
  }

  onSubmit(action: string, conceptID: number) {
    if (action === ActionType.UPDATE) this.updateConcept(conceptID);

    if (action === ActionType.DELETE) this.deleteConcept(conceptID);
  }

  updateConcept(conceptID: number) {
    const conceptFormValue = this.conceptForm.value;
    this.conceptStore.updateConcept(conceptID, conceptFormValue).subscribe({
      next: () => this.snackbarService.openSnackBar('Concept updated.'),
      error: () => this.snackbarService.openSnackBar('Error updating concept.'),
    });
  }

  deleteConcept(conceptID: number) {
    this.conceptStore.deleteConcept(conceptID).subscribe({
      next: () => {
        this.answerStore.removeConceptID();
        this.snackbarService.openSnackBar('Concept deleted.');
      },
      error: () => this.snackbarService.openSnackBar('Error deleting concept.'),
    });
  }

  openDeleteDialog(conceptID: number) {
    const dialogRef = this.dialog.open(KnowledgeDialogDeleteComponent, {
      width: '20rem',
      data: {
        knowledgeItem: 'concept',
      },
    });

    dialogRef.afterClosed().subscribe(deleteConcept => {
      if (deleteConcept) this.deleteConcept(conceptID);
    });
  }
}
