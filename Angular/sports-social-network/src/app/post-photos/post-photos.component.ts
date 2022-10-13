import { Component, OnInit } from '@angular/core';
import { UploadEvent, UploadFile, FileSystemFileEntry, FileSystemDirectoryEntry } from 'ngx-file-drop';
import { HttpClient } from '@angular/common/http';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

import { Event } from '../models/event';
import { RecentService } from '../services/recent.service';
import { UploadPhotosDialogComponent } from '../upload-photos-dialog/upload-photos-dialog.component';

@Component({
  selector: 'app-post-photos',
  templateUrl: './post-photos.component.html',
  styleUrls: ['./post-photos.component.scss']
})
export class PostPhotosComponent implements OnInit {

  public files: UploadFile[] = [];
  http: HttpClient;

  selectedEventName = '';
  selectedEvent: Event;
  yourEvents: Event[];

  uploadedPhotos = '';
  eventStatus = '';

  constructor(private recentService: RecentService, public uploadDialog: MatDialog) { }



  ngOnInit() {
    this.recentService.getRecentEvents().subscribe(recentEvents => {
      this.yourEvents = recentEvents;
    });
  }

  public dropped(event: UploadEvent) {
    this.files = event.files;
    for (const droppedFile of event.files) {
      if (droppedFile.fileEntry.isFile) {
        const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        fileEntry.file((file: File) => {
          console.log(droppedFile.relativePath, file);
          const formData = new FormData();
          formData.append('logo', file, droppedFile.relativePath);
        });
      } else {
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public fileOver(event) {
    console.log(event);
  }

  public fileLeave(event) {
    console.log(event);
  }

  public onPostPhotos() {
    if (this.selectedEvent) {
      this.eventStatus = '';
      if (this.files.length !== 0)  {
        this.openUploadDialog();
      } else {
        this.eventStatus = 'You have not uploaded any images yet, you can upload by dropping them above';
      }
    } else {
      this.eventStatus = 'You have not chosen an event from the list yet, you can choose one by clicking';
      this.selectedEvent = undefined;
    }

  }

  openUploadDialog(): void {
    this.uploadDialog.open(UploadPhotosDialogComponent);
  }

  selectEvent(event) {
    console.log(event.name);
    this.selectedEvent = event;
    this.selectedEventName = event.name;
  }

  removePhoto(file: UploadFile) {
    this.files = this.files.filter(f => {
      return f.relativePath !== file.relativePath;
    });

  }
}
