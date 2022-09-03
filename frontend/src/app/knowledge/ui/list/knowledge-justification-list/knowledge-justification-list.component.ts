import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Justification, JustificationStore } from 'src/app/knowledge/data-access/justification.store';
import { State } from 'src/app/shared/utils/enums/state.enum';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { KnowledgeDialogJustificationCreateComponent } from '../../dialog/knowledge-dialog-justification-create/knowledge-dialog-justification-create.component';

@Component({
  selector: 'app-knowledge-justification-list',
  templateUrl: './knowledge-justification-list.component.html',
  styleUrls: ['./knowledge-justification-list.component.scss'],
})
export class KnowledgeJustificationListComponent implements OnInit {
  @Input() justifications: Justification[] | null = [];

  state: string = State.INIT;

  constructor(
    private justificationStore: JustificationStore,
    private snackbarService: SnackbarService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.justificationStore.state$.subscribe({
      next: value => (this.state = value),
      error: error => console.log(error),
    });
  }

  createJustification(justificationFormValue: any) {
    this.justificationStore.createJustification(justificationFormValue).subscribe({
      next: () => this.snackbarService.openSnackBar('Justification created.'),
      error: () => this.snackbarService.openSnackBar('Error creating justification.'),
    });
  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(KnowledgeDialogJustificationCreateComponent, {
      width: '30rem',
      data: {
        knowledgeItem: 'concept',
      },
    });

    dialogRef.afterClosed().subscribe(answerFormValue => {
      if (answerFormValue) this.createJustification(answerFormValue);
    });
  }
}
