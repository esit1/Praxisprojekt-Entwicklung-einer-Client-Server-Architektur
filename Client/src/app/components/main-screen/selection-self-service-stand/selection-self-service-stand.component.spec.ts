import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SelectionSelfServiceStandComponent} from './selection-self-service-stand.component';

describe('SelectionSelfServiceStandComponent', () => {
  let component: SelectionSelfServiceStandComponent;
  let fixture: ComponentFixture<SelectionSelfServiceStandComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SelectionSelfServiceStandComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectionSelfServiceStandComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
