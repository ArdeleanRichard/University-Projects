import { Component, OnInit } from '@angular/core';
import { EventsService } from '../services/events.service';
import { Event } from '../models/event';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from "@angular/material";
import {EventCreateDialogComponent} from "../event-create-dialog/event-create-dialog.component";
import {EventChangeDialogComponent} from "../event-change-dialog/event-change-dialog.component";

@Component({
  selector: 'app-event-user',
  templateUrl: './event-user.component.html',
  styleUrls: ['./event-user.component.scss']
})
export class EventUserComponent implements OnInit {
  eventChangeForm: FormGroup;

  events: Event[];
  selectedEvent: Event;
  selectedEventName: any;
  selectedEventSport: any;
  selectedEventSkill: any;
  selectedEventNrParticipants: any;
  selectedEventPrice: any;
  selectedEventDescription: any;

  constructor(private eventsService: EventsService,
              public matDialog: MatDialog) {
  }

  ngOnInit() {
    this.eventChangeForm = new FormGroup({
      'name': new FormControl('', [
        Validators.required,
        Validators.minLength(4)
      ]),
      'participants': new FormControl('', [
        Validators.required
      ]),
      'price': new FormControl('', [
        Validators.required
      ]),
      'description': new FormControl('')
    });

    this.eventsService.getUserEvents().subscribe(events => {
      this.events = events;
    });
  }

  get f() { return this.eventChangeForm.controls; }

  selectEvent(event) {
    this.selectedEvent = event;
    this.selectedEventName = event.name;
    this.selectedEventSport = event.category;
    this.selectedEventSkill = event.skill;
    this.selectedEventNrParticipants = event.participants;
    this.selectedEventPrice = event.price;
    this.selectedEventDescription = event.description;

    this.eventChangeForm.value.name = event.name;

  }

  onChangeEvent() {
    if (this.eventChangeForm.valid) {
      for (let i = 0; i < this.events.length; i++) {
        if (this.events[i].name === this.selectedEventName) {
          this.events[i].name = this.eventChangeForm.value.name;
          this.events[i].participants = this.eventChangeForm.value.participants;
          this.events[i].price = this.eventChangeForm.value.price;
          this.events[i].description = this.eventChangeForm.value.description;
        }
      }
      this.selectedEventName = this.eventChangeForm.value.name;
      this.selectedEventNrParticipants = this.eventChangeForm.value.participants;
      this.selectedEventPrice = this.eventChangeForm.value.price;
      this.selectedEventDescription = this.eventChangeForm.value.description;


      this.matDialog.open(EventChangeDialogComponent);
    } else {
      Object.keys(this.eventChangeForm.controls)
        .forEach(control => {
          this.eventChangeForm.controls[control].markAsTouched();
        });
    }
  }
}
