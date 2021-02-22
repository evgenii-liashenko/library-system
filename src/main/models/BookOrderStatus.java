package main.models;

public enum BookOrderStatus {
    RETURNED,
    ACTIVE,
    // OVERDUE     unnecessary because BookOrder contains the dates that can be used to determine this
}
