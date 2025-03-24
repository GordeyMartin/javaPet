package org.example.repository;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Task {
    String name;

    String description;

    String deadline;

    Status taskStatus;
}
