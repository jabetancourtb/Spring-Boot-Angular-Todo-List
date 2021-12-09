import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { TaskModel } from 'src/app/models/task/task.model';
import { TaskDTO } from 'src/app/models/task/taskDto.model';
import { TaskService } from 'src/app/services/task/task.service';

@Component({
  selector: 'app-task-update',
  templateUrl: './task-update.component.html',
  styleUrls: ['./task-update.component.scss']
})

export class TaskUpdateComponent {

  @ViewChild("updateTaskModal") updateTaskModalDOMContent: ElementRef;
  @Output() newItemEvent = new EventEmitter();

  public taskFromList : TaskModel;

  taskForm = new FormGroup({
    title : new FormControl(null, Validators.required),
    description : new FormControl(null, Validators.required)
  });

  constructor(private modalService: NgbModal
    , private taskService: TaskService
    , private toastr: ToastrService) { }


  public activate(taskFromList: TaskModel) : void {
    this.taskFromList = taskFromList
    this.taskForm.controls['title'].setValue(this.taskFromList.title);
    this.taskForm.controls['description'].setValue(this.taskFromList.description);
    this.openModal(this.updateTaskModalDOMContent);
  }

  public openModal(updateTaskModal: ElementRef) : void {
      this.modalService.open(updateTaskModal)
      .result.then((result) => {
      }, (reason) => {
    });
  }

  public updateTask() : void {
    const taskUpdated = new TaskDTO(this.taskFromList.id, this.taskForm.get('title').value, this.taskForm.get('description').value, this.taskFromList.userId)
    this.taskService.updateTask(taskUpdated).subscribe(data => {
      if(data) {
        this.toastr.success('The task has been updated');
        this.newItemEvent.emit();
        this.modalService.dismissAll();
      }
    });
  }

}
