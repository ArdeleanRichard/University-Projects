import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { GroupsService } from '../services/groups.service';
import { Group } from '../models/group';
import { tap } from 'rxjs/operators';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { CreateGroupDialogComponent } from '../create-group-dialog/create-group-dialog.component';
import { JoinConfirmationDialogComponent } from '../join-confirmation-dialog/join-confirmation-dialog.component';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements OnInit {

  recommended$: Observable<Group[]>;
  hiddenRecomended: Array<boolean>;

  yourGroups$: Observable<Group[]>;
  hiddenYour: Array<boolean>;

  sentGroup: Group;
  

  constructor(
    private router: Router,
    private dialog: MatDialog,
    private dialog2: MatDialog,
    private groupsService: GroupsService
  ) {
  }

  ngOnInit() {
    this.recommended$ = this.groupsService.getRecommendedGroups()
      .pipe(tap((recommendedGroups: Group[]) => {
        this.hiddenRecomended = new Array<boolean>();
        recommendedGroups.forEach(_ => this.hiddenRecomended.push(false));
      }));

    this.yourGroups$ = this.groupsService.getYourGroups()
      .pipe(tap((yourGroups: Group[]) => {
        this.hiddenYour = new Array<boolean>();
        yourGroups.forEach(_ => this.hiddenYour.push(false));
      }));
  }

  onJoinGroup(groupName): void {

  }

  onCreateGroup(): void {
    this.router.navigateByUrl('/group-create');
  }

  displayRecommendedGroupDetails(i: number): void {
    this.hiddenRecomended[i] = !this.hiddenRecomended[i];
  }

  displayYourGroupDetails(i: number): void {
    this.hiddenYour[i] = !this.hiddenYour[i];
  }

  viewPosts(){
    this.router.navigateByUrl('/group-create');
  }

  openCreateDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { sentGroup: this.sentGroup };
    const dialogRef = this.dialog.open(CreateGroupDialogComponent, dialogConfig);
    dialogRef.afterClosed()
      .subscribe(response => {
        if (response) {
          this.sentGroup = response['createdGroup'];
          this.groupsService.createGroup(this.sentGroup);
          this.yourGroups$ = this.groupsService.getYourGroups();
        }
      });
  }

  sendRequest() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.panelClass = 'join-confirmation-dialog';
    const dialogRef = this.dialog2.open(JoinConfirmationDialogComponent, dialogConfig);
  }
}
