export class UserDTO {
    
    email: string;
    password: string;
    fullName: string;

    constructor( email: string, password: string, fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
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