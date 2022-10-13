import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JoinConfirmationDialogComponent } from './join-confirmation-dialog.component';

describe('JoinConfirmationDialogComponent', () => {
  let component: JoinConfirmationDialogComponent;
  let fixture: ComponentFixture<JoinConfirmationDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoinConfirmationDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoinConfirmationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
