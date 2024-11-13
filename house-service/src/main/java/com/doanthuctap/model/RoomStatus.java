package com.doanthuctap.model;

public enum RoomStatus {
    AVAILABLE("Còn trống"),
    RENTED("Đã cho thuê"),
    DELETED("Đã xóa"),
    MAINTENANCE("Bảo trì");

    private final String status;

    RoomStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}