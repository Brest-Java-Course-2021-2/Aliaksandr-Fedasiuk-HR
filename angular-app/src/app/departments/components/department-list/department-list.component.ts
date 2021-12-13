import {Component, OnInit} from '@angular/core';
import {DepartmentService} from '../../../services/department.service';
import {DepartmentDto} from '../../../model/department-dto';
import {Router} from '@angular/router';

@Component({
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.scss']
})
export class DepartmentListComponent implements OnInit {

  dtos;

  constructor(
    private departmentService: DepartmentService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.dtos = this.departmentService.getDepartmentDTOs();
  }

  onEdit(departmentDto: DepartmentDto): void {
    const link = ['/departments/edit', departmentDto.departmentId];
    this.router.navigate(link);
  }

  onAdd(): void {
    const link = ['/departments/add'];
    this.router.navigate(link);
  }
}

