import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventRemindersComponent } from './event-reminders.component';

describe('EventRemindersComponent', () => {
  let component: EventRemindersComponent;
  let fixture: ComponentFixture<EventRemindersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventRemindersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventRemindersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
