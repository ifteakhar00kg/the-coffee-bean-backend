package com.cbtl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {
    private Long id;
    private String name;
    private String phone;
    private LocalDate reservationDate;
    private String reservationTime;
    private Integer guests;
    private String specialRequest;
    private String status;
    private LocalDateTime createdAt;
}
