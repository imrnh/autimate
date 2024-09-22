package com.thjavafest.wigglewonders.Wigglewonders.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "child")
public class ChildEntity {
    @Id
    private Long id;
    private String name;
    private int parentId;  // Foreign key referencing the 'users' table
    private Timestamp dob; // Date of birth
    private int gender;
}
