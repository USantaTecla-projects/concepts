import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccordionListItemComponent } from './accordion-list-item.component';

describe('AccordionListItemComponent', () => {
  let component: AccordionListItemComponent;
  let fixture: ComponentFixture<AccordionListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccordionListItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccordionListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
