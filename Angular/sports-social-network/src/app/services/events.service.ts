import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Event } from '../models/event';

@Injectable({
  providedIn: 'root'
})
export class EventsService {

  newlyEnrolledEvents: Array<Event>;

  constructor(
    private httpClient: HttpClient
  ) {
    this.newlyEnrolledEvents = new Array<Event>();
  }

  public getAllEvents(): Observable<Event[]> {
    const fileUrl = '../../assets/demo-data/events.json';
    return this.httpClient.get<Event[]>(fileUrl);
  }

  public joinEvent(e: Event): void {
    this.newlyEnrolledEvents.push(e);
  }

  public getUserEvents(): Observable<Event[]> {
    const fileUrl = '../../assets/demo-data/userEvents.json';
    return this.httpClient.get<Event[]>(fileUrl)
      .pipe(
        map((events: Event[]) => { this.newlyEnrolledEvents.forEach(ev => events.push(ev)); return events; })
      );
  }
}
