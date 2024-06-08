export interface NotificationMessage {
    title?: string;
    description: string;
    status: "error" | "info" | "message" | "warning";
}