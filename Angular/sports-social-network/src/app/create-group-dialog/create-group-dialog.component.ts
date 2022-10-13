import { Component, OnInit, Inject } from '@angular/core';
import { SPORTS, TYPE } from '../constants';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { Group } from '../models/group';
import { GroupsService } from '../services/groups.service';
import { GroupsComponent } from '../groups/groups.component';

@Component({
  selector: 'app-create-group-dialog',
  templateUrl: './create-group-dialog.component.html',
  styleUrls: ['./create-group-dialog.component.scss']
})
export class CreateGroupDialogComponent implements OnInit {

  type = TYPE;
  sports = SPORTS;

  name: string;
  members: number;
  description: string;
  selectedSport: string;
  selectedPrivacy: string;
  created = false;
  fileToUpload: File = null;

  constructor(
    private router: Router,
    private dialogRef: MatDialogRef<CreateGroupDialogComponent>
  ) { }

  ngOnInit() {
  }

  goHome() {
   this.dialogRef.close();
  }

  createGroup() {
    this.created = true;
    //this.dialogRef.close({ createdGroup: createdGroup });
  }

  goHomeWithCreatedGroup() {
    const createdGroup: Group = new Group();
    createdGroup.name = this.name;
    createdGroup.category = this.selectedSport;
    createdGroup.type = this.selectedPrivacy;
    createdGroup.numberMembers = 1;
    createdGroup.description = this.description;
    createdGroup.logo = '../../assets/images/';
    createdGroup.maxNumber = this.members;
    this.dialogRef.close({ createdGroup: createdGroup });
    }

  handleFileInput(file: any) {
    this.fileToUpload = file.item(0);
  }
}
