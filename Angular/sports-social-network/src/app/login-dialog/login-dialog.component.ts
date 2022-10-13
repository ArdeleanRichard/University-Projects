import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.scss']
})
export class LoginDialogComponent implements OnInit {

  username: string;
  password: string;

  loginFail: boolean;

  constructor(
    private dialogRef: MatDialogRef<LoginDialogComponent>,
    private authService: AuthService
  ) { }

  ngOnInit() { }

  close(): void {
    this.dialogRef.close();
  }

  authenticate(): void {
    if (this.authService.authenticate(this.username, this.password)) {
      this.close();
    } else {
      this.loginFail = true;
    }
  }
}
