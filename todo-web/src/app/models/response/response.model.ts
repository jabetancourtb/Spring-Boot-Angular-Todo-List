export class ResponseModel {

    content: Array<any>;
    totalItems: number;

    constructor() {}

    public getContent(): Array<any> {
        return this.content;
    }

    public getTotalItems(): number {
        return this.totalItems;
    }
}