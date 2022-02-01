import {Component, OnInit} from '@angular/core';
import {Observable, Observer} from "rxjs";
import {Tab} from "../../../../interface/tab";
import {AuthenticationService} from "../../../../service/authentication.service";

@Component({
  selector: 'app-self-service-stand',
  templateUrl: './self-service-stand.component.html',
  styleUrls: ['./self-service-stand.component.css']
})
export class SelfServiceStandComponent implements OnInit {

  asyncTabs: Observable<Tab[]>;

  constructor(private authentication: AuthenticationService) {
    this.asyncTabs = new Observable((observer: Observer<Tab[]>) => {
      setTimeout(() => {
        observer.next([
          {label: 'Ändern', pfad: 'change'},
          {label: 'Benutzer hinzufügen', pfad: 'add'},
          {label: 'Benutzerübersicht', pfad: 'overview'}
        ]);
      }, 1000);
    });
  }

  ngOnInit(): void {
    this.authentication.checkToken();
  }

}
