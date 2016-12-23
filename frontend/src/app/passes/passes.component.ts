import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'my-passes',
  templateUrl: './passes.component.html',
  styleUrls: ['./passes.component.scss']
})
export class PassesComponent implements OnInit {

  constructor() {
    // Do stuff
  }

  ngOnInit() {
    console.log('Hello Passes');
  }

}
