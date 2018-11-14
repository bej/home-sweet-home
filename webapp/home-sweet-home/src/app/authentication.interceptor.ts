import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Configuration, ConfigurationParameters} from '../generated';

export function apiConfigFactory() {
  const params: ConfigurationParameters = {
    // set configuration parameters here.
    username: 'user',
    password: '777a1ec0-2c9c-4a8a-b2e7-095388669578',
    basePath: 'http://localhost:8080'
  }
  return new Configuration(params);
}

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {
  private config: Configuration;

  constructor() {
    this.config = apiConfigFactory();
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    request = request.clone({
      setHeaders: {
        Authorization: `Basic ${btoa(this.config.username + ':' + this.config.password)}`
      }
    });

    return next.handle(request);
  }
}
