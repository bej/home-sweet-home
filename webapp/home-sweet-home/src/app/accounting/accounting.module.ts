import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountListComponent } from './account-list/account-list.component';
import {AccountsResolverService} from './accounts-resolver.service';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    AccountListComponent
  ],
  declarations: [AccountListComponent],
  providers: [AccountsResolverService]
})
export class AccountingModule { }
