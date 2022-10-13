import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileMainContainerComponent } from './profile-main-container.component';

describe('ProfileMainContainerComponent', () => {
  let component: ProfileMainContainerComponent;
  let fixture: ComponentFixture<ProfileMainContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfileMainContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileMainContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
