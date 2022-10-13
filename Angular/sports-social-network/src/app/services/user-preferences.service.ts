import { Injectable } from '@angular/core';
import { UserPreferences } from '../models/user-preferences';

@Injectable({
  providedIn: 'root'
})
export class UserPreferencesService {

  private userPreferences: UserPreferences;

  constructor() {
    this.userPreferences = new UserPreferences();
    this.userPreferences.homePageSuggestions = true;
    this.userPreferences.individualEvents = false;
    this.userPreferences.teamEvents = true;
    this.userPreferences.maximumDistance = 25;
    this.userPreferences.timeIntervalStart = '17:00';
    this.userPreferences.timeIntervalEnd = '21:00';
    this.userPreferences.duringWeekdays = true;
    this.userPreferences.duringWeekends = false;
    this.userPreferences.exploreIndex = 2;
    this.userPreferences.favoriteActivities = ['BASKETBALL', 'FOOTBALL', 'TENNIS'];
  }

  public getUserPreferences(): UserPreferences {
    return this.userPreferences;
  }

  public setUserPreferences(userPreferences: UserPreferences): void {
    this.userPreferences = userPreferences;
  }
}
