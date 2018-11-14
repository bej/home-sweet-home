import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {AccountEntityService, ResourcesAccount} from '../../generated';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import 'rxjs-compat/add/operator/map';

@Injectable({
  providedIn: 'root'
})
export class AccountsResolverService implements Resolve<Observable<ResourcesAccount>> {

  constructor(private accountEntityService: AccountEntityService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ResourcesAccount> {
    return this.accountEntityService.findAllAccountUsingGET().map(response => {
      response.embedded = response['_embedded'];
      response.links = response['_links'];
      return response;
    });
  }
}
