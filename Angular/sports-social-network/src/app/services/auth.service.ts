import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

import { User } from '../models/user';

const USERS: User[] = [
  { username: 'jdoe', password: 'pass', displayName: 'John Doe' }
];

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  userLoginStatus: Subject<boolean>;

  constructor() {
    this.userLoginStatus = new Subject<boolean>();
  }

  authenticate(username: string, password: string): boolean {
    const loggedInUser = USERS.find(user => user.username === username && user.password === password);
    if (loggedInUser) {
      this.userLoginStatus.next(true);
      window.sessionStorage.setItem('username', username);
      return true;
    } else {
      this.userLoginStatus.next(false);
      return false;
    }
  }

  logout(username: string): boolean {
    window.sessionStorage.removeItem('username');
    this.userLoginStatus.next(false);
    return true;
  }

  getLoginStatus(): Subject<boolean> {
    return this.userLoginStatus;
  }

  isUserLoggedIn(): boolean {
    return window.sessionStorage.getItem('username') != null;
  }

  getLoggedInUser(): User {
    return USERS.find(user => user.username === window.sessionStorage.getItem('username'));
  }
}
