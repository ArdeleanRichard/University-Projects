import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Reminder } from '../models/reminder';

@Injectable({
  providedIn: 'root'
})
export class RemindersService {

  myReminders: Array<Reminder>;

  constructor(private httpClient: HttpClient) {
    this.myReminders = new Array<Reminder>();
  }

  public createReminder(r: Reminder): void {
    this.myReminders.push(r);
  }

  public removeReminder(r: Reminder): void {
    const index =  this.myReminders.indexOf(r);
    if (index > -1) {
      this.myReminders.splice(index);
    }
  }

  public getReminders(): Observable<Reminder[]> {
    const fileUrl = '../../assets/demo-data/reminders.json';
    return this.httpClient.get<Reminder[]>(fileUrl);
  }
}
