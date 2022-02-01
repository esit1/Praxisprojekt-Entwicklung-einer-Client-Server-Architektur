import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../../service/authentication.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(
    private authentication: AuthenticationService
  ) {}

  ngOnInit(): void {
  }

  //All data stored in the local memory will be deleted
  logout() {
    this.authentication.deleteToken();
  }
}
