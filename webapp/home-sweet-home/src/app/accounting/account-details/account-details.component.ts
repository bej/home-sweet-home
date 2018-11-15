import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {
  Income,
  IncomeEntityService,
  ResourceAccount, ResourcesIncome,
  ResourcesTransaction,
  Transaction,
  TransactionEntityService
} from '../../../generated';
import 'rxjs-compat/add/observable/of';
import 'rxjs-compat/add/operator/delay';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {
  protected account: ResourceAccount;
  protected resourcesIncome: ResourcesIncome;
  protected transactions: Array<Transaction>;
  payment: Income = {
    amount: 0,
    title: 'New Payment'
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private transactionEntityService: TransactionEntityService,
    private incomeEntityService: IncomeEntityService) { }

  ngOnInit() {
    this.route.data.subscribe(data => {
      const accountUrl: string = data.resourceAccount['_links'].self.href;
      this.account = data.resourceAccount;
      this.resourcesIncome = data.resourcesIncome;
      // @ts-ignore
      // special handling: Data-Rest uses the resource URL as identifier
      // but not the full model as generated from swagger file
      this.payment.account = accountUrl;

      this.transactionEntityService.findAllByFromAccountTransactionUsingGET(accountUrl)
        .subscribe((response: ResourcesTransaction) => {
          response.embedded = response['_embedded'];
          response.links = response['_links'];
          this.transactions = response.embedded.transactions;
        });
    });

  }

  onPaymentSubmit() {
    this.incomeEntityService
      .saveIncomeUsingPOST(this.payment)
      .subscribe(response => {
        this.router.navigate([this.router.url]);
      });
  }
}
