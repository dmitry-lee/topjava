package ru.javawebinar.topjava.util.exception;

public class ErrorInfo {
    private String url;
    private ErrorType type;
    private String[] details;

    public ErrorInfo() {
    }

    public ErrorInfo(CharSequence url, ErrorType type, String[] details) {
        this.url = url.toString();
        this.type = type;
        this.details = details;
    }

    public ErrorType getType() {
        return type;
    }
}