package com.springbatch.excel.tutorial.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "employee")
public record Employee(
        @Id
        String id,
        String firstName,
        String lastName,
        @Indexed(unique = true, direction = IndexDirection.DESCENDING)
        String number,
        String email,
        String department,
        double salary
) {
}
