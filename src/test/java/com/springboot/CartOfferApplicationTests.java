package com.springboot;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.controller.OfferRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartOfferApplicationTests {

    @Test
    public void checkFlatXForOneSegment() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p1");
        OfferRequest offerRequest = new OfferRequest(1, "FLATX", 10, segments);

        boolean result = addOffer(offerRequest);

        // Assert that the offer was successfully added
        Assert.assertTrue(result); // Expecting true, indicating that the POST request was successful
    }

    public boolean addOffer(OfferRequest offerRequest) throws Exception {
        String urlString = "http://localhost:9001/api/v1/offer"; // Ensure the API URL is correct
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true); // Enable output stream to send data in the request body
        con.setRequestProperty("Content-Type", "application/json"); // Set content type to JSON

        // Serialize the OfferRequest to JSON
        ObjectMapper mapper = new ObjectMapper();
        String POST_PARAMS = mapper.writeValueAsString(offerRequest);

        // Send the request
        try (OutputStream os = con.getOutputStream()) {
            os.write(POST_PARAMS.getBytes()); // Write the offerRequest data to the output stream
            os.flush();
        }

        // Get the response code from the server
        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // If the response code is 200 (OK)
            // Read the response from the input stream
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                System.out.println("Response: " + response.toString());
            }

            return true; // Return true if the request was successful
        } else {
            System.out.println("POST request did not work.");
            return false; // Return false if the request failed
        }
    }
}
