import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginAndRegistrationComponent} from './login-and-registration.component';
import {LogoComponent} from './logo/logo.component';
import {FormComponent} from './form/form.component';
import {FooterComponent} from './footer/footer.component';
import {LoginFormComponent} from './form/login-form/login-form.component';
import {RegistrationFormComponent} from './form/registration-form/registration-form.component';
import {PasswordResetComponent} from './password-reset/password-reset.component';
import {DataProtectionComponent} from './data-protection/data-protection.component';
import {ImprintComponent} from './imprint/imprint.component';
import {ContactComponent} from './contact/contact.component';
import {ReactiveFormsModule} from "@angular/forms";
import {LoginAndRegistrationRoutesModule} from "./login-and-registration-routes.module";


@NgModule({
  declarations: [
    LoginAndRegistrationComponent,
    LogoComponent,
    FormComponent,
    FooterComponent,
    LoginFormComponent,
    RegistrationFormComponent,
    PasswordResetComponent,
    DataProtectionComponent,
    ImprintComponent,
    ContactComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    LoginAndRegistrationRoutesModule
  ]
})
export class LoginAndRegistrationModuleModule {
}
