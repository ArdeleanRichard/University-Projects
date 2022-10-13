import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Achievement } from '../models/achievement';

@Injectable({
  providedIn: 'root'
})
export class AchievementsService {
  constructor(
    private httpClient: HttpClient
  ) { }

  public getUserAchievements(): Observable<Achievement[]> {
    const fileUrl = '../../assets/demo-data/achievements.json';
    return this.httpClient.get<Achievement[]>(fileUrl);
  }
}
