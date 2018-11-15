import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {IncomeEntityService, ResourcesIncome} from '../../generated';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IncomeResolverService implements Resolve<Observable<ResourcesIncome>> {

  constructor(private incomeEntityService: IncomeEntityService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ResourcesIncome> {
    return this.incomeEntityService
      .findAllByAccountIncomeUsingGET(`/accounts/${route.paramMap.get('id')}`)
      .map(response => {
        response.embedded = response['_embedded'];
        response.links = response['_links'];
        return response;
      });
  }
}
