import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';

import { Discussion, Message } from '../models/message';

@Injectable({
  providedIn: 'root'
})
export class MessagesService {

  private userDiscussions: Discussion[];

  constructor(
    private httpClient: HttpClient
  ) { }

  public getUserDiscussions(): Observable<Discussion[]> {
    const fileUrl = '../../assets/demo-data/messages.json';
    return this.httpClient.get<Discussion[]>(fileUrl)
      .pipe(tap((discussions: Discussion[]) => { this.userDiscussions = discussions; }));
  }

  public getUserLastActiveDiscussion(): Observable<Discussion> {
    const fileUrl = '../../assets/demo-data/messages.json';
    return this.httpClient.get<Discussion[]>(fileUrl)
      .pipe(
        tap((discussions: Discussion[]) => { this.userDiscussions = discussions; }),
        map((discussions: Discussion[]) => discussions[0])
      );
  }

  public sendMessage(messageToSend: Message): Observable<Discussion> {
    this.userDiscussions[0].messages.push(messageToSend);
    return of(this.userDiscussions[0]);
  }
}
