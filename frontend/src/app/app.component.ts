import { Component } from '@angular/core';

import '../style/app.scss';

@Component({
  selector: 'my-app', // <my-app></my-app>
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  url = 'https://github.com/socialedge/vega';

  toggleDrawer(drawer): void {
    drawer.toggle();
  }
}
