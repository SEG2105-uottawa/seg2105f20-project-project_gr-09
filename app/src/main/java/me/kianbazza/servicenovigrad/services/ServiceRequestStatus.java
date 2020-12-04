package me.kianbazza.servicenovigrad.services;

public enum ServiceRequestStatus {
    WAITING_FOR_REVIEW,
    APPROVED,
    REJECTED;

    public static ServiceRequestStatus fromString(String s) {
        String requestStatusStr = s.trim();

        if (requestStatusStr.equalsIgnoreCase(WAITING_FOR_REVIEW.toString())) {
            return WAITING_FOR_REVIEW;
        } else if (requestStatusStr.equalsIgnoreCase(APPROVED.toString())) {
            return APPROVED;
        } else if (requestStatusStr.equalsIgnoreCase(REJECTED.toString())) {
            return REJECTED;
        } else {
            return null;
        }
    }
}
