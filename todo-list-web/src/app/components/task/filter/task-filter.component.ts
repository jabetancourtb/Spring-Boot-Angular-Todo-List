import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TaskDTO } from 'src/app/models/task/taskDto.model';
import { JwtParseService } from 'src/app/services/util/jwtParse.service';

@Component({
  selector: 'app-task-filter',
  templateUrl: './task-filter.component.html',
  styleUrls: ['./task-filter.component.scss']
})
export class TaskFilterComponent implements OnInit {

  @Output() filter: EventEmitter<any> = new EventEmitter();

  public jwtData : object;

  public activeFilter: boolean;

  filterForm = new FormGroup({
    id: new FormControl(null),
    title : new FormControl(null),
    description : new FormControl(null),
    userId: new FormControl(0)
  });

  constructor(private jwtParseService: JwtParseService) { }

  ngOnInit(): void {
    this.jwtData = this.jwtParseService.parseJWT(); 
  }

  public onSubmit(filterGroup: TaskDTO): void {
    filterGroup.userId = this.jwtData['id'];
    this.filter.emit(filterGroup); 
  }

  public reset(): void {
    this.filterForm.reset();
    this.filter.emit(null);
  }

}
