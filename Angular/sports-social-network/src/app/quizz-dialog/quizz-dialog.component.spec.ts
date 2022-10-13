import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizzDialogComponent } from './quizz-dialog.component';

describe('QuizzDialogComponent', () => {
  let component: QuizzDialogComponent;
  let fixture: ComponentFixture<QuizzDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuizzDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizzDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
