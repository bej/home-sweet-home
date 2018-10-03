import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {ApiModule, Configuration, ConfigurationParameters} from '../generated';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthenticationInterceptor} from './authentication.interceptor';

export function apiConfigFactory() {
  const params: ConfigurationParameters = {
    // set configuration parameters here.
    username: 'user',
    password: '092e5ea1-e536-4f8a-9178-a38397be4ecc',
    basePath: 'http://localhost:8080'
  }
  return new Configuration(params);
}


@NgModule({
  declarations: [
    AppComponent
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
