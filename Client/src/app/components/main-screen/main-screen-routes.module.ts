import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {MainScreenComponent} from "./main-screen.component";
import {NewGoodsComponent} from "./main/goods/new-goods/new-goods.component";
import {GoodsComponent} from "./main/goods/goods.component";
import {ReceiptComponent} from "./main/receipt/receipt.component";
import {UserComponent} from "./main/user/user.component";
import {StatisticsComponent} from "./main/statistics/statistics.component";
import {EndOfDayComponent} from "./main/end-of-day/end-of-day.component";
import {MainMenuComponent} from "./main-menu/main-menu.component";
import {SelfServiceStandComponent} from "./main/self-service-stand/self-service-stand.component";
import {OverviewGoodsComponent} from "./main/goods/overview-goods/overview-goods.component";
import {ChangeGoodsComponent} from "./main/goods/change-goods/change-goods.component";
import {SettingsGoodsComponent} from "./main/goods/settings-goods/settings-goods.component";
import {OverviewReceiptComponent} from "./main/receipt/overview-receipt/overview-receipt.component";
import {ChangeUserComponent} from "./main/user/change-user/change-user.component";
import {CreateUserComponent} from "./main/user/create-user/create-user.component";
import {OverviewUserComponent} from "./main/user/overview-user/overview-user.component";
import {MonthComponent} from "./main/statistics/month/month.component";
import {YearComponent} from "./main/statistics/year/year.component";
import {AddUserSelfComponent} from "./main/self-service-stand/add-user-self/add-user-self.component";
import {ChangeSelfComponent} from "./main/self-service-stand/change-self/change-self.component";
import {OverviewSelfComponent} from "./main/self-service-stand/overview-self/overview-self.component";
import {OverviewEndComponent} from "./main/end-of-day/overview-end/overview-end.component";
import {NewEndComponent} from "./main/end-of-day/new-end/new-end.component";
import {NewReceiptComponent} from "./main/receipt/new-receipt/new-receipt.component";
import {EndOfDayPdfComponent} from "./main/end-of-day/end-of-day-pdf/end-of-day-pdf.component";


const myRoutes: Routes = [
  {
    path: '',
    component: MainScreenComponent,
    children: [
      {
        path: 'goods',
        component: GoodsComponent,
        children: [
          {
            path: 'overview',
            component: OverviewGoodsComponent
          },
          {
            path: 'new',
            component: NewGoodsComponent
          },
          {
            path: 'change',
            component: ChangeGoodsComponent
          }, {
            path: 'settings',
            component: SettingsGoodsComponent
          }
        ]
      },
      {
        path: 'receipt',
        component: ReceiptComponent,
        children: [
          {
            path: 'new',
            component: NewReceiptComponent
          },
          {
            path: 'overview',
            component: OverviewReceiptComponent
          },
        ]
      },
      {
        path: 'user',
        component: UserComponent,
        children: [
          {
            path: 'change',
            component: ChangeUserComponent
          },
          {
            path: 'create',
            component: CreateUserComponent
          },
          {
            path: 'overview',
            component: OverviewUserComponent
          }
        ]
      },
      {
        path: 'statistics',
        component: StatisticsComponent,
        children: [
          {
            path: 'month',
            component: MonthComponent
          },
          {
            path: 'year',
            component: YearComponent
          }
        ]
      },
      {
        path: 'self',
        component: SelfServiceStandComponent,
        children: [
          {
            path: 'add',
            component: AddUserSelfComponent
          },
          {
            path: 'change',
            component: ChangeSelfComponent
          },
          {
            path: 'overview',
            component: OverviewSelfComponent
          }
        ]
      },
      {
        path: 'end',
        component: EndOfDayComponent,
        children: [
          {
            path: 'overview',
            component: OverviewEndComponent
          },
          {
            path: 'new',
            component: NewEndComponent
          },
          {
            path: 'create',
            component: EndOfDayPdfComponent
          }
        ]
      },
      {
        path: '',
        component: MainMenuComponent
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
export class MainScreenRoutesModule {
}
