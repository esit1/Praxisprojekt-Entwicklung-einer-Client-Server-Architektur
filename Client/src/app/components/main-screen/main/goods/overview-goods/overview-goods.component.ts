import {ServerCommunicationServiceService} from "../../../../../service/server-communication.service";
import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {Goods} from "../../../../../interface/goods";


@Component({
  selector: 'app-overview-goods',
  templateUrl: './overview-goods.component.html',
  styleUrls: ['./overview-goods.component.css']
})
/**
 * Class provides an overview of all goods.
 */
export class OverviewGoodsComponent implements OnInit {

  //empty list goods
  goodsList: Goods[] = [];

  //Heading of the table
  header: string[] = ['Warenname', 'Einheit', 'Preis', 'Bemerkung', 'im Verkauf'];

  //Saves whether a result was found
  searchResult: boolean = true;

  //List of search results
  searchResultList: Goods[] = [];


  constructor(private client: HttpClient,
              private communication: ServerCommunicationServiceService
  ) {
  }

  ngOnInit(): void {
    this.loadGoods();
    this.searchResultList = this.goodsList;
  }

  //Filter the list of goods according to the search results and save them in the list
  search(event: Event) {

    //Old search results are deleted
    this.searchResultList = [];

    //Saves the keystrokes
    const filterValue = (event.target as HTMLInputElement).value;

    //Searches the list for the word
    for (let key in this.goodsList) {
      if (this.goodsList.hasOwnProperty(key)) {
        if (
          this.goodsList[key].goodsName.includes(filterValue) ||
          this.goodsList[key].unitName.includes(filterValue) ||
          this.goodsList[key].goodsPrice.toString().includes(filterValue) ||
          this.goodsList[key].goodsNote.includes(filterValue)
        ) {
          //Saves all found experiences in the list
          this.searchResultList.push((this.goodsList[key]));
        }
      }
      //If not found, the variable is set to false.
      this.searchResult = this.searchResultList.length > 0;
    }
  }

  // Loads a list of goods from the server
  private loadGoods() {

    //set url
    let url = 'goods/' + localStorage.getItem('key_selfServiceStand_name');

    //communication Server
    this.communication
      .serverCommunication(url, '', 'get', true)
      .subscribe((response: any) => {

          //Saves the goods in a list
          for (var entry of response) {
            this.goodsList.push({
              goodsId: entry.goodsId,
              goodsName: entry.goodsNameNr.goodsName,
              unitName: entry.goodsUnitNr.unitName,
              goodsPrice: entry.goodsPrice,
              goodsNote: entry.goodsNote,
              goodsActive: entry.goodsActive
            });
          }
        }, error => {
          console.log(error);
        }
      );
  }
}
