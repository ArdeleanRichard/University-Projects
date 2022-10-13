import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Event } from '../models/event';

@Injectable({
  providedIn: 'root'
})
export class RecentService {

  myRecentEvents: Array<Event>;

  constructor(private httpClient: HttpClient) {
    this.myRecentEvents = new Array<Event>();
  }

  public getRecentEvents(): Observable<Event[]> {
    const fileUrl = '../../assets/demo-data/recentEvents.json';
    return this.httpClient.get<Event[]>(fileUrl);
  }
}
