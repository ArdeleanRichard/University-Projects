import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PageUnderDevelopmentComponent } from './page-under-development.component';

describe('PageUnderDevelopmentComponent', () => {
  let component: PageUnderDevelopmentComponent;
  let fixture: ComponentFixture<PageUnderDevelopmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PageUnderDevelopmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PageUnderDevelopmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
