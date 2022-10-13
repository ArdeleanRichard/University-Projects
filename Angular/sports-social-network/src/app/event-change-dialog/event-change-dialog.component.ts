import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-event-change-dialog',
  templateUrl: './event-change-dialog.component.html',
  styleUrls: ['./event-change-dialog.component.scss']
})
export class EventChangeDialogComponent implements OnInit {

  constructor(
    private dialogRef: MatDialogRef<EventChangeDialogComponent>
  ) { }

  ngOnInit() {
  }

  goBack() {
    this.dialogRef.close();
  }

}
