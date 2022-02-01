import {Component, OnInit} from '@angular/core';
import {Tab} from "../../../../interface/tab";
import {Observable, Observer} from "rxjs";
import {AuthenticationService} from "../../../../service/authentication.service";

@Component({
  selector: 'app-end-of-day',
  templateUrl: './end-of-day.component.html',
  styleUrls: ['./end-of-day.component.css']
})
export class EndOfDayComponent implements OnInit {

  asyncTabs: Observable<Tab[]>;

  constructor(private authentication: AuthenticationService) {
    this.asyncTabs = new Observable((observer: Observer<Tab[]>) => {
      setTimeout(() => {
        observer.next([
          {label: 'Erstellen', pfad: 'new'},
          {label: 'Pdf erstellen', pfad: 'create'},
          {label: 'Übersicht', pfad: 'overview'}
        ]);
      }, 1000);
    });
  }

  ngOnInit(): void {
    this.authentication.checkToken();
  }

}
