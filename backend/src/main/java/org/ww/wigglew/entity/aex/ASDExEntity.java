package org.ww.wigglew.entity.aex;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "asd_ex")
public class ASDExEntity {
    @Id
    private String id;
    private String username;
    private String testType;
    private String asdStatus;
    private String confidence;
    private LocalDateTime testDate;
    private String requestID;
}
