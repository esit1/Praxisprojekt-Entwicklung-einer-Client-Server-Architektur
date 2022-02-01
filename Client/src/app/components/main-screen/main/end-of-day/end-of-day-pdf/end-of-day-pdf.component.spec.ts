import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EndOfDayPdfComponent } from './end-of-day-pdf.component';

describe('EndOfDayPdfComponent', () => {
  let component: EndOfDayPdfComponent;
  let fixture: ComponentFixture<EndOfDayPdfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EndOfDayPdfComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EndOfDayPdfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
