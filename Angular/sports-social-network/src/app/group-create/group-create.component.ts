import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

import { Group } from '../models/group';
import { SPORTS, TYPE } from '../constants';
import { PostsService } from '../services/posts.service';
import { Post } from '../models/post';
import { GroupsService } from '../services/groups.service';

@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.scss']
})
export class GroupCreateComponent implements OnInit {

  imageUrl = '/assets/images/default-image.png';
  fileToUpload: File = null;
  yourGroups$: Observable<Group[]>;
  type = TYPE;
  sports = SPORTS;

  model: any = {};

  posts$: Observable<Post[]>;

  constructor(
    private router: Router,
    private postsService: PostsService,
    private groupsService: GroupsService
  ) { }

  ngOnInit() {
    this.posts$ = this.postsService.getAllPosts();
  }

  onCreate(groupName, groupDescription, groupSport, groupType, groupMaxMembers) {
    const groupToCreate: Group = new Group();
    groupToCreate.name = groupName.value;
    groupToCreate.description = groupDescription.value;
    groupToCreate.category = groupSport.value;
    groupToCreate.type = groupType.value;
    groupToCreate.numberMembers = groupMaxMembers.value;
    groupToCreate.logo = '../../assets/images/' + this.fileToUpload.name;
    this.groupsService.createGroup(groupToCreate);
    this.router.navigateByUrl('/groups');
  }

  onCancel() {
    this.router.navigateByUrl('/groups');
  }

  goBack() {
    this.router.navigateByUrl('/groups');
  }
}
