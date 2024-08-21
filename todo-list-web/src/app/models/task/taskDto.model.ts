export class TaskDTO {
    
    id: number;
    title: string;
    description: string;
    date: Date;
    userId: number;

    constructor(id: number, title: string, description: string, userId: number) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

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