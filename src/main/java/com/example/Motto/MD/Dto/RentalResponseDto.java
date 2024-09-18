package com.example.Motto.MD.Dto;


import com.example.Motto.MD.Entity.Rental;

import java.util.Map;

public record RentalResponseDto(long id, String createdAt, String startDate, String dueDate,VehicleResponseDto vehicle, RenterResponseDto renter) {

    public static RentalResponseDto fromEntity(Rental rental) {
        return new RentalResponseDto(
                rental.getId(),
                rental.getCreatedAt().toString(),
                rental.getStartDate().toString(),
                rental.getDueDate().toString(),
                VehicleResponseDto.fromEntity(rental.getVehicle()),
                RenterResponseDto.fromEntity(rental.getRenter())
        );
    }
}
