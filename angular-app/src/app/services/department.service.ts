import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {DepartmentDto} from "../model/department-dto";
import {catchError, map, tap} from 'rxjs/operators';
import {Department} from "../model/department";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  private basePath = environment.basePath;

  constructor(protected httpClient: HttpClient) {
  }

  public getDepartmentDTOs(): Observable<Array<DepartmentDto>> {
    return this.httpClient.get<Array<DepartmentDto>>(`${this.basePath}/department-dtos`)
      .pipe(
        map(response => response || []),
        tap(response => console.log(JSON.stringify(response))),
        catchError(this.handleError)
      );
  }

  public findDepartmentById(id: number | string): Observable<Department> {
    return this.httpClient.get<Department>(`${this.basePath}/departments/${encodeURIComponent(String(id))}`);
  }

  public createDepartment(department: Department): Observable<number> {
    if (department === null || department === undefined) {
      throw new Error(
        'Required parameter department was null or undefined when calling createDepartment.'
      );
    }

    // let headers = new HttpHeaders();
    // headers.append('Accept', 'application/json');
    // headers.append('Content-Type', 'application/json');
    return this.httpClient.post<number>(`${this.basePath}/departments`, department);
  }

  public updateDepartment(department: Department): Observable<number> {
    if (department === null || department === undefined) {
      throw new Error(
        'Required parameter department was null or undefined when calling updateDepartment.'
      );
    }
    return this.httpClient.put<number>(`${this.basePath}/departments`, department);
  }

  public deleteDepartment(id: number): Observable<number> {
    if (id === null || id === undefined) {
      throw new Error(
        'Required parameter id was null or undefined when calling deleteDepartment.'
      );
    }
    return this.httpClient.delete<number>(`${this.basePath}/departments/${encodeURIComponent(String(id))}`);
  }

  private handleError(err: HttpErrorResponse): Observable<never> {
    let errorMessage: string;

    // A client-side or network error occurred.
    if (err.error instanceof Error) {
      errorMessage = `An error occurred: ${err.error.message}`;
    }
      // The backend returned an unsuccessful response code.
    // The response body may contain clues as to what went wrong,
    else {
      errorMessage = `Backend returned code ${err.status}, body was: ${err.error}`;
    }
    console.error(errorMessage);
    return Observable.throw(errorMessage);
  }
}

