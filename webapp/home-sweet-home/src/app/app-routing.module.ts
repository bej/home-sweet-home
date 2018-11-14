import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AccountListComponent} from './accounting/account-list/account-list.component';
import {AccountsResolverService} from './accounting/accounts-resolver.service';

const routes: Routes = [
  {
    path: 'accounts',
    component: AccountListComponent,
    resolve: {
      resourcesAccount: AccountsResolverService
    }
  },
  {
    path: '**',
    redirectTo: 'accounts'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
