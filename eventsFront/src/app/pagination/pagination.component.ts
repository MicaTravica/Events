import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent implements OnInit, OnChanges {

  @Input() totalItems: number;
  @Input() pageSize: number;
  @Output() pageSelected: EventEmitter<number>;
  pages: number[];
  activePage: number;

  constructor() {
    this.pageSelected = new EventEmitter();
    this.activePage = 1;
  }

  ngOnInit() {
    this.pages = [];
    for (let i = 1; i <= this.getNoPages(this.totalItems, this.pageSize); i++) {
      this.pages.push(i);
    }
  }

  ngOnChanges(changes: any) {
    this.activePage = 1;
    this.pages = [];
    for (let i = 1; i <= this.getNoPages(this.totalItems, this.pageSize); i++) {
      this.pages.push(i);
    }
  }

  selected(newPage: number) {
    if (newPage >= 1 && newPage <= this.getNoPages(this.totalItems, this.pageSize)) {
      this.activePage = newPage;
      this.pageSelected.emit(this.activePage);
    }
  }

  public getNoPages(totalItems: number, pageSize: number): number {
    return Math.ceil(totalItems / pageSize);
  }
}
