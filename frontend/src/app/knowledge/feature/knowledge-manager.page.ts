import { Component } from '@angular/core';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';
import { ConceptStore } from '../data-access/concept.store';
import { DefinitionStore } from '../data-access/definition.store';
import { JustificationStore } from '../data-access/justification.store';
import { Concept } from '../types/concept.model';
import { Definition } from '../types/definition.model';
import { Justification } from '../types/justification.model';

@Component({
  selector: 'app-knowledge-manager',
  templateUrl: './knowledge-manager.page.html',
  styleUrls: ['./knowledge-manager.page.scss'],
})
export class KnowledgeManagerPage {
  private selectedConceptID!: number;

  private selectedDefinitionID!: number;

  private selectedJustificationID!: number;

  resetDefinitionList = false;

  resetJustificationList = false;

  constructor(
    public conceptStore: ConceptStore,
    public definitionStore: DefinitionStore,
    public justificationStore: JustificationStore,
    private snackbarService: SnackbarService
  ) {}

  onConceptSelect(conceptID: number) {
    this.selectedConceptID = conceptID;
    this.resetDefinitionList = true;
    this.definitionStore.getDefinitions(this.selectedConceptID).subscribe({
      next: () => (this.resetDefinitionList = false),
      error: (err: Error) => console.log(err.message),
    });
    this.justificationStore.resetJustificationList();
  }

  onConceptCreate(concept: Concept) {
    this.conceptStore.createConcept(concept).subscribe({
      next: () => this.snackbarService.openSnackBar('Concept created.'),
      error: (err: Error) => this.snackbarService.openSnackBar(err.message),
    });
  }

  onConceptUpdate(updatedConcept: Concept) {
    this.conceptStore.updateConcept(this.selectedConceptID, updatedConcept).subscribe({
      next: () => this.snackbarService.openSnackBar('Concept updated.'),
      error: (err: Error) => this.snackbarService.openSnackBar(err.message),
    });
  }

  onConceptDelete(conceptID: number) {
    this.conceptStore.deleteConcept(conceptID).subscribe({
      next: () => {
        this.snackbarService.openSnackBar('Concept deleted.');
        this.definitionStore.resetDefinitionList();
        this.justificationStore.resetJustificationList();
      },
      error: () => this.snackbarService.openSnackBar('Error deleting concept.'),
    });
  }

  onConceptPageGet(pageIndex: number) {
    this.conceptStore.getConcepts(pageIndex).subscribe();
  }

  onDefinitionSelect(definitionID: number) {
    this.selectedDefinitionID = definitionID;
    this.resetJustificationList = true;
    this.justificationStore.getJustifications(this.selectedConceptID, this.selectedDefinitionID).subscribe({
      next: () => (this.resetJustificationList = false),
      error: error => console.log(error),
    });
  }

  onDefinitionCreate(definition: Definition) {
    this.definitionStore.createDefinition(this.selectedConceptID, definition).subscribe({
      next: () => this.snackbarService.openSnackBar('Definition created.'),
      error: () => this.snackbarService.openSnackBar('Error creating definition.'),
    });
  }

  onDefinitionUpdate(updatedDefinition: Definition) {
    this.definitionStore
      .updateDefinition(this.selectedConceptID, this.selectedDefinitionID, updatedDefinition)
      .subscribe({
        next: () => this.snackbarService.openSnackBar('Definition updated.'),
        error: () => this.snackbarService.openSnackBar('Error updating definition.'),
      });
  }

  onDefinitionDelete(definitionID: number) {
    this.definitionStore.deleteDefinition(this.selectedConceptID, definitionID).subscribe({
      next: () => {
        this.snackbarService.openSnackBar('Definition deleted.');
        this.justificationStore.resetJustificationList();
      },
      error: () => this.snackbarService.openSnackBar('Error deleting definition.'),
    });
  }

  onJustificationSelect(justificationID: number) {
    this.selectedJustificationID = justificationID;
  }

  onJustificationCreate(justification: Justification) {
    this.justificationStore
      .createJustification(this.selectedConceptID, this.selectedDefinitionID, justification)
      .subscribe({
        next: () => this.snackbarService.openSnackBar('Justification created.'),
        error: () => this.snackbarService.openSnackBar('Error creating justification.'),
      });
  }

  onJustificationUpdate(updatedJustification: Justification) {
    this.justificationStore
      .updateJustification(
        this.selectedConceptID,
        this.selectedDefinitionID,
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
      .deleteJustification(this.selectedConceptID, this.selectedDefinitionID, justificationID)
      .subscribe({
        next: () => this.snackbarService.openSnackBar('Justification deleted.'),
        error: () => this.snackbarService.openSnackBar('Error deleting justification.'),
      });
  }
}
