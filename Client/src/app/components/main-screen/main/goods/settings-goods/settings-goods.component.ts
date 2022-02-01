import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {ServerCommunicationServiceService} from "../../../../../service/server-communication.service";

@Component({
  selector: 'app-settings-goods',
  templateUrl: './settings-goods.component.html',
  styleUrls: ['./settings-goods.component.css']
})
export class SettingsGoodsComponent implements OnInit {

  settingsGoodsNameForm = new FormGroup({
    goodsNameSe: new FormControl(null, Validators.required),
  });

  settingUnitForm = new FormGroup({
    unitNameSe: new FormControl(null, Validators.required),
  });

  constructor(private client: HttpClient,
              private communication: ServerCommunicationServiceService
  ) {
  }

  ngOnInit(): void {
  }

  //create a new goods name
  saveGoodsName() {

    //set url
    let url = 'goodsName/' + this.settingsGoodsNameForm.value.goodsNameSe;

    //communication Server
    this.communication
      .serverCommunication(url, '', 'post', false)
      .subscribe((response: any) => {

          alert('Der Warenname '+ response.value.goodsNameSe + ' wurde angelegt.');
          this.settingUnitForm.controls['goodsNameSe'].reset()
        }, error => {
          console.log(error);
        }
      );
  }

  //create an new unit name
  saveUnitName() {

    //set url
    let url = 'unit/' + this.settingUnitForm.value.unitNameSe;

    //communication Server
    this.communication
      .serverCommunication(url, '', 'post', false)
      .subscribe((response: any) => {

        alert('Die Einheit '+ response.value.unitName + ' wurde angelegt.');
        this.settingUnitForm.controls['unitNameSe'].reset()
        }, error => {
          console.log(error);
        }
      );
  }
}
