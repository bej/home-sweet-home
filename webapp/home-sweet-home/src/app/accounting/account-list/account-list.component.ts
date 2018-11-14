import { Component, OnInit } from '@angular/core';
import {
  Account,
  AccountEntityService,
  ResourcesAccount,
  ResourcesTransaction,
  Transaction,
  TransactionEntityService
} from '../../../generated';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {
  title = 'home-sweet-home';

  protected accounts: Array<Account>;
  protected transactions: Array<Transaction>;

  constructor(private accountEntityService: AccountEntityService, private transactionEntityService: TransactionEntityService) {

  }

  ngOnInit() {
    this.accountEntityService.findAllAccountUsingGET().subscribe((response: ResourcesAccount) => {
      response.embedded = response['_embedded'];
      response.links = response['_links'];
      this.accounts = response.embedded.accounts;
    });

    this.transactionEntityService.findAllTransactionUsingGET().subscribe((response: ResourcesTransaction) => {
      response.embedded = response['_embedded'];
      response.links = response['_links'];
      this.transactions = response.embedded.transactions;
    });
  }
}
