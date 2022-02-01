import {Component, OnInit} from '@angular/core';
import {Observable, Observer} from "rxjs";
import {Tab} from "../../../../interface/tab";
import {AuthenticationService} from "../../../../service/authentication.service";

@Component({
  selector: 'app-goods',
  templateUrl: './goods.component.html',
  styleUrls: ['./goods.component.css']
})
export class GoodsComponent implements OnInit{

  asyncTabs: Observable<Tab[]>;

  constructor(private authentication: AuthenticationService) {
    this.asyncTabs = new Observable((observer: Observer<Tab[]>) => {
      setTimeout(() => {
        observer.next([
          {label: 'Ware anlegen', pfad: 'new'},
          {label: 'Ware ändern/löschen', pfad: 'change'},
          {label: 'Warenoptionen', pfad: 'settings'},
          {label: 'Übersicht', pfad: 'overview'}
        ]);
      }, 1000);
    });
  }

  ngOnInit(): void {
    this.authentication.checkToken();
  }

}
