import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountListComponent } from './account-list/account-list.component';
import {AccountsResolverService} from './accounts-resolver.service';
import { AccountDetailsComponent } from './account-details/account-details.component';
import {AccountResolverService} from './account-resolver.service';
import {RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FormsModule
  ],
  exports: [
    AccountListComponent,
    AccountDetailsComponent
  ],
  declarations: [AccountListComponent, AccountDetailsComponent],
  providers: [
    AccountsResolverService,
    AccountResolverService
  ]
})
export class AccountingModule { }
