import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActionType } from 'src/app/shared/utils/enums/action-type.enum';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { AnswerStore } from '../../data-access/answer.store';
import { Concept, ConceptStore } from '../../data-access/concept.store';
import { MatDialog } from '@angular/material/dialog';
import { KnowledgeDeleteDialogComponent } from '../knowledge-delete-dialog/knowledge-delete-dialog.component';

@Component({
  selector: 'app-knowledge-concept-list-form',
  templateUrl: './knowledge-concept-list-form.component.html',
  styleUrls: ['./knowledge-concept-list-form.component.scss'],
})
export class KnowledgeConceptListFormComponent implements OnInit {
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
    if (action === ActionType.UPDATE) this.saveConcept(conceptID);

    if (action === ActionType.DELETE) this.deleteConcept(conceptID);
  }

  saveConcept(conceptID: number) {
    const conceptFormValue = this.conceptForm.value;
    this.conceptStore.saveConcept(conceptID, conceptFormValue).subscribe({
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
    const dialogRef = this.dialog.open(KnowledgeDeleteDialogComponent, {
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
