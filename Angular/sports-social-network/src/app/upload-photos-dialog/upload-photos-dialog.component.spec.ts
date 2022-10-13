import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadPhotosDialogComponent } from './upload-photos-dialog.component';

describe('UploadPhotosDialogComponent', () => {
  let component: UploadPhotosDialogComponent;
  let fixture: ComponentFixture<UploadPhotosDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadPhotosDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadPhotosDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
