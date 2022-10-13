import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePremiumPointsComponent } from './profile-premium-points.component';

describe('ProfilePremiumPointsComponent', () => {
  let component: ProfilePremiumPointsComponent;
  let fixture: ComponentFixture<ProfilePremiumPointsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfilePremiumPointsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfilePremiumPointsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
