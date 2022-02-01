import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ChangeSelfComponent} from './change-self.component';

describe('ChangeSelfComponent', () => {
  let component: ChangeSelfComponent;
  let fixture: ComponentFixture<ChangeSelfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChangeSelfComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeSelfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
