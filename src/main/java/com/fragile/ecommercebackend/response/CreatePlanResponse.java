package com.fragile.ecommercebackend.response;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Data
@Builder
public class CreatePlanResponse {

    private Boolean status;
    private String message;
    private Data data;

    @lombok.Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        @JsonProperty("name")
        private String name;

        @JsonProperty("amount")
        private String amount;

        @JsonProperty("interval")
        private String interval;

        @JsonProperty("integration")
        private String integration;

        @JsonProperty("plan_code")
        private String planCode;

        @JsonProperty("send_invoices")
        private String sendInvoices;

        @JsonProperty("send_sms")
        private String sendSms;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("id")
        private String id;

        @JsonProperty("createdAt")
        private String createdAt;

        @JsonProperty("updatedAt")
        private String updatedAt;
    }
}

