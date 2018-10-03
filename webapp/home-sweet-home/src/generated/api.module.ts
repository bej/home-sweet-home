import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Configuration } from './configuration';

import { AccountEntityService } from './api/accountEntity.service';
import { BasicErrorControllerService } from './api/basicErrorController.service';
import { ExpenseEntityService } from './api/expenseEntity.service';
import { IncomeEntityService } from './api/incomeEntity.service';
import { ProfileControllerService } from './api/profileController.service';
import { TransactionEntityService } from './api/transactionEntity.service';

@NgModule({
  imports:      [ CommonModule, HttpClientModule ],
  declarations: [],
  exports:      [],
  providers: [
    AccountEntityService,
    BasicErrorControllerService,
    ExpenseEntityService,
    IncomeEntityService,
    ProfileControllerService,
    TransactionEntityService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        }
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import your base AppModule only.');
        }
    }
}
