import {Component, OnInit} from '@angular/core';
import {AccountEntityService, ResourcesAccount, Account} from '../generated';
import {HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'home-sweet-home';

  protected accounts: Array<Account>;

  constructor(private accountEntityService: AccountEntityService) {

  }

  ngOnInit() {
    this.accountEntityService.findAllAccountUsingGET().subscribe((response: ResourcesAccount) => {
      this.accounts = response['_embedded'].accounts;
    });
  }

}
