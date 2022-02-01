import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OverviewGoodsComponent} from './overview-goods.component';

describe('OverviewGoodsComponent', () => {
  let component: OverviewGoodsComponent;
  let fixture: ComponentFixture<OverviewGoodsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OverviewGoodsComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OverviewGoodsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
