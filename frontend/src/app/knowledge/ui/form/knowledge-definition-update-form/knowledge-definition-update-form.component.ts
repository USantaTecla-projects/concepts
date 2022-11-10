import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Definition } from 'src/app/shared/types/definition/definition.model';

@Component({
  selector: 'app-knowledge-definition-update-form',
  templateUrl: './knowledge-definition-update-form.component.html',
  styleUrls: ['./knowledge-definition-update-form.component.scss'],
})
export class KnowledgeDefinitionUpdateFormComponent implements OnInit {
  @Input() definition!: Definition;

  @Output() updateDefinition: EventEmitter<Definition> = new EventEmitter();

  definitionForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.definitionForm = this.formBuilder.group({
      text: [this.definition.text, [Validators.required]],
      correct: [this.definition.correct],
    });
  }

  onSubmit(): void {
    const definitionFormValue = this.definitionForm.value;
    if (definitionFormValue) {
      const updatedDefinition: Definition = { ...definitionFormValue };
      this.updateDefinition.emit(updatedDefinition);
    }
  }
}
