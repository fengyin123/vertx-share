package io.vertx.blog.first;

import com.jayway.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * These tests checks our REST API.
 */
public class MyRestIT {

  @BeforeClass
  public static void configureRestAssured() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = Integer.getInteger("http.port", 8082);
  }

  @AfterClass
  public static void unconfigureRestAssured() {
    RestAssured.reset();
  }

  @Test
  public void checkThatWeCanRetrieveIndividualProduct() {
    // Get the list of bottles, ensure it's a success and extract the first id.
    final String id = get("/api/contacts").then()
        .assertThat()
        .statusCode(200)
        .extract()
        .jsonPath().getString("find { it.name=='Zhang San' }.id");

    // Now get the individual resource and check the content
    get("/api/contacts/" + id).then()
        .assertThat()
        .statusCode(200)
        .body("name", equalTo("Zhang San"))
        .body("number", equalTo("177 7777 7777"))
        .body("id", equalTo(id));
  }

  @Test
  public void checkWeCanAddAndDeleteAProduct() {
    // Create a new bottle and retrieve the result (as a Whisky instance).
    Contacts contacts = given()
        .body("{\"name\":\"Zhang San\", \"number\":\"177 7777 7777\"}").request().post("/api/contacts").thenReturn().as(Contacts.class);
    assertThat(contacts.getName()).isEqualToIgnoringCase("Zhang San");
    assertThat(contacts.getNumber()).isEqualToIgnoringCase("177 7777 7777");
    assertThat(contacts.getId()).isNotEmpty();



    // Check that it has created an individual resource, and check the content.
    get("/api/contacts/" + contacts.getId()).then()
        .assertThat()
        .statusCode(200)
        .body("name", equalTo("Zhang San"))
        .body("number", equalTo("177 7777 7777"))
        .body("id", equalTo(contacts.getId()));



    // Delete the bottle
    delete("/api/contacts/" + contacts.getId()).then().assertThat().statusCode(204);

    // Check that the resource is not available anymore
    get("/api/contacts/" + contacts.getId()).then()
        .assertThat()
        .statusCode(404);

  }
}
