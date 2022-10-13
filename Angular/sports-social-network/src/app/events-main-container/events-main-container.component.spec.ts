import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventsMainContainerComponent } from './events-main-container.component';

describe('EventsMainContainerComponent', () => {
  let component: EventsMainContainerComponent;
  let fixture: ComponentFixture<EventsMainContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventsMainContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventsMainContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
