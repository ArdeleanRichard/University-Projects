import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventOperationsComponent } from './event-operations.component';

describe('EventOperationsComponent', () => {
  let component: EventOperationsComponent;
  let fixture: ComponentFixture<EventOperationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventOperationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventOperationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
