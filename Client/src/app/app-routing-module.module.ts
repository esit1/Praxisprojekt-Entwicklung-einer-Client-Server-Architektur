import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";

/**
 * The route information is stored in this variable
 */
const myRoutes: Routes = [
  {
    path: '',
    loadChildren: () => import('./components/main-screen/main-screen-routes.module').then(mod => mod.MainScreenRoutesModule)
  },
  {
    path: 'login',
    loadChildren: () => import('./components/login-and-registration/login-and-registration-module.module').then(mod => mod.LoginAndRegistrationModuleModule)
  },
  {
    path:'**',
    component: PageNotFoundComponent,
  },
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(myRoutes)
  ],
  exports: [RouterModule],
})
export class AppRoutingModuleModule {
}
