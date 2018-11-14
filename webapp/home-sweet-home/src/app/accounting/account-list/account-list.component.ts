import { Component, OnInit } from '@angular/core';
import {
  ResourcesAccount
} from '../../../generated';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {
  protected resourcesAccount: ResourcesAccount;

  constructor(private route: ActivatedRoute) {

  }

  ngOnInit() {
    this.resourcesAccount = this.route.snapshot.data.resourcesAccount;
  }

  getIdForAccount(account: Account) {
    const selfLink = account['_links'].self;
    const fragments = selfLink.href.split('/');

    return fragments[fragments.length - 1];
  }
}
