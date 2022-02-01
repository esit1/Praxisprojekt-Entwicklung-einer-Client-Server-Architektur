import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ServerCommunicationServiceService} from '../../../../../service/server-communication.service';
import {HttpClient} from '@angular/common/http';
import {Router} from "@angular/router";
import {Unit} from "../../../../../interface/unit";
import {GoodsName} from "../../../../../interface/goods-name";


@Component({
  selector: 'app-new-goods',
  templateUrl: './new-goods.component.html',
  styleUrls: ['./new-goods.component.css'],
  providers: [ServerCommunicationServiceService],
})

/**
 * Create a new goods
 */
export class NewGoodsComponent implements OnInit {

  //the goods properties
  goodsName: string | undefined;
  unitName: string | undefined;
  goodsPrice: number | undefined;
  goodsNote: string | undefined;
  createNew: boolean = false;
  message: String = '';

  selectedValueGoodsName: string | null | undefined;
  selectedValueUnitName: string | null | undefined;

  newGoodsForm = new FormGroup({
    goodsNameNew: new FormControl(null, Validators.required),
    unitNameNew: new FormControl(null, Validators.required),
    goodsPriceNew: new FormControl(null, Validators.required),
    goodsNoteNew: new FormControl(null, Validators.required),
  });

  //list of units
  unitList: Unit[] = [];

  //list of goods name
  goodsNameList: GoodsName[] = [];

  constructor(private client: HttpClient,
              private communication: ServerCommunicationServiceService,
              private router: Router
  ) {
  }

  //loads list with goods name and units
  ngOnInit(): void {
    this.getListWithGoodsName();
    this.getListWithUnit();
  }

//saves the new entry and transfers it to the server
  saveEntry() {

    //If the note is empty, this '' is used.
    if (this.newGoodsForm.value.goodsNoteNew === null) {
      this.newGoodsForm.value.goodsNoteNew = '';
    }

    //set url
    let url = 'goods/';
    let data = {
      goodsName: this.newGoodsForm.value.goodsNameNew,
      unitName: this.newGoodsForm.value.unitNameNew,
      goodsPrice: this.newGoodsForm.value.goodsPriceNew,
      goodsNote: this.newGoodsForm.value.goodsNoteNew,
      selfServiceStandName: localStorage.getItem('key_selfServiceStand_name'),
      userName: localStorage.getItem('key_user_name'),
      goodsActive:true,
    };

    //communication Server
    this.communication
      .serverCommunication(url, data, 'post', false)
      .subscribe((response: any) => {
            this.createNew = true;
            this.message = 'Die Ware ' + this.goodsName + 'wurde angelegt';

        alert('Der Eintrag wurde erfolgreich angelegt');
            this.router.navigate(['/goods/overview']).then(() => {
              window.location.reload();
            });
        }, error => {
          alert('Fehlermeldung' + error);
          console.log(error);
        }
      );
  }


  //Get List with goods name
  getListWithGoodsName() {

    //set url
    let url = 'goodsName/';

    //communication Server
    this.communication
      .serverCommunication(url, '', 'get', true)
      .subscribe((response: any) => {
          //save entry in list
          for (const entry of response) {
            this.goodsNameList.push({goodsName: entry.goodsName});
          }
        }, error => {
          console.log(error);
        }
      );
  }

  //Get List with goods name
  getListWithUnit() {

    //set url
    let url = 'unit/';

    //communication Server
    this.communication
      .serverCommunication(url, '', 'get', true)
      .subscribe((response: any) => {
          //save entry in list
          for (const entry of response) {
            this.unitList.push({unitName: entry.unitName});
          }
        }, error => {
          console.log(error);
        }
      );
  }
}
