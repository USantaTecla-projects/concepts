import { Component, OnInit } from '@angular/core';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { Answer, AnswerStore } from '../data-access/answer.store';
import { Concept, ConceptStore } from '../data-access/concept.store';
import { Justification, JustificationStore } from '../data-access/justification.store';

@Component({
  selector: 'app-knowledge-manager',
  templateUrl: './knowledge-manager.page.html',
  styleUrls: ['./knowledge-manager.page.scss'],
})
export class KnowledgeManagerPage implements OnInit {
  private selectedConceptID!: number;

  private selectedAnswerID!: number;

  private selectedJustificationID!: number;

  resetAnswerList: boolean = false;

  resetJustificationList: boolean = false;

  constructor(
    public conceptStore: ConceptStore,
    public answerStore: AnswerStore,
    public justificationStore: JustificationStore,
    private snackbarService: SnackbarService
  ) {}

  ngOnInit(): void {}

  onConceptSelect(conceptID: number) {
    this.selectedConceptID = conceptID;
    this.resetAnswerList = true;
    this.answerStore.readAnswers(this.selectedConceptID).subscribe({
      next: () => (this.resetAnswerList = false),
      error: error => console.log(error),
    });
    this.justificationStore.resetJustificationList();
  }

  onConceptCreate(concept: Concept) {
    this.conceptStore.createConcept(concept).subscribe({
      next: () => this.snackbarService.openSnackBar('Concept created.'),
      error: () => this.snackbarService.openSnackBar('Error creating concept.'),
    });
  }

  onConceptUpdate(updatedConcept: Concept) {
    this.conceptStore.updateConcept(this.selectedConceptID, updatedConcept).subscribe({
      next: () => this.snackbarService.openSnackBar('Concept updated.'),
      error: () => this.snackbarService.openSnackBar('Error updating concept.'),
    });
  }

  onConceptDelete(conceptID: number) {
    this.conceptStore.deleteConcept(conceptID).subscribe({
      next: () => this.snackbarService.openSnackBar('Concept deleted.'),
      error: () => this.snackbarService.openSnackBar('Error deleting concept.'),
    });
  }

  onAnswerSelect(answerID: number) {
    this.selectedAnswerID = answerID;
    this.resetJustificationList = true;
    this.justificationStore.readJustifications(this.selectedConceptID, this.selectedAnswerID).subscribe({
      next: () => (this.resetJustificationList = false),
      error: error => console.log(error),
    });
  }

  onAnswerCreate(answer: Answer) {
    this.answerStore.createAnswer(this.selectedConceptID, answer).subscribe({
      next: () => this.snackbarService.openSnackBar('Answer created.'),
      error: () => this.snackbarService.openSnackBar('Error creating answer.'),
    });
  }

  onAnswerUpdate(updatedAnswer: Answer) {
    this.answerStore.updateAnswer(this.selectedConceptID, this.selectedAnswerID, updatedAnswer).subscribe({
      next: () => this.snackbarService.openSnackBar('Answer updated.'),
      error: () => this.snackbarService.openSnackBar('Error updating answer.'),
    });
  }

  onAnswerDelete(answerID: number) {
    this.answerStore.deleteAnswer(this.selectedConceptID, answerID).subscribe({
      next: () => this.snackbarService.openSnackBar('Concept deleted.'),
      error: () => this.snackbarService.openSnackBar('Error deleting concept.'),
    });
  }

  onJustificationSelect(justificationID: number) {
    this.selectedJustificationID = justificationID;
  }

  onJustificationCreate(justification: Justification) {
    this.justificationStore
      .createJustification(this.selectedConceptID, this.selectedAnswerID, justification)
      .subscribe({
        next: () => this.snackbarService.openSnackBar('Justification created.'),
        error: () => this.snackbarService.openSnackBar('Error creating justification.'),
      });
  }

  onJustificationUpdate(updatedJustification: Justification) {
    this.justificationStore
      .updateJustification(
        this.selectedConceptID,
        this.selectedAnswerID,
        this.selectedJustificationID,
        updatedJustification
      )
      .subscribe({
        next: () => this.snackbarService.openSnackBar('Justification updated.'),
        error: () => this.snackbarService.openSnackBar('Error updating justification.'),
      });
  }

  onJustificationDelete(justificationID: number) {
    this.justificationStore
      .deleteJustification(this.selectedConceptID, this.selectedAnswerID, justificationID)
      .subscribe({
        next: () => this.snackbarService.openSnackBar('Justification deleted.'),
        error: () => this.snackbarService.openSnackBar('Error deleting justification.'),
      });
  }
}
