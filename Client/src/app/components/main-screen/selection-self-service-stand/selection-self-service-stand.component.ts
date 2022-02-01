import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ServerCommunicationServiceService} from "../../../service/server-communication.service";
import {SelfServiceStand} from "../../../interface/self-service-stand";


@Component({
  selector: 'app-selection-self-service-stand',
  templateUrl: './selection-self-service-stand.component.html',
  styleUrls: ['./selection-self-service-stand.component.css']
})
export class SelectionSelfServiceStandComponent implements OnInit {

  selectedValue: string | null = localStorage.getItem('key_selfServiceStand_name');

  selfServiceStandList: SelfServiceStand[] = [];

  constructor(private client: HttpClient,
              private communication: ServerCommunicationServiceService
  ) {
  }

  ngOnInit(): void {
    this.getListWithSelfServiceStandName();
  }


  select() {
    if (typeof this.selectedValue === "string") {
      localStorage.setItem('key_selfServiceStand_name', this.selectedValue);
    }
  }


//Get List with self-service-stand name
  getListWithSelfServiceStandName() {

    //set url
    let url = 'selfServiceStand/list/' + localStorage.getItem('key_user_name');

    //communication Server
    this.communication
      .serverCommunication(url, '', 'get', true)
      .subscribe((response: any) => {

          //save entry in list
          for (var entry of response) {
            this.selfServiceStandList.push({name: entry.selfServiceStandName});
          }

          //if selfServiceStand empty, load local Storage varaibel self-Service-Stand
          if (localStorage.getItem('key_selfServiceStand_name') === null) {
            localStorage.setItem('key_selfServiceStand_name', response[0].selfServiceStandName);
          }
        }, error => {
          console.log(error);
        }
      );
  }

}
