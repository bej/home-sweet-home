import { TestBed } from '@angular/core/testing';

import { IncomeResolverService } from './income-resolver.service';

describe('IncomeResolverService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IncomeResolverService = TestBed.get(IncomeResolverService);
    expect(service).toBeTruthy();
  });
});
