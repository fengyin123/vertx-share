package io.vertx.blog.first;

import io.vertx.core.json.JsonObject;

public class Contacts {

  private String id;

  private String name;

  private String number;

  public Contacts(String name, String number) {
    this.name = name;
    this.number = number;
    this.id = "";
  }

  public Contacts(JsonObject json) {
    this.name = json.getString("name");
    this.number = json.getString("number");
    this.id = json.getString("_id");
  }

  public Contacts() {
    this.id = "";
  }

  public Contacts(String id, String name, String number) {
    this.id = id;
    this.name = name;
    this.number = number;
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject()
        .put("name", name)
        .put("number", number);
    if (id != null && !id.isEmpty()) {
      json.put("_id", id);
    }
    return json;
  }

  public String getName() {
    return name;
  }

  public String getNumber() {
    return number;
  }

  public String getId() {
    return id;
  }

  public Contacts setName(String name) {
    this.name = name;
    return this;
  }

  public Contacts setNumber(String number) {
    this.number = number;
    return this;
  }

  public Contacts setId(String id) {
    this.id = id;
    return this;
  }
}