package org.example.repository;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Task {
    String name;

    String description;

    LocalDate deadline;

    Status taskStatus;
}
