import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JoinQuizzComponent } from './join-quizz.component';

describe('JoinQuizzComponent', () => {
  let component: JoinQuizzComponent;
  let fixture: ComponentFixture<JoinQuizzComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoinQuizzComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoinQuizzComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
