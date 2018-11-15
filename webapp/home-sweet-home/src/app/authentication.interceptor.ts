import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Configuration, ConfigurationParameters} from '../generated';

export function apiConfigFactory() {
  const params: ConfigurationParameters = {
    username: 'user',
    password: '90f1a1b9-c98a-49c1-a77c-7cc5a19756ac',
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
