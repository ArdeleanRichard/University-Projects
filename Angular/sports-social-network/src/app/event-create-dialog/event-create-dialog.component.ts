import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-event-create-dialog',
  templateUrl: './event-create-dialog.component.html',
  styleUrls: ['./event-create-dialog.component.scss']
})
export class EventCreateDialogComponent implements OnInit {

  constructor(
    private dialogRef: MatDialogRef<EventCreateDialogComponent>
  ) { }

  ngOnInit() {
  }

  goBack() {
    this.dialogRef.close();
  }
}
