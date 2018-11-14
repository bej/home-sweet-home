import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {
  Income,
  IncomeEntityService,
  ResourceAccount,
  ResourcesTransaction,
  Transaction,
  TransactionEntityService
} from '../../../generated';
import {Observable} from 'rxjs';
import 'rxjs-compat/add/observable/of';
import 'rxjs-compat/add/operator/delay';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {
  protected account: ResourceAccount;
  protected transactions: Array<Transaction>;
  payment: any = {
    amount: 0
  };


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private transactionEntityService: TransactionEntityService,
    private incomeEntityServie: IncomeEntityService) { }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.account = data.resourceAccount;

      this.transactionEntityService.findAllTransactionUsingGET().subscribe((response: ResourcesTransaction) => {
        response.embedded = response['_embedded'];
        response.links = response['_links'];
        this.transactions = response.embedded.transactions;
      });
    });

  }

  onPaymentSubmit() {
    const income: Income = {
      account: this.account['_links'].self.href,
      amount: this.payment.amount,
      title: 'Random Payment'
    };
    this.incomeEntityServie.saveIncomeUsingPOST(income).subscribe(response => {
        this.router.navigate([this.router.url]);
    });
  }
}
