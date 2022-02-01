import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ChangeGoodsComponent} from './change-goods.component';

describe('ChangeGoodsComponent', () => {
  let component: ChangeGoodsComponent;
  let fixture: ComponentFixture<ChangeGoodsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChangeGoodsComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeGoodsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
