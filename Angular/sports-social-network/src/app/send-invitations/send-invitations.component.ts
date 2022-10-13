import { Component, OnInit } from '@angular/core';
import { Friend } from '../models/friend';
import { Event } from '../models/event';
import { Observable } from 'rxjs';
import { FriendsService } from '../services/friends.service';
import { EventsService } from '../services/events.service';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-send-invitations',
  templateUrl: './send-invitations.component.html',
  styleUrls: ['./send-invitations.component.scss']
})
export class SendInvitationsComponent implements OnInit {

  friends$: Observable<Friend[]>;
  events$: Observable<Event[]>;
  eventSelected: boolean;
  selectedEvent: Event;
  sel: Array<boolean>;
  invited: Array<boolean>;
  eventName: String;
  constructor(private friendsService: FriendsService,
    private eventsService: EventsService) { }

  ngOnInit() {
    this.friends$ = this.friendsService.getAllFriends()
      .pipe(tap(friends => { this.sel = new Array<boolean>(); friends.forEach(f => this.sel.push(false)); }))
      .pipe(tap(friends => { this.invited = new Array<boolean>(); friends.forEach(f => this.invited.push(false)); }));
    this.events$ = this.eventsService.getUserEvents();
    this.eventSelected = false;
    this.eventName="";
  }

  selectEvent(event: Event) {
    this.selectedEvent = event;
    this.eventName=event.name;
  }

  sendInvite(event: Event) {
      for(var i=0;i<this.invited.length;i++){
        this.invited[i]=this.sel[i];
      }
  }

}
