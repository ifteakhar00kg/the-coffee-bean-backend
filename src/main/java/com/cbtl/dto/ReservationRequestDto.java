package com.cbtl.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotNull
    private LocalDate reservationDate;

    @NotBlank
    private String reservationTime;

    @NotNull
    @Min(1)
    private Integer guests;

    private String specialRequest;
}
