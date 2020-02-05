import { NgModule } from '@angular/core';

import {
  MatButtonModule,
  MatMenuModule,
  MatIconModule,
  MatCardModule,
  MatSidenavModule,
  MatFormFieldModule,
  MatInputModule,
  MatTooltipModule,
  MatToolbarModule,
  MatSliderModule,
  MatGridListModule,
  MatExpansionModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatSelectModule,
  MatListModule,
  MatCheckboxModule,
  MatCheckbox,
  MatTabsModule,
  MatDialogModule,
  MatRadioModule,
  MatRippleModule,
  MatRadioGroup

} from '@angular/material';

@NgModule({
  imports: [
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    MatCardModule,
    MatSidenavModule,
    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule,
    MatToolbarModule,
    MatSliderModule,
    MatGridListModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatListModule,
    MatCheckboxModule,
    MatTabsModule,
    MatDialogModule,
    MatRippleModule
  ],
  exports: [
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    MatCardModule,
    MatSidenavModule,
    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule,
    MatToolbarModule,
    MatSliderModule,
    MatGridListModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatListModule,
    MatCheckboxModule,
    MatTabsModule,
    MatDialogModule,
    MatRadioModule
  ]
})
export class MaterialModule {}
