import { Component, OnInit } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { filter } from 'rxjs/operators';

import { AuthService } from '../services/auth.service';
import { MatDialog } from '@angular/material';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
import { User } from '../models/user';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  USER_MENUS = [
    { name: 'HOME', url: '/' },
    { name: 'EVENTS', url: '/events' },
    { name: 'GROUPS', url: '/groups' },
    { name: 'PROFILE', url: '/profile' }
  ];

  selectedMenu: string;

  isUserLoggedIn: boolean;
  loggedInUser: User;

  constructor(
    private router: Router,
    private authService: AuthService,
    public loginDialog: MatDialog
  ) {
    this.router.events
      .pipe(filter(ev => ev instanceof NavigationStart))
      .subscribe((ev: NavigationStart) => {
        if (this.USER_MENUS.find(menu => menu.url.indexOf(ev.url.split('/')[1]) > -1)) {
          this.selectedMenu = this.USER_MENUS.find(menu => menu.url.indexOf(ev.url.split('/')[1]) > -1).name;
        }
      });
  }

  ngOnInit() {
    this.authService.getLoginStatus()
      .subscribe((loginStatus: boolean) => {
        this.isUserLoggedIn = loginStatus;
        if (this.isUserLoggedIn) {
          this.loggedInUser = this.authService.getLoggedInUser();
        }
      });
    this.isUserLoggedIn = this.authService.isUserLoggedIn();
    if (this.isUserLoggedIn) {
      this.loggedInUser = this.authService.getLoggedInUser();
    }
  }

  selectMenu(selectedMenu: string): void {
    this.selectedMenu = selectedMenu;
    this.router.navigate([this.USER_MENUS.find(menu => menu.name === selectedMenu).url]);
  }

  navigateToProfile(): void {
    this.router.navigate(['profile/preferences']);
  }

  openLoginDialog(): void {
    this.loginDialog.open(LoginDialogComponent);
  }

  logout() {
    this.authService.logout(this.loggedInUser.username);
  }
}
