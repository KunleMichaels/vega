import { Component, OnInit } from '@angular/core';
import { ApiService } from './../shared';

@Component({
  selector: 'my-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(private api: ApiService) {
  }

  ngOnInit() {
    console.log('Hello Dashboard');
  }

}
