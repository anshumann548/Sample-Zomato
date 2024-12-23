package com.springboot.controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AutowiredController {
    List<OfferRequest> allOffers = new ArrayList<>();

    @PostMapping(path = "/api/v1/offer")
    public ApiResponse postOperation(@RequestBody OfferRequest offerRequest) {
        System.out.println(offerRequest);
        allOffers.add(offerRequest);
        return new ApiResponse("success");
    }

    @PostMapping(path = "/api/v1/cart/apply_offer")
    public ApplyOfferResponse applyOffer(@RequestBody ApplyOfferRequest applyOfferRequest) throws Exception {
        System.out.println(applyOfferRequest);
        int cartVal = applyOfferRequest.getCart_value();
        SegmentResponse segmentResponse = getSegmentResponse(applyOfferRequest.getUser_id());
        Optional<OfferRequest> matchRequest = allOffers.stream()
                .filter(x -> x.getUserId() == applyOfferRequest.getRestaurant_id())  // Matching userId with restaurant_id
                .filter(x -> x.getSegments().contains(segmentResponse.getSegment()))  // Matching segments with the segmentResponse
                .findFirst();

        if (matchRequest.isPresent()) {
            System.out.println("got a match");
            OfferRequest gotOffer = matchRequest.get();

            if (gotOffer.getOfferType().equals("FLATX")) {
                cartVal = cartVal - gotOffer.getOfferValue();
            } else {
                cartVal = (int) (cartVal - cartVal * gotOffer.getOfferValue() * 0.01);
            }
        }
        return new ApplyOfferResponse(cartVal);
    }

    private SegmentResponse getSegmentResponse(int userid) {
        SegmentResponse segmentResponse = new SegmentResponse();
        try {
            String urlString = "http://localhost:1080/api/v1/user_segment?" + "user_id=" + userid;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");

            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            segmentResponse = mapper.readValue(responseStream, SegmentResponse.class);
            System.out.println("got segment response" + segmentResponse);

        } catch (Exception e) {
            System.out.println(e);
        }
        return segmentResponse;
    }
}
