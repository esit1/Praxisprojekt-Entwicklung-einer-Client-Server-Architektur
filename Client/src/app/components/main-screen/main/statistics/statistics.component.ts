import {Component, OnInit} from '@angular/core';
import {Observable, Observer} from "rxjs";
import {Tab} from "../../../../interface/tab";
import {AuthenticationService} from "../../../../service/authentication.service";

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  asyncTabs: Observable<Tab[]>;

  constructor(private authentication: AuthenticationService) {
    this.asyncTabs = new Observable((observer: Observer<Tab[]>) => {
      setTimeout(() => {
        observer.next([
          {label: 'Monatsstatistik', pfad: 'month'},
          {label: 'Jahresstatistik', pfad: 'year'}
        ]);
      }, 1000);
    });
  }

  ngOnInit(): void {
    this.authentication.checkToken();
  }

}
