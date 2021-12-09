export class TaskModel {

    id: number;
    title: string;
    description: string;
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

    public getUserId(): number {
        return this.userId;
    }
}