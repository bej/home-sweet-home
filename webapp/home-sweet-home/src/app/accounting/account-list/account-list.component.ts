import { Component, OnInit } from '@angular/core';
import {
  Account,
  AccountEntityService,
  ResourcesAccount,
  ResourcesTransaction,
  Transaction,
  TransactionEntityService
} from '../../../generated';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {
  title = 'home-sweet-home';
  protected resourcesAccount: ResourcesAccount;

  protected accounts: Array<Account>;
  protected transactions: Array<Transaction>;

  constructor(private route: ActivatedRoute, private transactionEntityService: TransactionEntityService) {

  }

  ngOnInit() {
    this.resourcesAccount = this.route.snapshot.data.resourcesAccount;

    this.transactionEntityService.findAllTransactionUsingGET().subscribe((response: ResourcesTransaction) => {
      response.embedded = response['_embedded'];
      response.links = response['_links'];
      this.transactions = response.embedded.transactions;
    });
  }
}
