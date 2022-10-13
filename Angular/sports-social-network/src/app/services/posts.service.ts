import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {  Post } from '../models/post';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class PostsService {

  constructor(private httpClient: HttpClient) { }

  public getAllPosts(): Observable<Post[]> {
    const fileUrl = '../../assets/demo-data/posts.json';
    return this.httpClient.get<Post[]>(fileUrl);
  }
}
