export class UserPasswordRecoveryModel {

    id: number;
    verificationCode: string;
    userId: number;
    creationDate: Date;
    isValid: boolean;


    constructor(id: number, verificationCode: string, userId: number, creationDate: Date, isValid: boolean) {
        this.id = id;
        this.verificationCode = verificationCode;
        this.userId = userId;
        this.creationDate = creationDate;
        this.isValid = isValid;
    }

    public getId(): number {
        return this.id;
    }

    public getVerificationCode(): string {
        return this.verificationCode;
    }

    public getUserId(): number {
        return this.userId;
    }

    public getCreationDate(): Date {
        return this.creationDate;
    }

    public getIsValid(): boolean {
        return this.isValid;
    }

    
}