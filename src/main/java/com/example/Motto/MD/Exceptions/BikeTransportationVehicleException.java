package com.example.Motto.MD.Exceptions;

public class BikeTransportationVehicleException extends RuntimeException {
    String path;
    public BikeTransportationVehicleException(String message, String path) {
        super(message);
        this.path = path;
    }

    public BikeTransportationVehicleException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getPath() {
        return path;
    }
}
