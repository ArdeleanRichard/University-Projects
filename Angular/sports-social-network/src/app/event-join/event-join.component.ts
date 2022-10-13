import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { EventsService } from '../services/events.service';
import { Event } from '../models/event';
import { SPORTS, SKILL_LEVELS } from '../constants';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';
import { MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent } from '@angular/material';
import { COMMA, ENTER } from '@angular/cdk/keycodes';

@Component({
  selector: 'app-event-join',
  templateUrl: './event-join.component.html',
  styleUrls: ['./event-join.component.scss']
})
export class EventJoinComponent implements OnInit {

  SPORTS = SPORTS;
  SKILL_LEVELS = SKILL_LEVELS;

  events: Event[];
  originalEventsList: Event[];
  selectedEvent: Event;
  joinEventStatus = '';
  searchStatus = '';

  prefSport: string;
  prefSkill: string;
  prefPrice: number;
  prefPart: number;

  searched: string[] = [];

  SPORTS_LIST: string[] = [];
  FILTERED_SPORTS_LIST: Observable<string[]>;

  userPreferredSports: string[] = [];
  preferenceControl = new FormControl();
  @ViewChild('PreferenceInput') preferenceInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;
  separatorKeysCodes: number[] = [ENTER, COMMA];

  constructor(
    private router: Router,
    private eventsService: EventsService
  ) {
    this.FILTERED_SPORTS_LIST = this.preferenceControl.valueChanges.pipe(
      startWith(null),
      map((sport: string | null) => sport ? this._filter(sport) : this.SPORTS_LIST));
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.SPORTS_LIST.filter(sport => sport.toLowerCase().indexOf(filterValue) === 0);
  }

  removePreferredSport(sportToRemove: string): void {
    this.userPreferredSports = this.userPreferredSports
      .filter(sportPreference => sportPreference !== sportToRemove);
    this.SPORTS_LIST.push(sportToRemove);
    this.events = this.originalEventsList;
    this.searchStatus = '';
    if (this.userPreferredSports) {
      for (let i = 0; i < this.userPreferredSports.length; i++) {
        this.events = this.events.filter(ev => ev.name.toLowerCase().includes(this.userPreferredSports[i].toLowerCase()));
        if (this.events.length === 0) {
          this.searchStatus = 'No events with that name have been found.';
        }
      }
    }
    this.preferenceControl.setValue('');
  }

  addPreferredSport(event: MatChipInputEvent): void {
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = event.value;

      if ((value || '').trim()) {
        this.userPreferredSports.push(value.trim());
        this.SPORTS_LIST = this.SPORTS_LIST.filter(s => s !== value);
        this.preferenceControl.setValue('');

        this.events = this.events.filter(ev => ev.name.toLowerCase().includes(value.toLowerCase()));
      }

      if (this.events.length === 0) {
        this.searchStatus = 'No events with that name have been found.';
      }

      if (input) {
        input.value = '';
      }
    }
  }

  selectedPreferredSport(event: MatAutocompleteSelectedEvent): void {
    const sportToAdd = event.option.value;
    this.userPreferredSports.push(sportToAdd);
    this.preferenceInput.nativeElement.value = '';
    this.SPORTS_LIST = this.SPORTS_LIST.filter(s => s !== sportToAdd);
    this.preferenceControl.setValue('');

    this.events = this.events.filter(ev => ev.name.toLowerCase().includes(sportToAdd.toLowerCase()));

    this.selectedEvent = undefined;
  }

  ngOnInit() {
    this.eventsService.getAllEvents().subscribe(events => {
      this.originalEventsList = events;
      this.events = events;
    });

  }

  formatLabel(value: number | null) {
    if (!value) {
      return 0;
    }

    return value;
  }

  onJoinEvent() {
    if (this.selectedEvent) {
      this.eventsService.joinEvent(this.selectedEvent);
      this.events = this.events.filter(ev => ev !== this.selectedEvent);
      this.joinEventStatus = '';
    } else {
      this.joinEventStatus = 'You have not chosen an event by clicking from the list yet.';
      this.selectedEvent = undefined;
    }
  }

  selectEvent(event: Event): void {
    this.selectedEvent = event;
    this.joinEventStatus = '';
  }

  onEnter(event, search) {
    if (event.keyCode === 13) {
      this.searched.push(search.value);
      if (this.SPORTS.indexOf(search.value) > 0) {

      }
      search.value = '';
    }
  }

  deleteSearch(search) {
    for (let i = 0; i < this.searched.length; i++) {
      if (this.searched[i] === search) {
        this.searched.splice(i, 1);
        this.eventsService.getAllEvents().subscribe(events => {
          this.events = events as Event[];
        });
      }
    }
  }

  onApplyFilters() {
    this.events = this.originalEventsList;
    this.searchStatus = '';
    if (this.prefSport) {
      this.events = this.events.filter(ev => ev.category === this.prefSport);
    }
    if (this.prefSkill) {
      this.events = this.events.filter(ev => ev.skill === this.prefSkill);
    }
    if (this.prefPrice) {
      this.events = this.events.filter(ev => ev.price < this.prefPrice);
    }
    if (this.prefPart) {
      this.events = this.events.filter(ev => ev.participants < this.prefPart);
    }
    this.selectedEvent = undefined;
  }

  onResetFilters(){
    this.events=this.originalEventsList;
  }
}
