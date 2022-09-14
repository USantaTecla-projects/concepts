import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Concept } from 'src/app/knowledge/interfaces/concept.model';

@Component({
  selector: 'app-knowledge-concept-update-form',
  templateUrl: './knowledge-concept-update-form.component.html',
  styleUrls: ['./knowledge-concept-update-form.component.scss'],
})
export class KnowledgeConceptUpdateFormComponent implements OnInit {
  @Input() concept!: Concept;

  @Output() updateConcept: EventEmitter<Concept> = new EventEmitter();

  conceptForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.conceptForm = this.formBuilder.group({
      text: [this.concept.text, [Validators.required]],
    });
  }

  onSubmit(): void {
    const conceptFormValue = this.conceptForm.value;
    if (conceptFormValue) {
      const updatedConcept: Concept = { ...conceptFormValue };
      this.updateConcept.emit(updatedConcept);
    }
  }
}
