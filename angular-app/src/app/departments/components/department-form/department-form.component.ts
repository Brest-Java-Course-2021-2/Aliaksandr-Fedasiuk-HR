import {Component, OnInit} from '@angular/core';
import {Department} from '../../../model/department';
import {DepartmentService} from '../../../services/department.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {switchMap} from 'rxjs/operators';

@Component({
  templateUrl: './department-form.component.html',
  styleUrls: ['./department-form.component.scss']
})
export class DepartmentFormComponent implements OnInit {
  department: Department;

  constructor(
    private departmentService: DepartmentService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.department = new Department();
    const observer = {
      next: (department: Department) => (this.department = {...department}),
      error: (err: any) => console.log(err)
    };
    this.route.paramMap
      .pipe(
        switchMap((params: ParamMap) => {
            const id = params.get('departmentID');
            console.log(id);
            if (id) {
              return this.departmentService.findDepartmentById(params.get('departmentID'));
            }
          }
        )
      )
      .subscribe(observer);
  }

  onSubmit(): void {
    // console.log('onSave ' + JSON.stringify(this.department));
    const department = {...this.department} as Department;

    if (department.departmentId) {
      this.departmentService.updateDepartment(department).subscribe();
    } else {
      this.departmentService.createDepartment(department).subscribe();
    }

    this.onGoBack();
  }

  onGoBack(): void {
    this.router.navigate(['/departments']);
  }

}
