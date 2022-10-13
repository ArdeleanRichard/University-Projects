import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ScoredActivity } from '../models/scored-activity';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ScoredActiviesService {

  constructor(
    private httpClient: HttpClient
  ) { }

  public getUserScoredActivities(): Observable<ScoredActivity[]> {
    const fileUrl = '../../assets/demo-data/scored-activities.json';
    return this.httpClient.get<ScoredActivity[]>(fileUrl);
  }
}
