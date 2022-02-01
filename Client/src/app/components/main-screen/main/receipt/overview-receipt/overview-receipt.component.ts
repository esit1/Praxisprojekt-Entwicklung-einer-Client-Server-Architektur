import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ServerCommunicationServiceService} from "../../../../../service/server-communication.service";
import {Receipt} from "../../../../../interface/receipt";


@Component({
  selector: 'app-overview-receipt',
  templateUrl: './overview-receipt.component.html',
  styleUrls: ['./overview-receipt.component.css']
})
/**
 * Overview of incoming and outgoing goods.
 */
export class OverviewReceiptComponent implements OnInit {

  //List with all end of the day entries
  receiptList: Receipt[] = [];

  //header of the table
  headerRe: string[] = ['Warenname', 'Datum', 'Anzahl', 'Einheit', 'Benutzer'];

  //saves whether there was a search result
  searchResult = true;

  //list with all search results
  searchResultList: Receipt[] = [];


  constructor(private client: HttpClient,
              private communication: ServerCommunicationServiceService
  ) {}

  //loads a list with all search results
  ngOnInit(): void {
    this.loadReceipt();
    this.searchResultList = this.receiptList;
  }

  //determines all search results that match the keyboard input
  search(event: Event) {
    //empty list
    this.searchResultList = [];

    //key event
    const filterValue = (event.target as HTMLInputElement).value;

    for (let key in this.receiptList) {
      if (this.receiptList.hasOwnProperty(key)) {
        if (
          this.receiptList[key].goodsName.includes(filterValue) ||
          this.receiptList[key].dateReceipt.includes(filterValue) ||
          this.receiptList[key].goodsUnit.toString().includes(filterValue) ||
          this.receiptList[key].userName.includes(filterValue)
        ) {
          //list with search results
          this.searchResultList.push((this.receiptList[key]));

        }
      }

      //Error message display in HTML
      this.searchResult = this.searchResultList.length > 0;
    }
  }


  //load the list with goods
  private loadReceipt() {

    //set url
    let url = 'receipt/all/' + localStorage.getItem('key_selfServiceStand_name');

    //communication Server
    this.communication
      .serverCommunication(url, '', 'get', true)
      .subscribe((response: any) => {

          //save entry in list
          for (var entry of response) {

            this.receiptList.push({
              receiptGoodId: entry.receiptGoodId,
              dateReceipt: entry.dateReceipt,
              goodsPieces: entry.goodsPieces,
              goodsName: entry.goodsName,
              goodsUnit: entry.goodsUnit,
              userName: entry.userName,
            });
          }
        }, error => {
          console.log(error);
        }
      );
  }
}
