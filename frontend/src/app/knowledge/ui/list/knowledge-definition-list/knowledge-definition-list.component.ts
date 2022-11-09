import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Definition } from 'src/app/knowledge/types/definition.model';
import { State } from 'src/app/shared/interfaces/enums/state.enum';
import { DefinitionStore } from '../../../data-access/definition.store';
import { KnowledgeDialogDefinitionCreateComponent } from '../../dialog/knowledge-dialog-definition-create/knowledge-dialog-definition-create.component';
import { KnowledgeDialogDeleteComponent } from '../../dialog/knowledge-dialog-delete/knowledge-dialog-delete.component';

@Component({
  selector: 'app-knowledge-definition-list',
  templateUrl: './knowledge-definition-list.component.html',
  styleUrls: ['./knowledge-definition-list.component.scss'],
})
export class KnowledgeDefinitionListComponent implements OnInit, OnChanges {
  @Input() definitionsList!: Definition[] | null;

  @Input() state: string | null = State.INIT;

  @Input() resetList = false;

  @Output() selectDefinition: EventEmitter<number> = new EventEmitter();

  @Output() createDefinition: EventEmitter<Definition> = new EventEmitter();

  @Output() updateDefinition: EventEmitter<Definition> = new EventEmitter();

  @Output() deleteDefinition: EventEmitter<number> = new EventEmitter();

  selectedDefinitionID!: number;

  constructor(private definitionStore: DefinitionStore, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.definitionStore.state$.subscribe({
      next: value => (this.state = value),
      error: error => console.error(error),
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    const resetListChange = changes['resetList'];
    if (resetListChange) {
      this.selectedDefinitionID = 0;
    }
  }

  onDefinitionSelect(definitionID: number): void {
    this.selectedDefinitionID = definitionID;
    this.selectDefinition.emit(this.selectedDefinitionID);
  }

  onDefinitionCreate(): void {
    const dialogRef = this.dialog.open(KnowledgeDialogDefinitionCreateComponent, {
      width: '30rem',
      data: {
        knowledgeItem: 'definition',
      },
    });

    dialogRef.afterClosed().subscribe(definitionFormValue => {
      if (definitionFormValue) {
        const newDefinition: Definition = { ...definitionFormValue };
        this.createDefinition.emit(newDefinition);
      }
    });
  }

  onDefinitionUpdate(updatedDefinition: Definition) {
    this.updateDefinition.emit(updatedDefinition);
  }

  onDefinitionDelete(): void {
    const dialogRef = this.dialog.open(KnowledgeDialogDeleteComponent, {
      width: '20rem',
      data: {
        knowledgeItem: 'definition',
      },
    });

    dialogRef.afterClosed().subscribe(deleteDefinition => {
      if (deleteDefinition) {
        this.deleteDefinition.emit(this.selectedDefinitionID);
        this.selectedDefinitionID = 0;
      }
    });
  }
}
