import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FileDropModule } from 'ngx-file-drop';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from './app-routing.module';
import { HeaderComponent } from './header/header.component';

import { AgmCoreModule } from '@agm/core';

import {
  MatButtonModule,
  MatMenuModule,
  MatToolbarModule,
  MatIconModule,
  MatCardModule,
  MatSliderModule,
  MatInputModule,
  MatCheckboxModule,
  MatChipsModule,
  MatAutocompleteModule,
  MatSelectModule,
  MatFormFieldModule,
  MatDividerModule,
  MatRadioModule,
  MatDialogModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatExpansionModule,
  MatExpansionPanel,
  MatListModule
} from '@angular/material';

import { GroupsComponent } from './groups/groups.component';
import { GroupCreateComponent } from './group-create/group-create.component';
import { EventCreateComponent } from './event-create/event-create.component';
import { EventOperationsComponent } from './event-operations/event-operations.component';
import { EventJoinComponent } from './event-join/event-join.component';
import { EventRemindersComponent } from './event-reminders/event-reminders.component';
import { ProfilePreferencesComponent } from './profile-preferences/profile-preferences.component';
import { SideMenuComponent } from './side-menu/side-menu.component';
import { EventUserComponent } from './event-user/event-user.component';
import { JoinQuizzComponent } from './join-quizz/join-quizz.component';
import { QuizzDialogComponent } from './quizz-dialog/quizz-dialog.component';
import { EventsMainContainerComponent } from './events-main-container/events-main-container.component';
import { LoginDialogComponent } from './login-dialog/login-dialog.component';
import { PostPhotosComponent } from './post-photos/post-photos.component';
import { SendInvitationsComponent } from './send-invitations/send-invitations.component';
import { CreateGroupDialogComponent } from './create-group-dialog/create-group-dialog.component';
import { JoinConfirmationDialogComponent } from './join-confirmation-dialog/join-confirmation-dialog.component';
import { EventRecentComponent } from './event-recent/event-recent.component';
import { ProfileMainContainerComponent } from './profile-main-container/profile-main-container.component';
import { ProfilePremiumPointsComponent } from './profile-premium-points/profile-premium-points.component';
import { PageUnderDevelopmentComponent } from './page-under-development/page-under-development.component';
import { UploadPhotosDialogComponent } from './upload-photos-dialog/upload-photos-dialog.component';
import { EventCreateDialogComponent } from './event-create-dialog/event-create-dialog.component';
import { EventChangeDialogComponent } from './event-change-dialog/event-change-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    LoginDialogComponent,
    GroupsComponent,
    GroupCreateComponent,
    EventCreateComponent,
    EventOperationsComponent,
    EventJoinComponent,
    EventRemindersComponent,
    ProfilePreferencesComponent,
    SideMenuComponent,
    EventUserComponent,
    JoinQuizzComponent,
    QuizzDialogComponent,
    EventsMainContainerComponent,
    PostPhotosComponent,
    SendInvitationsComponent,
    CreateGroupDialogComponent,
    JoinConfirmationDialogComponent,
    EventRecentComponent,
    ProfileMainContainerComponent,
    ProfilePremiumPointsComponent,
    PageUnderDevelopmentComponent,
    UploadPhotosDialogComponent,
    EventCreateDialogComponent,
    EventChangeDialogComponent
  ],
  entryComponents: [
    LoginDialogComponent,
    QuizzDialogComponent,
    CreateGroupDialogComponent,
    JoinConfirmationDialogComponent,
    UploadPhotosDialogComponent,
    EventCreateDialogComponent,
    EventChangeDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    FileDropModule,

    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatSliderModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    MatChipsModule,
    MatAutocompleteModule,
    MatSelectModule,
    MatFormFieldModule,
    MatDividerModule,
    MatRadioModule,
    MatDialogModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatExpansionModule,
    MatListModule,

    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyCa6KJ4YcdSG-nwfffwqhCHcoA40g1aQzA'
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
