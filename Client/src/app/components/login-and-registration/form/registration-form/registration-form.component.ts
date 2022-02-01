import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {ServerCommunicationServiceService} from '../../../../service/server-communication.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css']
})
export class RegistrationFormComponent implements OnInit {
  registrationForm = new FormGroup({
    userNameRe: new FormControl(null, Validators.required),
    email: new FormControl(null, Validators.required),
    selfServiceStandName: new FormControl(null, Validators.required),
    userPassword1: new FormControl(null, Validators.required),
    userPassword2: new FormControl(null, Validators.required)
  });

  userNameCorrectly: boolean = true;
  emailCorrectly: boolean = true;
  selfServiceStandCorrectly: boolean = true;
  userPasswordCorrectly: boolean = true;
  userPasswordDouble: boolean = true;

  errorMessageUserName: String = '';
  errorMessagePassword: String = '';
  errorMessagePasswordDouble: String = '';
  errorMessageEmail: String = '';
  errorMessageSelfServiceStandName: String = '';


  constructor(
    private client: HttpClient,
    private communication: ServerCommunicationServiceService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
  }

  registration() {

    let url = 'user/newAdmin';
    let data = {
      userName: this.registrationForm.value.userNameRe,
      userPassword: this.registrationForm.value.userPassword1,
      selfServiceStandName: this.registrationForm.value.selfServiceStandName,
      email: this.registrationForm.value.email,
    };

    this.checkEmail(this.registrationForm.value.email);
    this.checkPasswordDouble(this.registrationForm.value.userPassword1, this.registrationForm.value.userPassword2);
    this.checkPassword(this.registrationForm.value.userPassword1);

    if (this.emailCorrectly && this.userPasswordDouble && this.userPasswordCorrectly) {

      this.communication
        .serverCommunication(url, data, 'post', true)
        .subscribe((response: any) => {

            this.router.navigate(['/']).then(() => {
              window.location.reload();
            });

          }, error => {

            console.log(error.error.Message)
            this.checkUserName(error.error.Message, this.registrationForm.value.userNameRe);
            this.checkSelfServiceStand(error.error.Message, this.registrationForm.value.selfServiceStandName);
          }
        );
    }
  }

  checkSelfServiceStand(message: String, selfServiceStandName: String) {
    if (message.includes('Self-service-stand')) {
      this.selfServiceStandCorrectly = false;
      this.errorMessageSelfServiceStandName = 'Der Name ' + selfServiceStandName + ' ist bereits vergeben.';
    }
  }

  checkEmail(email: String) {
    if (!email.match('[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$')) {
      this.emailCorrectly = false;
      this.errorMessageEmail = 'Die ' + email + ' ist nicht korrekt.';
    }
  }

  checkPasswordDouble(password1: String, password2: String) {

    if (password1 != password2) {
      this.userPasswordDouble = false;
      this.errorMessagePasswordDouble = 'Die eingebenden Passwörter stimmen nicht überein. ';
    }
  }

  checkUserName(message: String, userName: String) {
    if (message.includes('User')) {
      this.userNameCorrectly = false;
      this.errorMessageUserName = 'Der Benutzername ' + userName + ' ist bereits vergeben.';
    }
  }

  checkPassword(password: String) {

    if (!password.match('(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}')) {
      this.userPasswordCorrectly = false;

      this.errorMessagePassword = 'Passwort ist ungültig. Passwort enthält: ';

      //Password does not contain a number.
      if (!password.match('.*[0-9].*')) {
        this.errorMessagePassword = this.errorMessagePassword + 'keine Nummer, ';
      }

      //Password does not contain lowercase letters.
      if (!password.match('(.*[a-z])')) {
        this.errorMessagePassword = this.errorMessagePassword + 'kein Kleinbuchstaben, ';
      }

      //Password does not contain capital letters.
      if (!password.match('(.*[A-Z])')) {
        this.errorMessagePassword = this.errorMessagePassword + 'kein Großbuchstaben, ';
      }

      //Password contains spaces.
      if (!password.match('(\\S+$)')) {
        this.errorMessagePassword = this.errorMessagePassword + 'kein Sonderzeichen, ';
      }

      //Password does not contain 6 characters.
      if (!password.match('.{6,}')) {
        this.errorMessagePassword = this.errorMessagePassword + 'keine Mindestlänge von 6, ';
      }

      //Deletes the last two characters.
      this.errorMessagePassword = this.errorMessagePassword.substring(0, this.errorMessagePassword.length - 2);

      this.errorMessagePassword = this.errorMessagePassword + '.';

    }
  }
}
