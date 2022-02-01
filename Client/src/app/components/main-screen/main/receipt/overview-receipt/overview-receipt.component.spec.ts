import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OverviewReceiptComponent} from './overview-receipt.component';

describe('OverviewReceiptComponent', () => {
  let component: OverviewReceiptComponent;
  let fixture: ComponentFixture<OverviewReceiptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OverviewReceiptComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OverviewReceiptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
