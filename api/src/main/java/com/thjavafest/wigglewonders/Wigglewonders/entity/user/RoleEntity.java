package com.thjavafest.wigglewonders.Wigglewonders.entity.user;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class RoleEntity {
  @Id
  private String id;

  private ERoleEntity name;

  public RoleEntity() {

  }

  public RoleEntity(ERoleEntity name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ERoleEntity getName() {
    return name;
  }

  public void setName(ERoleEntity name) {
    this.name = name;
  }
}
