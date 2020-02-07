import { Component, OnInit } from '@angular/core';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { ChartDataSets, ChartType } from 'chart.js';
import { Label } from 'ng2-charts';
import { ReportService } from '../services/report-service/report.service';
import { stringify } from 'querystring';
import { Place } from '../models/place-model/place.model';

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
      (data: Place[]) => {
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
                  (data: Map<string, number>) => {
                    this.putData(data);
                  }
                );
          } else {
            this.reportService.getAttendanceTime(this.id, this.fromDate, this.toDate)
              .subscribe(
                (data: Map<string, number>) => {
                  this.putData(data);
                }
              );
          }
        }
      } else {
        if (this.about === 'Profit') {
          this.reportService.getProfitEvent(this.id)
            .subscribe(
              (data: Map<string, number>) => {
                this.putData(data);
              }
            );
        } else {
          this.reportService.getAttendanceEvent(this.id)
            .subscribe(
              (data: Map<string, number>) => {
                this.putData(data);
              }
            );
        }
      }
    }
  }

  initChart() {
    this.lineChartData = undefined;
    this.lineChartLabels = undefined;
  }

  putData(data: Map<string, number>) {
    this.lineChartLabels = [];
    const values = [];
    // tslint:disable-next-line: forin
    for (const key in data) {
      this.lineChartLabels.push(key);
      values.push(data[key]);
    }
    this.lineChartData = [{ data: values, label: this.about }];
  }

  picker1max() {
    if (this.toDate) {
      return new Date(this.toDate.getFullYear(), this.toDate.getMonth(), this.toDate.getDate() - 1);
    }
    return new Date(this.maxDate.getFullYear(), this.maxDate.getMonth(), this.maxDate.getDate() - 1);
  }
  picker2min() {
    if (this.fromDate) {
      return new Date(this.fromDate.getFullYear(), this.fromDate.getMonth(), this.fromDate.getDate() + 1);
    }
    return null;
  }
}
