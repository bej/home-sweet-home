import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {AccountEntityService, ResourceAccount} from '../../generated';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AccountResolverService implements Resolve<Observable<ResourceAccount>> {

  constructor(private accountEntityService: AccountEntityService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ResourceAccount> {
    const accountId = Number(route.paramMap.get('id'));
    return this.accountEntityService.findByIdAccountUsingGET(accountId);
  }
}
