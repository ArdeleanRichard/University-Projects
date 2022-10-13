import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventChangeDialogComponent } from './event-change-dialog.component';

describe('EventChangeDialogComponent', () => {
  let component: EventChangeDialogComponent;
  let fixture: ComponentFixture<EventChangeDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventChangeDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventChangeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
