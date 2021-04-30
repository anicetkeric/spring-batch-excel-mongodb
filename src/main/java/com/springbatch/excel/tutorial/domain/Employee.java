package com.springbatch.excel.tutorial.domain;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "employee")
public class Employee  {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    @NotNull
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String number;

    private String email;

    private String department;

    private double salary;
}