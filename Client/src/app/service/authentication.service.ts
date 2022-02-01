import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})

/**
 * This service manages the authentication methods.
 */
export class AuthenticationService {

  constructor(
    private router: Router
  ) { }

  /**
   * The token is saved, either in the local storge or in the sesseon storage
   * @param token The transferring token.
   * @param remainSignedIn Stay logged in, yes or no
   */
  saveToken(token: string, remainSignedIn: boolean){

    /**
     * Checks whether the token is saved permanently or only for one session.
     */
    if (remainSignedIn){
      localStorage.setItem('key_token_id', token);
    }else {
      sessionStorage.setItem('key_token_id', token);
    }
  }

  /**
   * Checks whether a token is available, if not it is redirected to the login page.
   */
  checkToken(){
    return sessionStorage.getItem('key_token_id')|| localStorage.getItem('key_token_id') ? '' : this.router.navigate(['/login']);
  }

  /**
   * Deletes all data from the local and session memory.
   */
  deleteToken(){
    localStorage.clear();
    sessionStorage.clear();
  }

  /**
   * return token
   */
  getToken(){
    return sessionStorage.getItem('key_token_id')|| localStorage.getItem('key_token_id');
  }
}
