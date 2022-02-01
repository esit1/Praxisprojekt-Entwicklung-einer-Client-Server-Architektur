import {Component, OnInit} from '@angular/core';
import {Observable, Observer} from "rxjs";
import {Tab} from "../../../../interface/tab";
import {AuthenticationService} from "../../../../service/authentication.service";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  asyncTabs: Observable<Tab[]>;

  constructor(private authentication: AuthenticationService) {
    this.asyncTabs = new Observable((observer: Observer<Tab[]>) => {
      setTimeout(() => {
        observer.next([
          {label: 'Benutzerdaten ändern', pfad: 'change'},
        ]);
      }, 1000);
    });
  }

  ngOnInit(): void {
    this.authentication.checkToken();
  }
}
