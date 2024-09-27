package org.ww.wigglew.entity.doctor;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "doctor")
public class DoctorEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private String phone;

    private String name;
    private String gender;
    private String officeHours;

    private DoctorAddress address;
}