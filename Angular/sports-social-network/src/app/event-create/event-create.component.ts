import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatCalendar } from '@angular/material';
import { MatDialog } from '@angular/material';

import { EventsService } from '../services/events.service';
import { SPORTS, SKILL_LEVELS, TYPE } from '../constants';
import { EventCreateDialogComponent } from '../event-create-dialog/event-create-dialog.component';

@Component({
  selector: 'app-event-create',
  templateUrl: './event-create.component.html',
  styleUrls: ['./event-create.component.scss']
})
export class EventCreateComponent implements OnInit {

  SPORTS = SPORTS;
  SKILL_LEVELS = SKILL_LEVELS;
  TYPE = TYPE;

  eventCreationForm: FormGroup;

  selectedDate: Date;
  @ViewChild(MatCalendar) _datePicker: MatCalendar<Date>;

  latitude: 46.7712;
  longitude: 23.6236;

  constructor(private router: Router,
    private eventsService: EventsService,
    public matDialog: MatDialog
  ) { }

  ngOnInit() {
    this.eventCreationForm = new FormGroup({
      'name': new FormControl('', [
        Validators.required,
        Validators.minLength(4)
      ]),
      'category': new FormControl('', [
        Validators.required
      ]),
      'skill': new FormControl('', [
        Validators.required
      ]),
      'participants': new FormControl('', [
        Validators.required
      ]),
      'type': new FormControl('', [
        Validators.required
      ]),
      'price': new FormControl('', [
        Validators.required
      ]),
      'description': new FormControl(''),
      'time': new FormControl('')
    });

    this._datePicker.selectedChange.subscribe((date: Date) => {
      this.eventCreationForm.controls['time'].setValue(date.toDateString());
      this.selectedDate = date;
    });
  }

  get f() { return this.eventCreationForm.controls; }

  onCreate() {
    if (this.eventCreationForm.valid) {
      const eventToAdd = this.eventCreationForm.value;
      this.eventsService.joinEvent(eventToAdd);
      this.matDialog.open(EventCreateDialogComponent);
      this.router.navigate(['/events/user']);
    } else {
      Object.keys(this.eventCreationForm.controls)
        .forEach(control => {
          this.eventCreationForm.controls[control].markAsTouched();
        });
    }
  }

  onCancel() {
    this.eventCreationForm.reset();
    this.selectedDate = null;
  }
}
