import { Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-confirmation',
  templateUrl: './modal-confirmation.component.html',
  styleUrls: ['./modal-confirmation.component.scss']
})

export class ModalConfirmationComponent{

  @ViewChild("confirmationModal") confirmationModalDOMContent: ElementRef;
  @Output() newItemEvent = new EventEmitter();

  public title: string;
  public message: string;

  constructor(private modalService: NgbModal) {}

  public activate(title: string, message: string): void {
    this.title = title;
    this.message = message;
    this.openModal(this.confirmationModalDOMContent);
  }

  public openModal(confirmationModal: ElementRef): void {
    this.modalService.open(confirmationModal).result.then((result) => {
      }, (reason) => {
    });
  }

  public onClick(action: boolean) {
    this.newItemEvent.emit(action);
    this.modalService.dismissAll();
  }

}
