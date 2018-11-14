import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {ApiModule, Configuration, ConfigurationParameters} from '../generated';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {apiConfigFactory, AuthenticationInterceptor} from './authentication.interceptor';
import { AccountListComponent } from './account-list/account-list.component';


@NgModule({
  declarations: [
    AppComponent,
    AccountListComponent
  ],
  imports: [
    BrowserModule,
    ApiModule.forRoot(apiConfigFactory),
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
