import { RouterModule, Routes } from '@angular/router';

import { ActivityComponent } from './activity/activity.component';
import { AboutComponent } from './about/about.component';
import { DashboardComponent } from "./dashboard/dashboard.component";
import { PassesComponent } from "./passes/passes.component";

const routes: Routes = [
  { path: '', component: DashboardComponent},
  { path: 'passes', component: PassesComponent },
  { path: 'activity', component: ActivityComponent },
  { path: 'about', component: AboutComponent}
];

export const routing = RouterModule.forRoot(routes);
