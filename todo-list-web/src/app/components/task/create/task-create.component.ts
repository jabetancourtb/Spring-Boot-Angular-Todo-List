import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { TaskDTO } from 'src/app/models/task/taskDto.model';
import { TaskService } from 'src/app/services/task/task.service';

@Component({
  selector: 'app-task-create',
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.scss']
})

export class TaskCreateComponent {

  @ViewChild("createTaskModal") createTaskModalDOMContent: ElementRef;
  @Input("userId") userId: number;
  @Output() newItemEvent = new EventEmitter();

  taskForm = new FormGroup({
    title : new FormControl(null, Validators.required),
    description : new FormControl(null, Validators.required)
  });

  constructor(private modalService: NgbModal
    , private taskService: TaskService
    , private toastr: ToastrService) { }

  public activate() : void {
    this.taskForm.controls['title'].setValue('');
    this.taskForm.controls['description'].setValue('');
    this.openModal(this.createTaskModalDOMContent);
  }

  public openModal(createTaskModal: ElementRef) : void {
      this.modalService.open(createTaskModal)
      .result.then((result) => {
      }, (reason) => {
    });
  }

  public createNewTask() : void {
    const taskDto = new TaskDTO(-1, this.taskForm.get('title').value, this.taskForm.get('description').value, this.userId)
    this.taskService.createTask(taskDto).subscribe(data => {
      if(data) {
        this.toastr.success('New task added');
        this.newItemEvent.emit();
        this.modalService.dismissAll();
      } 
    });
  }

}
