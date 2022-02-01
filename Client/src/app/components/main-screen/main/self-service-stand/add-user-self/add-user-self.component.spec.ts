import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddUserSelfComponent} from './add-user-self.component';

describe('AddUserSelfComponent', () => {
  let component: AddUserSelfComponent;
  let fixture: ComponentFixture<AddUserSelfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddUserSelfComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserSelfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
