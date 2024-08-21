import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { TaskModel } from 'src/app/models/task/task.model';
import { TaskDTO } from 'src/app/models/task/taskDto.model';
import { TaskService } from 'src/app/services/task/task.service';
import { UserService } from 'src/app/services/user/user.service';
import { JwtParseService } from 'src/app/services/util/jwtParse.service';
import { environment } from 'src/environments/environment';
import { ModalConfirmationComponent } from '../../util/modal-confirmation/modal-confirmation.component';
import { TaskCreateComponent } from '../create/task-create.component';
import { TaskUpdateComponent } from '../update/task-update.component';

@Component({
  selector: 'app-task',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})

export class TaskListComponent implements OnInit {

  @ViewChild(TaskCreateComponent) taskCreate : TaskCreateComponent;
  @ViewChild(TaskUpdateComponent) taskupdate : TaskUpdateComponent;
  @ViewChild(ModalConfirmationComponent) modalConfirmation: ModalConfirmationComponent;

  public jwtData : object;
  public tasks: TaskModel[] = [];
  public temporalTask : TaskModel;


  public itemsToSkip = 0;

  public configPagination : any = {
    itemsPerPage: environment.PAGINATION.TASK.ITEMS_PER_PAGE,
    currentPage: 1,
    totalItems: 0,
    id: "tasks"
  };

  constructor(private taskService: TaskService
    , private jwtParseService: JwtParseService
    , private toastr: ToastrService) { }

  ngOnInit(): void {
    this.jwtData = this.jwtParseService.parseJWT();
    this.getTasksByUserId(this.itemsToSkip);
  }

  public pageChanged(page) : void {
    this.configPagination.currentPage = page;
    this.itemsToSkip = environment.PAGINATION.TASK.ITEMS_PER_PAGE * (page-1);
    this.getTasksByUserId(this.itemsToSkip);
  }

  public getTasksByUserId(itemsToSkip: number) : void {
    let promise = this.taskService.getTasksByUserId(this.jwtData['id'], environment.PAGINATION.TASK.ITEMS_PER_PAGE, itemsToSkip);

    promise.then(data => {
      this.tasks = data.content;
      this.configPagination.totalItems = data.totalItems;
    });

    promise.catch(error => {
      this.toastr.error('Something went wrong, please try again later');
    })
  }

  public getTasksByFilterAndUserId(taskDto: TaskDTO, itemsToSkip: number) : void {
    let promise = this.taskService.getTasksByFilterAndUserId(taskDto, environment.PAGINATION.TASK.ITEMS_PER_PAGE, itemsToSkip);

    promise.then(data => {
      this.tasks = data.content;
      this.configPagination.totalItems = data.totalItems;
    });

    promise.catch(error => {
      this.toastr.error('Something went wrong, please try again later');
    })
  }

  public getTaskByFilter(taskDto: TaskDTO): void {
    if(taskDto == null || taskDto == undefined) {
      this.getTasksByUserId(this.itemsToSkip);
    }
    else {
      this.getTasksByFilterAndUserId(taskDto, this.itemsToSkip);
    }
  }

  public openAddNewTaskModal(): void {
    this.taskCreate.activate();
  }

  public newTaskAdded(): void {
    this.getTasksByUserId(this.itemsToSkip);
  }

  public openUpdateTaskModal(taskToUpdate: TaskModel): void {
    this.taskupdate.activate(taskToUpdate);
  }

  public taskUpdated(): void {
    this.getTasksByUserId(this.itemsToSkip);
  }

  public showRemoveModalConfirmation(task: TaskModel) : void {
    this.temporalTask = task;
    let title = "Remove task";
    let message = "Are you sure you want to delete the task "+ this.temporalTask.title + "?";
    this.modalConfirmation.activate(title, message);
  }

  public removeModalConfirmation(event: boolean) : void {
    if (event) {
      this.removeTask(this.temporalTask.id);
    }
  }

  public removeTask(taskId: number) : void {
    this.taskService.deleteTask(taskId).subscribe(data => {
      if(data) {
        this.getTasksByUserId(this.itemsToSkip);
        this.toastr.success("The task has been removed");
        this.temporalTask = null;
      }
    });
  }

}
