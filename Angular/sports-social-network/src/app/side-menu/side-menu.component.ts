import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.scss']
})
export class SideMenuComponent implements OnInit {

  @Input()
  menuOptions: string[];

  @Input()
  selectedMenuOption: string;

  @Output()
  selectedMenuChange = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  getSelectedMenuOption(): string {
    return this.selectedMenuOption;
  }

  setSelectMenuOption(selectedMenuOption: string): void {
    this.selectedMenuOption = selectedMenuOption;
  }

  selectedMenuItemChanged(selectedMenuOption: string): void {
    this.setSelectMenuOption(selectedMenuOption);
    this.selectedMenuChange.emit(this.selectedMenuOption);
  }
}
