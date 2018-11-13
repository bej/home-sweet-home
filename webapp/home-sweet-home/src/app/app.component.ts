import {Component, OnInit} from '@angular/core';
import {AccountEntityService, ResourcesAccount, Account, TransactionEntityService, ResourcesTransaction, Transaction} from '../generated';
import {HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
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
