import { Component, OnInit,Inject } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialogRef,MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-quizz-dialog',
  templateUrl: './quizz-dialog.component.html',
  styleUrls: ['./quizz-dialog.component.scss']
})
export class QuizzDialogComponent implements OnInit {

 
  correct : string;

  constructor(private router: Router,
    private dialogRef: MatDialogRef<QuizzDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data
     ) { }
   
  ngOnInit() {
    this.correct=this.data.correctAnswers;
  }
  goHome() {
    this.router.navigateByUrl('/');
    this.dialogRef.close();
  }
}
