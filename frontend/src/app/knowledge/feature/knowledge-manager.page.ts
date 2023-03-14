import { Component, OnDestroy, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';
import { Concept } from '../../shared/types/concept/concept.model';
import { Definition } from '../../shared/types/definition/definition.model';
import { Justification } from '../../shared/types/justification/justification.model';
import { ConceptStore } from '../data-access/concept.store';
import { DefinitionStore } from '../data-access/definition.store';
import { JustificationStore } from '../data-access/justification.store';
import { KnowledgeConceptListComponent } from '../ui/list/knowledge-concept-list/knowledge-concept-list.component';

@Component({
  selector: 'app-knowledge-manager',
  templateUrl: './knowledge-manager.page.html',
  styleUrls: ['./knowledge-manager.page.scss'],
})
export class KnowledgeManagerPage implements OnDestroy {
  @ViewChild('conceptList')
  private conceptList!: KnowledgeConceptListComponent;

  private subscriptionList: Subscription[] = [];

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

  ngOnDestroy(): void {
    this.conceptStore.resetConceptList();
    this.definitionStore.resetDefinitionList();
    this.justificationStore.resetJustificationList();

    this.subscriptionList.forEach(subscription => subscription.unsubscribe());
  }

  onConceptSelect(conceptID: number) {
    this.selectedConceptID = conceptID;
    this.resetDefinitionList = true;
    this.subscriptionList.push(
      this.definitionStore.getDefinitions(this.selectedConceptID).subscribe({
        next: () => (this.resetDefinitionList = false),
        error: (err: Error) => console.error(err.message),
      })
    );
    this.justificationStore.resetJustificationList();
  }

  onConceptCreate(concept: Concept) {
    if (concept.text) {
      this.subscriptionList.push(
        this.conceptStore.createConcept(concept).subscribe({
          next: () => this.snackbarService.openSnackBar('Concept created.'),
          error: (err: Error) => this.snackbarService.openSnackBar(err.message),
        })
      );
    }
  }

  onConceptUpdate(updatedConcept: Concept) {
    this.subscriptionList.push(
      this.conceptStore.updateConcept(this.selectedConceptID, updatedConcept).subscribe({
        next: () => this.snackbarService.openSnackBar('Concept updated.'),
        error: (err: Error) => this.snackbarService.openSnackBar(err.message),
      })
    );
  }

  onConceptDelete(conceptID: number) {
    this.subscriptionList.push(
      this.conceptStore.deleteConcept(conceptID).subscribe({
        next: () => {
          this.snackbarService.openSnackBar('Concept deleted.');
          this.definitionStore.resetDefinitionList();
          this.justificationStore.resetJustificationList();
          this.conceptList.updateNumberOfConcepts(-1);
        },
        error: () => this.snackbarService.openSnackBar('Error deleting concept.'),
      })
    );
  }

  onConceptPageGet(pageIndex: number) {
    this.subscriptionList.push(this.conceptStore.getConcepts(pageIndex).subscribe());
  }

  onDefinitionSelect(definitionID: number) {
    this.selectedDefinitionID = definitionID;
    this.resetJustificationList = true;
    this.subscriptionList.push(
      this.justificationStore.getJustifications(this.selectedConceptID, this.selectedDefinitionID).subscribe({
        next: () => (this.resetJustificationList = false),
        error: error => console.error(error),
      })
    );
  }

  onDefinitionCreate(definition: Definition) {
    if (definition.text) {
      this.subscriptionList.push(
        this.definitionStore.createDefinition(this.selectedConceptID, definition).subscribe({
          next: () => this.snackbarService.openSnackBar('Definition created.'),
          error: () => this.snackbarService.openSnackBar('Error creating definition.'),
        })
      );
    }
  }

  onDefinitionUpdate(updatedDefinition: Definition) {
    this.subscriptionList.push(
      this.definitionStore
        .updateDefinition(this.selectedConceptID, this.selectedDefinitionID, updatedDefinition)
        .subscribe({
          next: () => this.snackbarService.openSnackBar('Definition updated.'),
          error: () => this.snackbarService.openSnackBar('Error updating definition.'),
        })
    );
  }

  onDefinitionDelete(definitionID: number) {
    this.subscriptionList.push(
      this.definitionStore.deleteDefinition(this.selectedConceptID, definitionID).subscribe({
        next: () => {
          this.snackbarService.openSnackBar('Definition deleted.');
          this.justificationStore.resetJustificationList();
        },
        error: () => this.snackbarService.openSnackBar('Error deleting definition.'),
      })
    );
  }

  onJustificationSelect(justificationID: number) {
    this.selectedJustificationID = justificationID;
  }

  onJustificationCreate(justification: Justification) {
    if (justification.text) {
      this.subscriptionList.push(
        this.justificationStore
          .createJustification(this.selectedConceptID, this.selectedDefinitionID, justification)
          .subscribe({
            next: () => this.snackbarService.openSnackBar('Justification created.'),
            error: () => this.snackbarService.openSnackBar('Error creating justification.'),
          })
      );
    }
  }

  onJustificationUpdate(updatedJustification: Justification) {
    console.log(updatedJustification);
    console.table([this.selectedConceptID, this.selectedDefinitionID, this.selectedJustificationID]);
    this.subscriptionList.push(
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
        })
    );
  }

  onJustificationDelete(justificationID: number) {
    this.subscriptionList.push(
      this.justificationStore
        .deleteJustification(this.selectedConceptID, this.selectedDefinitionID, justificationID)
        .subscribe({
          next: () => this.snackbarService.openSnackBar('Justification deleted.'),
          error: () => this.snackbarService.openSnackBar('Error deleting justification.'),
        })
    );
  }
}
