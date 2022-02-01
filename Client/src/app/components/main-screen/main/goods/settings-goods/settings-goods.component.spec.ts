import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SettingsGoodsComponent} from './settings-goods.component';

describe('SettingsGoodsComponent', () => {
  let component: SettingsGoodsComponent;
  let fixture: ComponentFixture<SettingsGoodsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SettingsGoodsComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SettingsGoodsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
