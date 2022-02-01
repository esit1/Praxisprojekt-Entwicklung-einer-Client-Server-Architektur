import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
/**
 * Convert a date to  string.
 */
export class ConvertDateService {

  constructor() { }


  /**
   * convert a date to 2021-10-02
   * @param year The year.
   * @param month The month.
   * @param day The day.
   */
  convertDate(year: any, month: any, day: number) {
    let monthString: String;
    let dayString: String;

    //Checks whether the day is less than 10.
    if (day < 10){
      dayString = '0'+day;
    }else{
      dayString = String(day);
    }

    //Checks whether the month is less than 10.
    if (month < 10){
      monthString = '0'+month;
    }else{
      monthString = String(month);
    }
    //Returns a string in the format 2021-10-02
    return  year+'-'+monthString+'-'+dayString;
  }
}


