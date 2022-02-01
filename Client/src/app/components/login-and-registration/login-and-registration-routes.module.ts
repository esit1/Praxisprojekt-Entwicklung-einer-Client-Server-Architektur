import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {LoginAndRegistrationComponent} from "./login-and-registration.component";
import {FormComponent} from "./form/form.component";
import {DataProtectionComponent} from "./data-protection/data-protection.component";
import {ImprintComponent} from "./imprint/imprint.component";
import {ContactComponent} from "./contact/contact.component";
import {PasswordResetComponent} from "./password-reset/password-reset.component";


const myRoutes: Routes = [
  {
    path: '',
    component: LoginAndRegistrationComponent,
    children: [
      {
        path: '',
        component: FormComponent
      },
      {
        path: 'data',
        component: DataProtectionComponent
      },
      {
        path: 'imprint',
        component: ImprintComponent
      },
      {
        path: 'contact',
        component: ContactComponent
      },
      {
        path: 'reset',
        component: PasswordResetComponent
      }
    ]
  }
]

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild((myRoutes))
  ],
  exports: [RouterModule]
})
export class LoginAndRegistrationRoutesModule {
}
