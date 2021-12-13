import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {EmployeesRoutingModule} from './employees-routing.module';
import {EmployeeListComponent} from './components/employee-list/employee-list.component';
import {EmployeesComponent} from './employees.component';


@NgModule({
  declarations: [EmployeeListComponent, EmployeesComponent],
  imports: [
    CommonModule,
    EmployeesRoutingModule
  ]
})
export class EmployeesModule { }
