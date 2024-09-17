package com.example.Motto.MD.Dto;

import com.example.Motto.MD.Entity.VehicleRental;
import com.example.Motto.MD.Entity.TransportationVehicle;
import com.example.Motto.MD.Entity.Renter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TransportationVehicleRentalDto(
        @NotBlank(message = "Bike Plate Number is required.")
        @NotNull(message = "Bike Plate Number must not be Null.")
        @Size(min = 8, max = 8, message = "Bike Plate Number must be between {min} and {max} characters.")
        String bikePlateNumber,
        @JsonProperty("renterCnhNumber")
        @NotBlank(message = "Renter CNH Number is required.")
        @NotNull(message = "Renter CNH Number must not be Null.")
        @Size(min = 9, max = 9, message = "Renter CNH Number must be between {min} and {max} characters.")
        String renterCnhNumber
) {
        public VehicleRental toEntity(Renter renter, TransportationVehicle transportationVehicle){
                return new VehicleRental(transportationVehicle, renter);
        }
}
