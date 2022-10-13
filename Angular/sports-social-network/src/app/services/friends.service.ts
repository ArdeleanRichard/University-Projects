import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Friend } from "../models/friend"
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FriendsService {

  constructor(private httpClient: HttpClient) { }

  public getAllFriends(): Observable<Friend[]> {
    const fileUrl = '../../assets/demo-data/friends.json';
    return this.httpClient.get<Friend[]>(fileUrl);
  }
}
