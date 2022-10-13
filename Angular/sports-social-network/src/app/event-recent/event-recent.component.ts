import { Component, OnInit } from '@angular/core';
import {RecentService} from '../services/recent.service';
import {Event} from '../models/event';
import {Player} from '../models/player';
import {PlayerService} from '../services/player.service';

@Component({
  selector: 'app-event-recent',
  templateUrl: './event-recent.component.html',
  styleUrls: ['./event-recent.component.scss']
})
export class EventRecentComponent implements OnInit {

  events: Event[];
  selectedEvent: Event;
  players: Player[];
  selectedPlayer: Player;
  randomPlayers:  Array<Player>;
  numberPlayers: number;
  selectedPlayerRating: number;
  selectedPlayerReviews: number

  constructor(private recentService: RecentService,
              private playerService: PlayerService) {
    this.randomPlayers = new Array<Player>();
  }

  ngOnInit() {
    this.recentService.getRecentEvents().subscribe(events => {
      this.events = events;
    });
    var nums = [0, 1, 2, 3, 4, 5];
    this.randomPlayers = new Array<Player>();
    this.playerService.getPlayers().subscribe(players => {
      this.players = players;
    });
    this.numberPlayers = Math.floor((Math.random() * 4) + 2);

    if (this.players !== undefined) {
      for (let i = 0; i < this.numberPlayers; i++) {
        let j = Math.floor((Math.random() * nums.length));
        this.randomPlayers.push(this.players[nums[j]]);
        nums.splice(j, 1);
      }
    }
  }

  selectPlayer(player: Player): void {
    this.selectedPlayer = player;
    this.selectedPlayerRating = this.selectedPlayer.rating;
    this.selectedPlayerReviews = this.selectedPlayer.reviewNumber;
  }

  selectEvent(event: Event): void {
    this.selectedPlayer = null;
    var nums = [0, 1, 2, 3, 4, 5];
    this.randomPlayers = new Array<Player>();
    this.playerService.getPlayers().subscribe(players => {
      this.players = players;
    });
    this.numberPlayers = Math.floor((Math.random() * 4) + 2);

    if (this.players !== undefined) {
      for (let i = 0; i < this.numberPlayers; i++) {
        let j = Math.floor((Math.random() * nums.length));
        this.randomPlayers.push(this.players[nums[j]]);
        nums.splice(j, 1);
      }
    }

    this.selectedEvent = event;
  }

  onRating(rating: number) {
    this.selectedPlayerRating =
      (this.selectedPlayer.rating * this.selectedPlayer.reviewNumber + rating)
      / (this.selectedPlayer.reviewNumber + 1);
    this.selectedPlayerReviews = this.selectedPlayer.reviewNumber + 1;
  }
}
