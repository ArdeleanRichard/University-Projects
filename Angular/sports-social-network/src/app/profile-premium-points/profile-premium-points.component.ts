import { Component, OnInit } from '@angular/core';

import { ScoredActiviesService } from '../services/scored-activies.service';
import { ScoredActivity } from '../models/scored-activity';
import { Achievement } from '../models/achievement';
import { AchievementsService } from '../services/achievements.service';

@Component({
  selector: 'app-profile-premium-points',
  templateUrl: './profile-premium-points.component.html',
  styleUrls: ['./profile-premium-points.component.scss']
})
export class ProfilePremiumPointsComponent implements OnInit {

  scoredActivities: ScoredActivity[];
  selectedScoredActivity: ScoredActivity;

  achievements: Achievement[];

  constructor(
    private scoredActivitiesService: ScoredActiviesService,
    private achievementsSservice: AchievementsService
  ) { }

  ngOnInit() {
    this.scoredActivitiesService.getUserScoredActivities()
      .subscribe((scoredActivities: ScoredActivity[]) => {
        this.scoredActivities = scoredActivities;
      });

    this.achievementsSservice.getUserAchievements()
      .subscribe((achievements: Achievement[]) => {
        this.achievements = achievements;
      });
  }

  selectScoredActivity(scoredActivity: ScoredActivity): void {
    this.selectedScoredActivity = scoredActivity;
  }
}
