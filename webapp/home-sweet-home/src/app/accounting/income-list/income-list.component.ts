import {Component, Input, OnInit} from '@angular/core';
import {Income, ResourcesIncome} from '../../../generated';

@Component({
  selector: 'app-income-list',
  templateUrl: './income-list.component.html',
  styleUrls: ['./income-list.component.css']
})
export class IncomeListComponent implements OnInit {
  @Input()
  protected resourcesIncome: ResourcesIncome;

  constructor() {

  }

  ngOnInit() {

  }

  getIdForIncome(income: Income) {
    const selfLink = income['_links'].self;
    const fragments = selfLink.href.split('/');

    return fragments[fragments.length - 1];
  }

}
