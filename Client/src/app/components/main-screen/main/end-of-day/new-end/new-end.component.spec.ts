import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewEndComponent} from './new-end.component';

describe('NewEndComponent', () => {
  let component: NewEndComponent;
  let fixture: ComponentFixture<NewEndComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewEndComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewEndComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
