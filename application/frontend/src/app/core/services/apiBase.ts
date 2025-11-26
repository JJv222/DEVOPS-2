export abstract class apiBase {
    protected readonly apiPrefix: string = "/api/" + this.createApiPrefix();
        
    protected abstract createApiPrefix(): string;
}