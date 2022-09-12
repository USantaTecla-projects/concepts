import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExamInCourseComponent } from './exam-in-course.component';

describe('ExamInCourseComponent', () => {
  let component: ExamInCourseComponent;
  let fixture: ComponentFixture<ExamInCourseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExamInCourseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExamInCourseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
