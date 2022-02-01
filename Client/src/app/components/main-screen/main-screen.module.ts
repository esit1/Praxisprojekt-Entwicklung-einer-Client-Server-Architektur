import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MainScreenRoutesModule} from "./main-screen-routes.module";
import {FooterComponent} from './footer/footer.component';
import {MatTableModule} from "@angular/material/table";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {MatSortModule} from "@angular/material/sort";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatTabsModule} from "@angular/material/tabs";
import {NewGoodsComponent} from "./main/goods/new-goods/new-goods.component";
import {MatButtonModule} from "@angular/material/button";
import {GoodsComponent} from './main/goods/goods.component';
import {ChangeGoodsComponent} from './main/goods/change-goods/change-goods.component';
import {MatPaginatorModule} from "@angular/material/paginator";
import {OverviewGoodsComponent} from './main/goods/overview-goods/overview-goods.component';
import {SettingsGoodsComponent} from './main/goods/settings-goods/settings-goods.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {EndOfDayComponent} from './main/end-of-day/end-of-day.component';
import {ReceiptComponent} from './main/receipt/receipt.component';
import {UserComponent} from './main/user/user.component';
import {StatisticsComponent} from './main/statistics/statistics.component';
import {NavbarComponent} from './navbar/navbar.component';
import {MatMenuModule} from "@angular/material/menu";
import {MainMenuComponent} from './main-menu/main-menu.component';
import {SelfServiceStandComponent} from './main/self-service-stand/self-service-stand.component';
import {OverviewReceiptComponent} from './main/receipt/overview-receipt/overview-receipt.component';
import {OverviewEndComponent} from './main/end-of-day/overview-end/overview-end.component';
import {NewEndComponent} from './main/end-of-day/new-end/new-end.component';
import {MonthComponent} from './main/statistics/month/month.component';
import {YearComponent} from './main/statistics/year/year.component';
import {OverviewUserComponent} from './main/user/overview-user/overview-user.component';
import {ChangeUserComponent} from './main/user/change-user/change-user.component';
import {CreateUserComponent} from './main/user/create-user/create-user.component';
import {OverviewSelfComponent} from './main/self-service-stand/overview-self/overview-self.component';
import {ChangeSelfComponent} from './main/self-service-stand/change-self/change-self.component';
import {AddUserSelfComponent} from './main/self-service-stand/add-user-self/add-user-self.component';
import {LogoutComponent} from './logout/logout.component';
import {NgbAlertModule, NgbDatepickerModule} from "@ng-bootstrap/ng-bootstrap";
import {NewReceiptComponent} from './main/receipt/new-receipt/new-receipt.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import { EndOfDayPdfComponent } from './main/end-of-day/end-of-day-pdf/end-of-day-pdf.component';


@NgModule({
  declarations: [
    FooterComponent,
    NewGoodsComponent,
    GoodsComponent,
    ChangeGoodsComponent,
    OverviewGoodsComponent,
    SettingsGoodsComponent,
    EndOfDayComponent,
    ReceiptComponent,
    UserComponent,
    StatisticsComponent,
    NavbarComponent,
    MainMenuComponent,
    SelfServiceStandComponent,
    OverviewReceiptComponent,
    OverviewEndComponent,
    NewEndComponent,
    MonthComponent,
    YearComponent,
    OverviewUserComponent,
    ChangeUserComponent,
    CreateUserComponent,
    OverviewSelfComponent,
    ChangeSelfComponent,
    AddUserSelfComponent,
    LogoutComponent,
    NewReceiptComponent,
    EndOfDayPdfComponent,
  ],
  exports: [
    FooterComponent,
    NavbarComponent,
    LogoutComponent
  ],
    imports: [
        CommonModule,
        MainScreenRoutesModule,
        MatTableModule,
        MatSelectModule,
        MatInputModule,
        MatSortModule,
        MatCheckboxModule,
        FormsModule,
        MatTabsModule,
        MatButtonModule,
        ReactiveFormsModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
        MatMenuModule,
        NgbDatepickerModule,
        NgbAlertModule,
        MatAutocompleteModule
    ]
})
export class MainScreenModule {
}
