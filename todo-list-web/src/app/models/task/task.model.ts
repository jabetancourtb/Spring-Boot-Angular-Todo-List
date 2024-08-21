export class TaskModel {

    id: number;
    title: string;
    description: string;
    date: Date;
    userId: number;

    constructor() {}

    public getId(): number {
        return this.id;
    }

    public getTitle(): string {
        return this.title;
    }

    public getDescription(): string {
        return this.description;
    }

    public getDate(): Date {
        return this.date;
    }

    public getUserId(): number {
        return this.userId;
    }
}