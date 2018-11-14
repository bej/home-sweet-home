import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountListComponent } from './account-list/account-list.component';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    AccountListComponent
  ],
  declarations: [AccountListComponent]
})
export class AccountingModule { }
