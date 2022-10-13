import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-upload-photos-dialog',
  templateUrl: './upload-photos-dialog.component.html',
  styleUrls: ['./upload-photos-dialog.component.scss']
})
export class UploadPhotosDialogComponent implements OnInit {

  constructor( private dialogRef: MatDialogRef<UploadPhotosDialogComponent>) { }

  ngOnInit() {
  }

  close(): void {
    this.dialogRef.close();
  }
}
