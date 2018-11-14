import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AccountListComponent} from './accounting/account-list/account-list.component';
import {AccountsResolverService} from './accounting/accounts-resolver.service';
import {AccountDetailsComponent} from './accounting/account-details/account-details.component';
import {AccountResolverService} from './accounting/account-resolver.service';

const routes: Routes = [
  {
    path: 'accounts',
    component: AccountListComponent,
    resolve: {
      resourcesAccount: AccountsResolverService
    }
  },
  {
    path: 'accounts/:id',
    component: AccountDetailsComponent,
    resolve: {
      resourceAccount: AccountResolverService
    },
    runGuardsAndResolvers: 'always'
  },
  {
    path: '**',
    redirectTo: 'accounts'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
