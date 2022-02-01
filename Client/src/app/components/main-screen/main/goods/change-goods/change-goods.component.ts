import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {ServerCommunicationServiceService} from "../../../../../service/server-communication.service";
import {Goods} from "../../../../../interface/goods";
import {Unit} from "../../../../../interface/unit";
import {GoodsName} from "../../../../../interface/goods-name";
import {Router} from "@angular/router";


@Component({
  selector: 'app-change-goods',
  templateUrl: './change-goods.component.html',
  styleUrls: ['./change-goods.component.css']
})
export class ChangeGoodsComponent implements OnInit {

  //list with unit
  unitList: Unit[] = [];

  //list with goods name
  goodsNameList: GoodsName[] = [];

  //list with goods
  goodsList: Goods[] = [];

  //Heading of the table
  header: string[] = ['ID', 'Warenname', 'Einheit', 'Preis', 'Bemerkung', 'im Verkauf'];

  //saves whether there is a search result
  searchResult = true;

  //List of search results
  searchResultList: Goods[] = [];

  //Goods properties
  currentId: number | undefined;
  goodsName: string | undefined;
  unitName: string | undefined;
  goodsPrice: number = 0;
  goodsNote: string | undefined;
  goodsId: number | undefined;
  goodsActive: boolean | undefined;

  selectedValueGoodsName: string | null | undefined;
  selectedValueUnitName: string | null | undefined;

  changeGoodsForm = new FormGroup({
    IDGoodsCha: new FormControl(null, Validators.required),
    goodsNameCha: new FormControl(null, Validators.required),
    unitNameCha: new FormControl(null, Validators.required),
    goodsPriceCha: new FormControl(null, Validators.required),
    goodsNoteCha: new FormControl(null),
    goodsAktiveCha :new FormControl(null, Validators.required),
  });


  constructor(private client: HttpClient,
              private router: Router,
              private communication: ServerCommunicationServiceService) {
  }

  //All goods are loaded first. Then a list of units and names of goods. Finally, the list of goods is copied into the search list.
  ngOnInit(): void {
    this.loadGoods();
    this.getListWithGoodsName();
    this.getListWithUnit();
    this.searchResultList = this.goodsList;
  }

  //As soon as a unit is to be changed or deleted, the values are set.
  setValues(goodsId: number) {
    this.currentId = goodsId;
    for (let key in this.goodsList) {
      if (this.goodsList.hasOwnProperty(key)) {
        if (this.goodsList[key].goodsId == goodsId) {
          this.goodsId = this.goodsList[key].goodsId;
          this.goodsName = this.goodsList[key].goodsName;
          this.unitName = this.goodsList[key].unitName;
          this.goodsPrice = this.goodsList[key].goodsPrice;
          this.goodsNote = this.goodsList[key].goodsNote;
          this.selectedValueGoodsName = this.goodsList[key].goodsName;
          this.selectedValueUnitName = this.goodsList[key].unitName;
        }
      }
    }
  }

//A goods is changed
  changeGoods() {

    //Checks whether the price has been changed.
    if (this.changeGoodsForm.value.goodsPriceCha == null) {
      //runs through a list of all goods, searching for the matching id
      for (let entry in this.goodsList) {
        if (this.goodsList[entry].goodsId == this.currentId) {
          this.changeGoodsForm.value.goodsPriceCha = this.goodsList[entry].goodsPrice;
        }
      }
    }

  //Checks whether the note has been changed.
    if (this.changeGoodsForm.value.goodsNoteCha == null) {
      //runs through a list of all goods, searching for the matching id
      for (let entry in this.goodsList) {
        if (this.goodsList[entry].goodsId == this.currentId) {
          this.changeGoodsForm.value.goodsNoteCha = this.goodsList[entry].goodsNote;
        }
      }
    }


    //goodsActive true or false
    this.goodsActive = this.changeGoodsForm.value.goodsAktiveCha === 'Aktive';

    //set url
    let url = 'goods/update/';
    let data = {
      goodsName: this.changeGoodsForm.value.goodsNameCha,
      unitName: this.changeGoodsForm.value.unitNameCha,
      goodsPrice: this.changeGoodsForm.value.goodsPriceCha,
      goodsNote: this.changeGoodsForm.value.goodsNoteCha,
      selfServiceStandName: localStorage.getItem('key_selfServiceStand_name'),
      userName: localStorage.getItem('key_user_name'),
      goodsId: this.currentId,
      goodsActive: this.goodsActive,
    };

    //communication Server
    this.communication
      .serverCommunication(url, data, 'post', false)
      .subscribe((response: any) => {

        alert('Der Eintrag wurde erfolgreich geändert');
        this.router.navigate(['/goods/overview']).then(() => {
          window.location.reload();
        });
        }, error => {
          console.log(error);
          alert(error);
        }
      );
  }

  // delete the good
  deleteGoods() {

    //set url
    let url = 'goods/delete/';
    let data = {
      selfServiceStandName: localStorage.getItem('key_selfServiceStand_name'),
      userName: localStorage.getItem('key_user_name'),
      goodsId: this.currentId
    };

    //communication Server
    this.communication
      .serverCommunication(url, data, 'post', false)
      .subscribe((response: any) => {

        alert('Der Eintrag wurde erfolgreich gelöscht');
        this.router.navigate(['/goods/overview']).then(() => {
          window.location.reload();
        });

        }, error => {
          console.log(error);
          alert(error);
        }
      );
  }

  //creates a list of search results
  search(event: Event) {

    //clears the list
    this.searchResultList = [];

    //saves the keyboard input
    const filterValue = (event.target as HTMLInputElement).value;

    //checks whether the entry corresponds to the search result
    for (let key in this.goodsList) {
      if (this.goodsList.hasOwnProperty(key)) {
        if (
          this.goodsList[key].goodsName.includes(filterValue) ||
          this.goodsList[key].unitName.includes(filterValue) ||
          this.goodsList[key].goodsPrice.toString().includes(filterValue) ||
          this.goodsList[key].goodsNote.includes(filterValue)
        ) {

          //the result found is added to the list
          this.searchResultList.push((this.goodsList[key]));
        }
      }

      //sets the variable to false if there were no search results
      this.searchResult = this.searchResultList.length > 0;
    }
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


  //load the list with goods
  private loadGoods() {
    //set url
    let url = 'goods/' + localStorage.getItem('key_selfServiceStand_name');

    //communication Server
    this.communication
      .serverCommunication(url, '', 'get', true)
      .subscribe((response: any) => {

          //save entry in list
          for (var entry of response) {
            this.goodsList.push({
              goodsId: entry.goodsId,
              goodsName: entry.goodsNameNr.goodsName,
              unitName: entry.goodsUnitNr.unitName,
              goodsPrice: entry.goodsPrice,
              goodsNote: entry.goodsNote,
              goodsActive:entry.goodsActive,
            });
          }
        }, error => {
          console.log(error);
        }
      );
  }
}
