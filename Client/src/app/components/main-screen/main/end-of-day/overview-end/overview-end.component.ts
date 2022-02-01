import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {HttpClient} from "@angular/common/http";
import {ServerCommunicationServiceService} from "../../../../../service/server-communication.service";
import {ConvertDateService} from "../../../../../service/convert-date.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {EndOfTheDayOverview} from "../../../../../interface/end-of-the-day-overwiew";

/**
 * Overview of all end of the day overview.
 */
@Component({
  selector: 'app-overview-end',
  templateUrl: './overview-end.component.html',
  styleUrls: ['./overview-end.component.css']
})
export class OverviewEndComponent implements OnInit {

  //datepicker
  model: NgbDateStruct | undefined;

  //End the list with end of the day
  overviewEndList: EndOfTheDayOverview[] = [];

  //selected date by the user
  selectDate: String = '';

  //today's date
  todayDate: any;

  //today's date as a string
  todayDateString: String | undefined;


  OverviewEndForm = new FormGroup({
    datepickerEnOv: new FormControl(null, Validators.required),
  });

  constructor(private client: HttpClient,
              private communication: ServerCommunicationServiceService,
              private convertDate: ConvertDateService
  ) {
  }

  //First loads the date. Then the date is converted. Finally, a list of daily closings is loaded.
  ngOnInit(): void {
    this.todayDate = new Date;
    this.todayDateString = this.convertDate.convertDate(this.todayDate.getFullYear(), this.todayDate.getUTCMonth() + 1, this.todayDate.getDate());
    this.getListWithEntry();
  }

  //Requests a list of all daily closing from the server
  getListWithEntry() {
    //set url
    let url = 'endOfDay/' + localStorage.getItem('key_selfServiceStand_name');

    //communication Server
    this.communication
      .serverCommunication(url, '', 'get', true)
      .subscribe((response: any) => {

          //save entry in list
          for (var entry of response) {
            this.overviewEndList.push({
              goodsName: entry.goodsName,
              goodsUnit: entry.goodsUnit,
              graduationCount: entry.graduationCount,
              graduationDate: entry.graduationDate,
              graduationRevenue: entry.graduationRevenue,
              graduationSold: entry.graduationSold,
              goodsPrice: entry.goodsPrice,
            });
          }
        }, error => {
          console.log(error);
        }
      );
  }

  //convert date
  OverviewEnd() {
    this.selectDate = this.convertDate.convertDate(this.OverviewEndForm.value.datepickerEnOv.year, this.OverviewEndForm.value.datepickerEnOv.month, this.OverviewEndForm.value.datepickerEnOv.day);
  }
}

