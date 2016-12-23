import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'my-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.scss']
})
export class ActivityComponent implements OnInit {

  constructor() {
    // Do stuff
  }

  ngOnInit() {
    console.log('Hello Your Activity');
  }

}
