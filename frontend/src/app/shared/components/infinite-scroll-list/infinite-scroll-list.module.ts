import { ScrollingModule } from '@angular/cdk/scrolling';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatListModule } from '@angular/material/list';
import { InfiniteScrollListComponent } from './infinite-scroll-list.component';

@NgModule({
  imports: [CommonModule, ScrollingModule, MatListModule],
  declarations: [InfiniteScrollListComponent],
  exports: [InfiniteScrollListComponent],
})
export class InfiniteScrollListModule {}
