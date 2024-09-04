package com.example.Motto.MD.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateBikeTransportationVehicle(
        @JsonProperty("plateNumber")
        String plateNumber) {
}
