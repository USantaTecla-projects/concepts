import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Concept } from 'src/app/knowledge/types/concept.model';

@Component({
  selector: 'app-knowledge-concept-create-form',
  templateUrl: './knowledge-concept-create-form.component.html',
  styleUrls: ['./knowledge-concept-create-form.component.scss'],
})
export class KnowledgeConceptCreateFormComponent implements OnInit {
  @Output() createConcept: EventEmitter<Concept> = new EventEmitter();

  conceptForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.conceptForm = this.formBuilder.group({
      text: [null, [Validators.required]],
    });
  }

  onSubmit(): void {
    const conceptFormValue = this.conceptForm.value;
    if (conceptFormValue) {
      const newConcept: Concept = { ...conceptFormValue };
      this.createConcept.emit(newConcept);
    }
  }

  onCancel(): void {
    this.createConcept.emit();
  }
}
