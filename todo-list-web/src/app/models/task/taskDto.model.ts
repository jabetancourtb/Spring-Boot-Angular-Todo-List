export class TaskDTO {
    
    id: number;
    title: string;
    description: string;
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

    public getUserId(): number {
        return this.userId;
    }
}