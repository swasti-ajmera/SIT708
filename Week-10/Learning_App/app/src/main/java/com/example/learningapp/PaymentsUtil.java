package com.example.learningapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentsUtil {

    public static JSONObject getGooglePayBaseConfiguration() throws JSONException {
        JSONObject baseRequest = new JSONObject();
        baseRequest.put("apiVersion", 2);
        baseRequest.put("apiVersionMinor", 0);
        return baseRequest;
    }

    public static JSONObject getGatewayTokenizationSpecification() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("gateway", "example");  // Replace with your actual gateway if needed
        params.put("gatewayMerchantId", "exampleMerchantId");

        JSONObject tokenizationSpec = new JSONObject();
        tokenizationSpec.put("type", "PAYMENT_GATEWAY");
        tokenizationSpec.put("parameters", params);

        return tokenizationSpec;
    }

    public static JSONObject getTransactionInfo(String amount) throws JSONException {
        JSONObject transactionInfo = new JSONObject();
        transactionInfo.put("totalPrice", amount);
        transactionInfo.put("totalPriceStatus", "FINAL");
        transactionInfo.put("currencyCode", "INR");
        return transactionInfo;
    }

    public static JSONObject getMerchantInfo() throws JSONException {
        JSONObject merchantInfo = new JSONObject();
        merchantInfo.put("merchantName", "Your App Name");
        return merchantInfo;
    }

    public static JSONObject createPaymentDataRequest(String amount) throws JSONException {
        JSONObject paymentDataRequest = getGooglePayBaseConfiguration();

        JSONObject cardPaymentMethod = new JSONObject();
        cardPaymentMethod.put("type", "CARD");

        JSONObject parameters = new JSONObject();
        parameters.put("allowedAuthMethods", new JSONArray().put("PAN_ONLY").put("CRYPTOGRAM_3DS"));
        parameters.put("allowedCardNetworks", new JSONArray().put("VISA").put("MASTERCARD"));

        cardPaymentMethod.put("parameters", parameters);
        cardPaymentMethod.put("tokenizationSpecification", getGatewayTokenizationSpecification());

        paymentDataRequest.put("allowedPaymentMethods", new JSONArray().put(cardPaymentMethod));
        paymentDataRequest.put("transactionInfo", getTransactionInfo(amount));
        paymentDataRequest.put("merchantInfo", getMerchantInfo());

        return paymentDataRequest;
    }
}
