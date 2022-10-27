import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { JustificationStore } from 'src/app/knowledge/data-access/justification.store';
import { Justification } from 'src/app/knowledge/types/justification.model';
import { State } from 'src/app/shared/interfaces/enums/state.enum';
import { KnowledgeDialogDeleteComponent } from '../../dialog/knowledge-dialog-delete/knowledge-dialog-delete.component';
import { KnowledgeDialogJustificationCreateComponent } from '../../dialog/knowledge-dialog-justification-create/knowledge-dialog-justification-create.component';

@Component({
  selector: 'app-knowledge-justification-list',
  templateUrl: './knowledge-justification-list.component.html',
  styleUrls: ['./knowledge-justification-list.component.scss'],
})
export class KnowledgeJustificationListComponent implements OnInit, OnChanges {
  @Input() justificationsList!: Justification[] | null;

  @Input() resetList = false;

  @Output() selectJustification: EventEmitter<number> = new EventEmitter();

  @Output() createJustification: EventEmitter<Justification> = new EventEmitter();

  @Output() updateJustification: EventEmitter<Justification> = new EventEmitter();

  @Output() deleteJustification: EventEmitter<number> = new EventEmitter();

  state: string = State.INIT;

  selectedJustificationID!: number;

  constructor(private justificationStore: JustificationStore, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.justificationStore.state$.subscribe({
      next: value => (this.state = value),
      error: error => console.log(error),
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    const resetListChange = changes['resetList'];
    if (resetListChange) {
      this.selectedJustificationID = 0;
    }
  }

  onJustificationSelect(justificationID: number): void {
    this.selectedJustificationID = justificationID;
    this.selectJustification.emit(this.selectedJustificationID);
  }

  onJustificationCreate(): void {
    const dialogRef = this.dialog.open(KnowledgeDialogJustificationCreateComponent, {
      width: '30rem',
      data: {
        knowledgeItem: 'justification',
      },
    });

    dialogRef.afterClosed().subscribe(justificationFormValue => {
      if (justificationFormValue) {
        const newJustification: Justification = { ...justificationFormValue };
        this.createJustification.emit(newJustification);
      }
    });
  }

  onJustificationUpdate(updatedJustification: Justification) {
    console.log(updatedJustification);
    this.updateJustification.emit(updatedJustification);
  }

  onJustificationDelete(): void {
    const dialogRef = this.dialog.open(KnowledgeDialogDeleteComponent, {
      width: '20rem',
      data: {
        knowledgeItem: 'justification',
      },
    });

    dialogRef.afterClosed().subscribe(deleteJustification => {
      if (deleteJustification) this.deleteJustification.emit(this.selectedJustificationID);
    });
  }
}
