import {Component, OnInit} from '@angular/core';
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {HttpClient} from "@angular/common/http";
import {ServerCommunicationServiceService} from "../../../../../service/server-communication.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Goods} from "../../../../../interface/goods";
import {ConvertDateService} from "../../../../../service/convert-date.service";


@Component({
  selector: 'app-new-receipt',
  templateUrl: './new-receipt.component.html',
  styleUrls: ['./new-receipt.component.css']
})
/**
 * With the help of this class, we added a new incoming and outgoing goods department.
 */
export class NewReceiptComponent implements OnInit {

  //Calendar model.
  model: NgbDateStruct | undefined;

  //Selected product name.
  selectedName: string | null | undefined;

  //empty list the goods are stored in this list.
  goodsList: Goods[] = [];

  //The form receiptForm is defined.
  receiptForm = new FormGroup({
    datepickerRe: new FormControl(null, Validators.required),
    goodsRe: new FormControl(null, Validators.required),
    goodsPiecesRe: new FormControl(null, Validators.required),
  });

  constructor(private client: HttpClient,
              private communication: ServerCommunicationServiceService,
              private convertDate : ConvertDateService
  ) {
  }

  ngOnInit() {
    //Loads list of goods
    this.getListGoods();
  }

//Get List with goods
  getListGoods() {

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

  //Sends a request to the server with the data, incoming and outgoing goods
  saveEntry() {

    let convertDateString: String = this.convertDate.convertDate(this.receiptForm.value.datepickerRe.year, this.receiptForm.value.datepickerRe.month, this.receiptForm.value.datepickerRe.day);

    //set url
    let url = 'receipt/';
    let data = {
      receiptDate: convertDateString,
      goodsPieces: this.receiptForm.value.goodsPiecesRe,
      goodsID: this.receiptForm.value.goodsRe,
      selfServiceStandName: localStorage.getItem('key_selfServiceStand_name'),
      userName: localStorage.getItem('key_user_name'),
    };

    //communication Server
    this.communication
      .serverCommunication(url, data, 'post', false)
      .subscribe((response: any) => {

        console.log(response);
        alert('Meldung gespeichert');

        }, error => {

          console.log(error);
        alert('Eingabefehler, Daten nochmals überprüfen');

        }
      );

    //Resets the form field
    this.receiptForm.controls['goodsPiecesRe'].reset();
    this.receiptForm.controls['goodsRe'].reset();
  }
}
