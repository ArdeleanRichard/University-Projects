import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { GroupsComponent } from './groups/groups.component';
import { GroupCreateComponent } from './group-create/group-create.component';
import { EventCreateComponent } from './event-create/event-create.component';
import { EventJoinComponent } from './event-join/event-join.component';
import { EventRemindersComponent } from './event-reminders/event-reminders.component';
import { ProfilePreferencesComponent } from './profile-preferences/profile-preferences.component';
import { JoinQuizzComponent } from './join-quizz/join-quizz.component';
import { EventUserComponent } from './event-user/event-user.component';
import { EventsMainContainerComponent } from './events-main-container/events-main-container.component';
import { PostPhotosComponent } from './post-photos/post-photos.component';
import { SendInvitationsComponent } from './send-invitations/send-invitations.component';
import { EventRecentComponent } from './event-recent/event-recent.component';
import { ProfileMainContainerComponent } from './profile-main-container/profile-main-container.component';
import { ProfilePremiumPointsComponent } from './profile-premium-points/profile-premium-points.component';
import { PageUnderDevelopmentComponent } from './page-under-development/page-under-development.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'events',
    component: EventsMainContainerComponent,
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'join'
      },
      {
        path: 'join',
        component: EventJoinComponent
      },
      {
        path: 'user',
        component: EventUserComponent
      },
      {
        path: 'create',
        component: EventCreateComponent
      },
      {
        path: 'recent',
        component: EventRecentComponent,
      },
      {
        path: 'reminders',
        component: EventRemindersComponent
      },
      {
        path: 'photos',
        component: PostPhotosComponent
      },
      {
        path: 'invitations',
        component: SendInvitationsComponent
      }
    ]
  },
  {
    path: 'groups',
    component: GroupsComponent
  },
  {
    path: 'group-create',
    component: GroupCreateComponent
  },
  {
    path: 'join-quizz',
    component: JoinQuizzComponent
  },
  {
    path: 'profile',
    component: ProfileMainContainerComponent,
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'preferences'
      },
      {
        path: 'preferences',
        component: ProfilePreferencesComponent
      },
      {
        path: 'premium-points',
        component: ProfilePremiumPointsComponent
      },
      {
        path: 'settings',
        component: PageUnderDevelopmentComponent
      },
      {
        path: 'security',
        component: PageUnderDevelopmentComponent
      },
      {
        path: 'about',
        component: PageUnderDevelopmentComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
