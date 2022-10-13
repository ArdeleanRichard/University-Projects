import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SendInvitationsComponent } from './send-invitations.component';

describe('SendInvitationsComponent', () => {
  let component: SendInvitationsComponent;
  let fixture: ComponentFixture<SendInvitationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SendInvitationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SendInvitationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
