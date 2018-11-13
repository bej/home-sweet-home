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
    password: 'a55f84c6-64fb-411e-a466-72ae71cf068f',
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
