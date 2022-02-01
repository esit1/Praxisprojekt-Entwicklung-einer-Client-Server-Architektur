import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OverviewSelfComponent} from './overview-self.component';

describe('OverviewSelfComponent', () => {
  let component: OverviewSelfComponent;
  let fixture: ComponentFixture<OverviewSelfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OverviewSelfComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OverviewSelfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
