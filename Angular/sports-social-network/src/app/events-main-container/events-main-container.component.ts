import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

const EVENT_OPERATIONS = [
  { name: 'Join Event', url: '/events/join' },
  { name: 'My Own Events', url: '/events/user' },
  { name: 'Create Event', url: '/events/create' },
  { name: 'Send Invitations', url: '/events/invitations' },
  { name: 'Recent Events', url: '/events/recent' },
  { name: 'Reminders', url: '/events/reminders' },
  { name: 'Post Photos', url: '/events/photos' }
];

@Component({
  selector: 'app-events-main-container',
  templateUrl: './events-main-container.component.html',
  styleUrls: ['./events-main-container.component.scss']
})
export class EventsMainContainerComponent implements OnInit {

  EVENT_OPERATIONS = EVENT_OPERATIONS;

  EVENT_MENU_OPTIONS = EVENT_OPERATIONS.map(op => op.name);

  selectedMenuOption: string;

  constructor(private router: Router) {
    this.router.events
      .pipe(filter(ev => ev instanceof NavigationEnd))
      .subscribe((ev: NavigationEnd) => {
        if (ev.url.split('/').length >= 3) {
          const newMenuOperation = this.EVENT_OPERATIONS.find(menu => menu.url.indexOf(ev.url.split('/')[2]) > -1);
          if (newMenuOperation) {
            this.selectedMenuOption = newMenuOperation.name;
          }
        } else {
          this.selectedMenuOption = this.EVENT_MENU_OPTIONS[0];
        }
      });
  }

  ngOnInit() {
  }

  onSelectedMenuChange(selectedMenuOption: string) {
    this.selectedMenuOption = selectedMenuOption;
    this.router.navigate([EVENT_OPERATIONS.find(op => op.name === this.selectedMenuOption).url]);
  }
}
