import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DepartmentListComponent} from "./components";
import {DepartmentFormComponent} from "./components/department-form/department-form.component";
import {DepartmentsComponent} from "./departments.component";

const routes: Routes = [
  {
    path: '',
    component: DepartmentsComponent,
    children: [
      {
        path: 'add',
        component: DepartmentFormComponent
      },
      {
        path: 'edit/:departmentID',
        component: DepartmentFormComponent
      },
      {
        path: '',
        component: DepartmentListComponent,
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DepartmentsRoutingModule { }
