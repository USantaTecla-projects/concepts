import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Definition } from 'src/app/shared/types/definition/definition.model';

@Component({
  selector: 'app-knowledge-definition-create-form',
  templateUrl: './knowledge-definition-create-form.component.html',
  styleUrls: ['./knowledge-definition-create-form.component.scss'],
})
export class KnowledgeDefinitionCreateFormComponent implements OnInit {
  @Output() createDefinition: EventEmitter<Definition> = new EventEmitter();

  definitionForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.definitionForm = this.formBuilder.group({
      text: [null, [Validators.required]],
      correct: [false],
    });
  }

  onSubmit(): void {
    const definitionFormValue = this.definitionForm.value;
    if (definitionFormValue) {
      const newDefinition: Definition = { ...definitionFormValue };
      this.createDefinition.emit(newDefinition);
    }
  }

  onCancel(): void {
    this.createDefinition.emit();
  }
}
