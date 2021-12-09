export class UserModel {

    id: number;
    email: string;
    password: string;
    fullName: string;

    constructor(id: number, email: string, password: string, fullName: string) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public getId(): number {
        return this.id;
    }

    public getEmail(): string {
        return this.email;
    }

    public getPassword(): string {
        return this.password;
    }

    public getFullName(): string {
        return this.fullName;
    }
}