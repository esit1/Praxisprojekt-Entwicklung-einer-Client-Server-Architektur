import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {ServerCommunicationServiceService} from "../../../../service/server-communication.service";
import {Router} from '@angular/router';
import {AuthenticationService} from "../../../../service/authentication.service";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
  providers: [ServerCommunicationServiceService]
})
export class LoginFormComponent implements OnInit {

  loginForm = new FormGroup({
    userName: new FormControl(null, Validators.required),
    userPassword: new FormControl(null, [Validators.required, Validators.pattern("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}")]),
    remainSignedIn :new FormControl(null)
  })

  //error message
  errorMessage = 'Der Benutzername oder das Passwort wurden falsch eingeben.';

  //An error was triggered.
  errorStatus: boolean = false;

  constructor(
    private client: HttpClient,
    private communication: ServerCommunicationServiceService,
    private router: Router,
    private authentication: AuthenticationService
  ) {
  }

  ngOnInit(): void {
  }

//Method sends a mail request to the server, with the login data, if this is valid, it is forwarded to the main menu
  login() {

    let url = 'login/';
    let data = {
      userName: this.loginForm.value.userName,
      userPassword: this.loginForm.value.userPassword,
    };

    //Establishes a connection to the server
    this.communication
      .serverCommunication(url, data, 'post', true)
      .subscribe((response: any) => {

        //set the user name
        localStorage.setItem('key_user_name', response.userName);
        this.authentication.saveToken(response.token, this.loginForm.value.remainSignedIn);

          this.router.navigate(['/']).then(() => {
            window.location.reload();
          });
        }, error => {
          console.log(error);
        }
      );

    this.loginForm.controls['userPassword'].reset();
  }
}
