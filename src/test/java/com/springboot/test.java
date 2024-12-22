package com.springboot;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class test {

    // Original Test Case (Apply offer with a valid cart value)
    @Test
    public void OfferWith_Valid_CartValue() {
        RestAssured.baseURI = "http://localhost:9001";

        String requestBody = "{ \"cart_value\": 180, \"user_id\": 1, \"restaurant_id\": 1 }";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/api/v1/cart/apply_offer");

        // Extract cart value from response (for assertion)
        int cartValue = response.jsonPath().getInt("cart_value");

        // Assertion: cart_value should be 144 (example logic you mentioned)
        assertEquals(cartValue, 144, "Test failed: cart_value is not 144");
    }

    // Test Case for FLATX Offer with Zero Cart Value (TC15)
    @Test
    public void FLATX_Of_on_Zero_Value() {
        RestAssured.baseURI = "http://localhost:9001";

        String requestBody = "{ \"cart_value\": 0, \"user_id\": 1, \"restaurant_id\": 1 }";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/api/v1/cart/apply_offer");

        // Assert that the response contains an error for zero cart value
        assertEquals(response.statusCode(), 400, "Expected 400 Bad Request for zero cart value.");
        assertTrue(response.getBody().asString().contains("cart value can't be zero"), "Error message mismatch.");
    }

    // Test Case for PERCENTX Offer with Zero Cart Value (TC16)
    @Test
    public void PERCENTX_Off_With_ZeroValue() {
        RestAssured.baseURI = "http://localhost:9001";

        String requestBody = "{ \"cart_value\": 0, \"user_id\": 1, \"restaurant_id\": 1 }";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/api/v1/cart/apply_offer");

        // Assert that the response contains an error for zero cart value
        assertEquals(response.statusCode(), 400, "Expected 400 Bad Request for zero cart value.");
        assertTrue(response.getBody().asString().contains("cart value can't be zero"), "Error message mismatch.");
    }

    // Test Case for Invalid User ID (TC17)
    @Test
    public void Invalid_UserId() {
        RestAssured.baseURI = "http://localhost:9001";

        String requestBody = "{ \"cart_value\": 180, \"user_id\": 999, \"restaurant_id\": 1 }";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/api/v1/cart/apply_offer");

        // Assert that the response contains an error for invalid user ID
        assertEquals(response.statusCode(), 404, "Expected 404 Not Found for invalid user ID.");
        assertTrue(response.getBody().asString().contains("User not found"), "Error message mismatch.");
    }

    // Test Case for Cart Value Less Than Offer Applied (TC22)
    @Test
    public void Cart_LessThan_Offer() {
        RestAssured.baseURI = "http://localhost:9001";

        // Request with cart value 5 and FLATX offer of 10
        String requestBody = "{ \"cart_value\": 5, \"user_id\": 1, \"restaurant_id\": 1 }";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/api/v1/cart/apply_offer");

        // Assert that the response contains an error for cart value being less than offer
        assertEquals(response.statusCode(), 400, "Expected 400 Bad Request for cart value less than offer.");
        assertTrue(response.getBody().asString().contains("Cart value is less than the applied offer"), "Error message mismatch.");
    }
}
