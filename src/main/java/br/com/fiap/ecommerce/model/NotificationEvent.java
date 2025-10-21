package br.com.fiap.ecommerce.model;

public class NotificationEvent {

    private String message;

    public NotificationEvent() {}

    public NotificationEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
