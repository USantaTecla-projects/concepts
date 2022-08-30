import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { AnswerStore } from '../../data-access/answer.store';
import { Concept, ConceptStore } from '../../data-access/concept.store';
import { JustificationStore } from '../../data-access/justification.store';

@Component({
  selector: 'app-knowledge-concept-list-form',
  templateUrl: './knowledge-concept-list-form.component.html',
  styleUrls: ['./knowledge-concept-list-form.component.scss'],
})
export class KnowledgeConceptListFormComponent implements OnInit {
  @Input() concept!: Concept;

  conceptForm: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private snackbarService: SnackbarService,
    private conceptStore: ConceptStore,
    private answerStore: AnswerStore,
    private justificationStore: JustificationStore
  ) {}

  ngOnInit(): void {
    this.conceptForm = this.formBuilder.group({
      text: [this.concept.text, [Validators.required]],
    });
  }

  saveConcept(conceptID: number) {
    const conceptFormValue = this.conceptForm.value;
    this.conceptStore.saveConcept(conceptID, conceptFormValue).subscribe({
      next: () => this.snackbarService.openSnackBar('Concept updated.'),
      error: () => this.snackbarService.openSnackBar('Error updating concept.'),
    });
  }
}
