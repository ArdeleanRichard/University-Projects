import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-join-confirmation-dialog',
  templateUrl: './join-confirmation-dialog.component.html',
  styleUrls: ['./join-confirmation-dialog.component.scss']
})
export class JoinConfirmationDialogComponent implements OnInit {

  constructor(private router: Router,
    private dialogRef: MatDialogRef<JoinConfirmationDialogComponent>) { }

  ngOnInit() {
    
  }

  goHome(){
    this.dialogRef.close();
  }

}
