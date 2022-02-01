import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModuleModule} from './app-routing-module.module';

import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SelectionSelfServiceStandComponent} from './components/main-screen/selection-self-service-stand/selection-self-service-stand.component';
import {MatSelectModule} from "@angular/material/select";
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MainScreenComponent} from './components/main-screen/main-screen.component';
import {MainScreenModule} from "./components/main-screen/main-screen.module";
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';


@NgModule({
  declarations: [
    AppComponent,
    SelectionSelfServiceStandComponent,
    MainScreenComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModuleModule,
    FormsModule,
    MatSelectModule,
    NoopAnimationsModule,
    MainScreenModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
