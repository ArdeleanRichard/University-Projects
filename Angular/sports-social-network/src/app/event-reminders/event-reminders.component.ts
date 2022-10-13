import { Component, OnInit } from '@angular/core';

import { EventsService } from '../services/events.service';
import { RemindersService } from '../services/reminders.service';
import { Event } from '../models/event';
import { Reminder } from '../models/reminder';

import { REPETITION } from '../constants';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-event-reminders',
  templateUrl: './event-reminders.component.html',
  styleUrls: ['./event-reminders.component.scss']
})
export class EventRemindersComponent implements OnInit {

  reminderCreationForm: FormGroup;

  repetitions = REPETITION;
  yourOptions = [];

  yourReminders: Reminder[];
  events: Event[];
  selectedReminder: Reminder;

  remindDate: Date;
  remindTimeStr: string;
  reminderStatus = '';

  constructor(private eventsService: EventsService, private remindersService: RemindersService) {
  }

  ngOnInit() {
    this.reminderCreationForm = new FormGroup({
      'chosenEvent': new FormControl('', [
        Validators.required
      ]),
      'repetitions': new FormControl('', [
        Validators.required
      ]),
      'number': new FormControl('', [
        Validators.required
      ])
    });

    this.remindDate = new Date();
    this.remindDate.setHours(7, 0, 0);
    this.remindTimeStr = this.remindDate.toTimeString().slice(0, 5);

    this.eventsService.getUserEvents().subscribe(events => {
      this.yourOptions = events;
    });

    this.remindersService.getReminders().subscribe(reminders => {
      this.yourReminders = reminders;
    });
  }

  get f() { return this.reminderCreationForm.controls; }

  onAddReminder(remindTime) {
    if (this.reminderCreationForm.valid) {
      const newReminder: Reminder = new Reminder();
      const e = this.reminderCreationForm.value.chosenEvent;

      newReminder.name = e;
      newReminder.time = remindTime;
      if (this.reminderCreationForm.value.number > 24) {
        newReminder.count = 24;
      } else {
        newReminder.count = this.reminderCreationForm.value.number % 24;
      }
      newReminder.unit = this.reminderCreationForm.value.repetitions;

      this.remindersService.createReminder(newReminder);
      this.yourReminders.push(newReminder);

      this.yourOptions = this.yourOptions.filter(op => op.name !== e);
    } else {
      Object.keys(this.reminderCreationForm.controls)
        .forEach( control => {
          this.reminderCreationForm.controls[control].markAsTouched();
        });
    }
  }

  onRemoveReminder() {
    if (this.selectedReminder) {
      this.remindersService.removeReminder(this.selectedReminder);
      this.yourReminders = this.yourReminders.filter(rem => rem.name !== this.selectedReminder.name);

      this.reminderStatus = '';
      this.yourOptions.push(this.selectedReminder);
    } else {
      this.reminderStatus = 'You have not chosen a reminder from the list yet.';
      this.selectedReminder = undefined;
    }
  }

  decrementTimeInput(): void {
      this.remindDate.setHours(this.remindDate.getHours() - 1);
      this.remindTimeStr = this.remindDate.toTimeString().slice(0, 5);
  }

  incrementTimeInput(): void {
      this.remindDate.setHours(this.remindDate.getHours() + 1);
      this.remindTimeStr = this.remindDate.toTimeString().slice(0, 5);
  }


  selectReminder(reminder) {
    console.log(reminder);
    this.selectedReminder = reminder;
  }
}
