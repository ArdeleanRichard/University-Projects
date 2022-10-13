import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventJoinComponent } from './event-join.component';

describe('EventJoinComponent', () => {
  let component: EventJoinComponent;
  let fixture: ComponentFixture<EventJoinComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventJoinComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventJoinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
