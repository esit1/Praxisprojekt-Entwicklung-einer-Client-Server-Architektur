import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {EndOfTheDayOverview} from "../../../../../interface/end-of-the-day-overwiew";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {ServerCommunicationServiceService} from "../../../../../service/server-communication.service";
import {ConvertDateService} from "../../../../../service/convert-date.service";
import html2canvas from "html2canvas";
import jsPDF from "jspdf";

/**
 * Class provides a pdf document of the chosen daily closing
 */
@Component({
  selector: 'app-end-of-day-pdf',
  templateUrl: './end-of-day-pdf.component.html',
  styleUrls: ['./end-of-day-pdf.component.css']
})
export class EndOfDayPdfComponent implements OnInit {

  //datepicker
  model: NgbDateStruct | undefined;

  //List contains a list of daily closings
  overviewEndList: EndOfTheDayOverview[] = [];

  //The date selected by the user
  selectDate: String = '';

  //Today's date
  todayDate: any;

 //Today's date as a string
  todayDateString: String | undefined;

  //For the pfd creation
  @ViewChild('htmlData') htmlData: ElementRef | undefined;

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
    this.getListWithEndOfTheEntry();
  }

//Converts a date after it is triggered
  OverviewEnd() {
    this.selectDate = this.convertDate.convertDate(this.OverviewEndForm.value.datepickerEnOv.year, this.OverviewEndForm.value.datepickerEnOv.month, this.OverviewEndForm.value.datepickerEnOv.day);
  }

//Loads a list with all end of the day entries
  getListWithEndOfTheEntry() {

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

  //create pdf
  public openPDF():void {

    //Loads all data in the tag htmlConvert
    let DATA = document.getElementById('htmlConvert');

    //If the variable DATA contains data, the function is excluded.
    if (DATA) {
      html2canvas(DATA).then(canvas => {

        let fileWidth = 148;
        let fileHeight = canvas.height * fileWidth / canvas.width;

        const FILEURI = canvas.toDataURL('image/png')
        let PDF = new jsPDF('p', 'mm', 'a4');
        let position = 40;
        PDF.addImage(FILEURI, 'PNG', 10, position, fileWidth, fileHeight)

        PDF.save('sbs'+this.todayDateString);
      });
    }
  }
}
