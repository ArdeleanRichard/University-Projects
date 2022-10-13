import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Player} from '../models/player';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  constructor(private httpClient: HttpClient) { }

  public getPlayers(): Observable<Player[]> {
    const fileUrl = '../../assets/demo-data/players.json';
    return this.httpClient.get<Player[]>(fileUrl);
  }
}
