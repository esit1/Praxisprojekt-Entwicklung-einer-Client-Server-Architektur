import {Component, OnInit} from '@angular/core';
import {Observable, Observer} from "rxjs";
import {Tab} from "../../../../interface/tab";
import {AuthenticationService} from "../../../../service/authentication.service";

@Component({
  selector: 'app-receipt',
  templateUrl: './receipt.component.html',
  styleUrls: ['./receipt.component.css']
})
export class ReceiptComponent implements OnInit {

  asyncTabs: Observable<Tab[]>;

  constructor(private authentication: AuthenticationService) {
    this.asyncTabs = new Observable((observer: Observer<Tab[]>) => {
      setTimeout(() => {
        observer.next([
          {label: 'Eingabe', pfad: 'new'},
          {label: 'Übersicht', pfad: 'overview'}
        ]);
      }, 1000);
    });
  }

  ngOnInit(): void {
    this.authentication.checkToken();
  }

}
