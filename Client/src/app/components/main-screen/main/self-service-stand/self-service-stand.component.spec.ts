import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SelfServiceStandComponent} from './self-service-stand.component';

describe('SelfServiceStandComponent', () => {
  let component: SelfServiceStandComponent;
  let fixture: ComponentFixture<SelfServiceStandComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SelfServiceStandComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelfServiceStandComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
