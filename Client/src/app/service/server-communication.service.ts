import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthenticationService} from "./authentication.service";

//
enum HTTPMethods  {
  get = 'get',
  post = 'post',
  delete = 'delete',
  put = 'put'
}

@Injectable({
  providedIn: 'root'
})

/**
 * Contains the information about communication with the server.
 */
export class ServerCommunicationServiceService {

  //save the token
  token = this.authentication.getToken();

  //save the url
  prefix: string = 'http://localhost:8080/';

  //save the http options
  httpOptions = {
    headers: new HttpHeaders({
      Accept: 'application/json', 'Content-Type': 'application/json', Authorization: 'Bearer ' + this.token,
    }),
  };

  constructor(private http: HttpClient,
  private authentication:AuthenticationService
  ) {
  }

  /**
   *
   * @param url url
   * @param data data
   * @param httpMethodsType communicationType(get,post, delete, put)
   * @param noheader header, yes or o
   */
  serverCommunication(url: string, data: any, httpMethodsType: string, noheader: boolean) {

    // POST-Requests
    if (httpMethodsType === HTTPMethods.post) {
      if (noheader) {
        return this.http.post(this.prefix + url, data);
      } else {
        return this.http.post(this.prefix + url, data, this.httpOptions);
      }

      // PUT-Requests
    } else if (httpMethodsType === HTTPMethods.put) {
      return this.http.put(this.prefix + url, data, this.httpOptions);

      //GET-Requests
    } else {
      return this.http.get(this.prefix + url, this.httpOptions);
    }
  }
}
