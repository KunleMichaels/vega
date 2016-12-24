import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'my-identifiers',
  templateUrl: './identifiers.component.html',
  styleUrls: ['./identifiers.component.scss']
})
export class IdentifiersComponent implements OnInit {

  constructor() {
    // Do stuff
  }

  ngOnInit() {
    console.log('Hello Identifiers');
  }

}
