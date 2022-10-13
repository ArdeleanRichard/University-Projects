import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

const PROFILE_OPERATIONS = [
  { name: 'Preferences', url: '/profile/preferences' },
  { name: 'Premium Points', url: '/profile/premium-points' },
  { name: 'Settings', url: '/profile/settings' },
  { name: 'Security', url: '/profile/security' },
  { name: 'About', url: '/profile/about' },
];

@Component({
  selector: 'app-profile-main-container',
  templateUrl: './profile-main-container.component.html',
  styleUrls: ['./profile-main-container.component.scss']
})
export class ProfileMainContainerComponent implements OnInit {

  PROFILE_OPERATIONS = PROFILE_OPERATIONS;

  PROFILE_MENU_OPTIONS = PROFILE_OPERATIONS.map(op => op.name);

  selectedMenuOption: string;

  constructor(
    private router: Router
  ) {
    this.router.events
      .pipe(filter(ev => ev instanceof NavigationEnd))
      .subscribe((ev: NavigationEnd) => {
        if (ev.url.split('/').length >= 3) {
          const newMenuOperation = this.PROFILE_OPERATIONS.find(menu => menu.url.indexOf(ev.url.split('/')[2]) > -1);
          if (newMenuOperation) {
            this.selectedMenuOption = newMenuOperation.name;
          }
        } else {
          this.selectedMenuOption = this.PROFILE_MENU_OPTIONS[0];
        }
      });
  }

  ngOnInit() {
  }

  onSelectedMenuChange(selectedMenuOption: string) {
    this.selectedMenuOption = selectedMenuOption;
    this.router.navigate([PROFILE_OPERATIONS.find(op => op.name === this.selectedMenuOption).url]);
  }
}
