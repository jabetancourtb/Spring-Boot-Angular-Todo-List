import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from "src/environments/environment";
import { HeadersService } from "../util/header.service";
import { TaskDTO } from "src/app/models/task/taskDto.model";
import { ResponseModel } from "src/app/models/response/response.model";


@Injectable({
    providedIn: 'root'
})

export class TaskService {

    constructor(private http : HttpClient, private headersService : HeadersService) { }

    getTasksByUserId(userId: number, limit: number, itemsToSkip: number) : Promise<ResponseModel> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/task/getByUserId/' + userId + "/" + limit + "/" + itemsToSkip;
        return new Promise((resolve, reject) => {
            this.http.get<ResponseModel>(apiUrl, this.headersService.authorizationHeader).subscribe(data => {
                    resolve(data);
                }, 
                (error) => {
                    reject(error);
                }
            );
        });
    }

    
    getTasksByFilterAndUserId(taskDto: TaskDTO, limit: number, itemsToSkip: number) : Promise<ResponseModel> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/task/getByFilterAndUserId/' + limit + "/" + itemsToSkip;
        return new Promise((resolve, reject) => {
            this.http.post<ResponseModel>(apiUrl, taskDto, this.headersService.authorizationHeader).subscribe(data => {
                    resolve(data);
                }, 
                (error) => {
                    reject(error);
                }
            );
        });
    }

    createTask(taskDto: TaskDTO) : Observable<boolean> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/task';
        return this.http.post<boolean>(apiUrl, taskDto, this.headersService.authorizationHeader);
    }

    updateTask(taskDto: TaskDTO) : Observable<boolean> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/task';
        return this.http.put<boolean>(apiUrl, taskDto, this.headersService.authorizationHeader);
    }

    deleteTask(id: number) : Observable<boolean> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/task/' + id;
        return this.http.delete<boolean>(apiUrl, this.headersService.authorizationHeader);
    }
}