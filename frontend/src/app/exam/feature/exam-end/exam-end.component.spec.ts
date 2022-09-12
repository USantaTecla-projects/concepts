import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExamEndComponent } from './exam-end.component';

describe('ExamEndComponent', () => {
  let component: ExamEndComponent;
  let fixture: ComponentFixture<ExamEndComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExamEndComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExamEndComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
