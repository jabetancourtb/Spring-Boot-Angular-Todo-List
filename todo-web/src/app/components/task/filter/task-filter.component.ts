import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TaskDTO } from 'src/app/models/task/taskDto.model';

@Component({
  selector: 'app-task-filter',
  templateUrl: './task-filter.component.html',
  styleUrls: ['./task-filter.component.scss']
})
export class TaskFilterComponent implements OnInit {

  @Output() filter: EventEmitter<any> = new EventEmitter();

  public activeFilter: boolean;

  filterForm = new FormGroup({
    id: new FormControl(null),
    title : new FormControl(null),
    description : new FormControl(null)
  });

  constructor() { }

  ngOnInit(): void {
  }

  public onSubmit(filterGroup: TaskDTO): void {
    this.filter.emit(filterGroup);
  }

  public reset(): void {
    console.log("Hola");
    this.filterForm.reset();
  }

}
