import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {DepartmentsRoutingModule} from './departments-routing.module';
import {DepartmentListComponent} from './components/department-list/department-list.component';
import {DepartmentFormComponent} from './components/department-form/department-form.component';
import {DepartmentsComponent} from './departments.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [DepartmentListComponent, DepartmentFormComponent, DepartmentsComponent],
  imports: [
    CommonModule,
    FormsModule,
    DepartmentsRoutingModule
  ]
})
export class DepartmentsModule { }
