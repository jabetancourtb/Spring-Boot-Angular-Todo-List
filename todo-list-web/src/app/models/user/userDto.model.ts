export class UserDTO {
    
    id: number;
    email: string;
    password: string;
    fullName: string;
    enabled: boolean;
    verificationCode: string;

    constructor( id: number, email: string, password: string, fullName: string, enabled: boolean, verificationCode: string) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.enabled = enabled;
        this.verificationCode = verificationCode;
    }

    public getId() : number {
        return this.id
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

    public isEnabled(): boolean {
        return this.enabled;
    }

    public getVerificationCode(): string {
        return this.verificationCode;
    }
}