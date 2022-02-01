import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OverviewEndComponent} from './overview-end.component';

describe('OverviewEndComponent', () => {
  let component: OverviewEndComponent;
  let fixture: ComponentFixture<OverviewEndComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OverviewEndComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OverviewEndComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
