import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ServerCommunicationServiceService} from "../../../../../service/server-communication.service";
import {EndOfDay} from "../../../../../interface/end-of-day";
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ConvertDateService} from "../../../../../service/convert-date.service";

/**
 * Class creates a new daily closing
 */
@Component({
  selector: 'app-new-end',
  templateUrl: './new-end.component.html',
  styleUrls: ['./new-end.component.css']
})
export class NewEndComponent implements OnInit {

  // List with all end of the day entries
  endOfTheDayList: EndOfDay[] =[];

  //datepicker
  model: NgbDateStruct | undefined;

  endForm = this.formBuilder.group({
    datepickerEn: new FormControl('', [ Validators.required ]),
    goodsCountEn: this.formBuilder.array([
    ], [Validators.required])
  });

//name of goods
get name() {
    return this.endForm.get('name') as FormControl;
  }
  //return goods count
  get goodsCountEn() {
    return this.endForm.get('goodsCountEn') as FormArray;
  }

  constructor(
    private formBuilder: FormBuilder,
    private client: HttpClient,
    private communication: ServerCommunicationServiceService,
    private convertDate : ConvertDateService
  ) {
  }

  //Loads a list with all active goods
  ngOnInit(): void {
    this.getlistwithGoodsAktive();
  }

//A list of all active goods
  getlistwithGoodsAktive() {

    //set url
    let url = 'goods/active/' + localStorage.getItem('key_selfServiceStand_name');

    //communication Server
    this.communication
      .serverCommunication(url, '', 'get', true)
      .subscribe((response: any) => {

          //save entry in list
          for (var entry of response) {
            this.endOfTheDayList.push({
              goodsId: entry.goodsId,
              goodsName: entry.goodsNameNr.goodsName,
              goodsNote: entry.goodsNote,
              goodsPrice: entry.goodsPrice,
              unitName: entry.goodsUnitNr.unitName,
              goodsCount: 0
              }
            );
            //create an new form control element
            this.goodsCountEn.push(new FormControl());
          }
        }, error => {
          console.log(error);
        }
      );
  }

  //Get List with self-service-stand name

  countGoods() {

    let convertDateString: String = this.convertDate.convertDate(this.endForm.value.datepickerEn.year, this.endForm.value.datepickerEn.month, this.endForm.value.datepickerEn.day);

    for (let i in this.endOfTheDayList ){

    //set url
    let url = 'endOfDay/';
    let data = {
      graduationDate: convertDateString,
      goodsID: this.endOfTheDayList[i].goodsId,
      userName: localStorage.getItem('key_user_name'),
      selfServiceStandName: localStorage.getItem('key_selfServiceStand_name'),
      graduationCount: this.endForm.value.goodsCountEn[i]
    };

    //communication Server
    this.communication
      .serverCommunication(url, data, 'post', false)
      .subscribe((response: any) => {

        }, error => {
          alert(error);
          console.log(error);
        }
      );
  }
  }
}
