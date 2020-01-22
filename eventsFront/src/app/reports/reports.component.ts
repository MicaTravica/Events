import { Component, OnInit } from '@angular/core';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { ChartDataSets, ChartType } from 'chart.js';
import { Label } from 'ng2-charts';
import { ReportService } from '../services/report-service/report.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {

  places = [];
  type1 = ['Profit', 'Attendance'];
  type2 = ['Time', 'Event'];
  id: number;
  about: string;
  by: string;
  fromDate: Date;
  toDate: Date;
  maxDate = new Date();
  lineChartData: ChartDataSets[];
  lineChartLabels: Label[];

  constructor(
    private placeService: PlaceService,
    private reportService: ReportService
  ) { }

  ngOnInit() {
    this.placeService.getPlaces().subscribe(
      (data: any[]) => { // zameni sa place
        this.places = data;
      }
    );
  }

  search() {
    if (this.id && this.about && this.by) {
      this.initChart();
      if (this.by === 'Time') {
        if (this.fromDate && this.toDate) {
          if (this.about === 'Profit') {
              this.reportService.getProfitTime(this.id, this.fromDate, this.toDate)
                .subscribe(
                  res => {
                    console.log('time + profit');
                  }
                );
          } else {
            this.reportService.getAttendanceTime(this.id, this.fromDate, this.toDate)
              .subscribe(
                res => {
                  console.log('time + attendace');
                }
              );
          }
          this.lineChartData = [
            { data: [85, 72, 78, 75, 77, 75], label: this.about } // u data ce ici zarada ili posecenost
          ];
          this.lineChartLabels = ['January', 'February', 'March', 'April', 'May', 'June']; // ove ce ici vreme, u zavisnsit od
          // toga sta je uneo, sad izaberi da li ces mesece ili godine ili sta, ili dodaj polje da bita da li ce po mesecima ili godina
        }
      } else {
        if (this.about === 'Profit') {
            this.reportService.getProfitEvent(this.id)
              .subscribe(
                res => {
                  console.log('event + profit');
                }
              );
        } else {
          this.reportService.getAttendanceEvent(this.id)
            .subscribe(
              res => {
                console.log('event + attendace');
              }
            );
        }
        // ovde treba da se radi poziv ka bekendu, ondnosno servicu, subscribe
        // na osnovu about vidis da li je zarada ili posecenost
        this.lineChartData = [
          { data: [85, 72, 78, 75, 77, 75], label: this.about } // u data ce ici zarada ili posecenost
        ];
        this.lineChartLabels = ['January', 'February', 'March', 'April', 'May', 'June']; // ove ce ici naziv dogadjaja
      }
    }
  }

  initChart() {
    this.lineChartData = undefined;
    this.lineChartLabels = undefined;
  }
}
