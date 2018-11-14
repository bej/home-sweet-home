import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AccountListComponent} from './accounting/account-list/account-list.component';

const routes: Routes = [
  { path: 'accounts', component: AccountListComponent },
  { path: '**', redirectTo: 'accounts'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
