package com.example.learningapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UpgradeActivity extends AppCompatActivity {

    private PaymentSheet paymentSheet;
    private String publishableKey = "pk_test_51RT0w6PqLkT24mGSd67vGI9nitRSUnIDBcBH8en9ZRiLZub36TtmxH5QwKCOAjd6gchFLKzGz60liU5henmq4lZi00tPCEoBZt"; // Your Stripe publishable key
    private OkHttpClient httpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        PaymentConfiguration.init(getApplicationContext(), publishableKey);
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        findViewById(R.id.googlePayStarterButton).setOnClickListener(v -> {
            startPayment(500); // $5.00 AUD in cents
        });

        findViewById(R.id.googlePayIntermediateButton).setOnClickListener(v -> {
            startPayment(1000); // $10.00 AUD in cents
        });

        findViewById(R.id.googlePayAdvancedButton).setOnClickListener(v -> {
            startPayment(2000); // $20.00 AUD in cents
        });
    }

    private void startPayment(int amountInCents) {
        try {
            JSONObject json = new JSONObject();
            json.put("amount", amountInCents);

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url("http://10.0.2.2:5000/create-payment-intent") // Replace with your backend URL
                    .post(body)
                    .build();

            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> Toast.makeText(UpgradeActivity.this, "Failed to contact server", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        runOnUiThread(() -> Toast.makeText(UpgradeActivity.this, "Server error: " + response.message(), Toast.LENGTH_SHORT).show());
                        return;
                    }
                    String responseBody = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        String clientSecret = jsonResponse.getString("clientSecret");
                        runOnUiThread(() -> presentPaymentSheet(clientSecret));
                    } catch (JSONException e) {
                        runOnUiThread(() -> Toast.makeText(UpgradeActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show());
                    }
                }
            });

        } catch (JSONException e) {
            Toast.makeText(this, "Error creating payment request", Toast.LENGTH_SHORT).show();
        }
    }

    private void presentPaymentSheet(String paymentIntentClientSecret) {
        PaymentSheet.Configuration config = new PaymentSheet.Configuration.Builder("Learning App")
                .allowsPaymentMethodsRequiringShippingAddress(true)
                .build();

        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, config);
    }

    private void onPaymentSheetResult(PaymentSheetResult result) {
        if (result instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show();
        } else if (result instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(this, "Payment canceled.", Toast.LENGTH_SHORT).show();
        } else if (result instanceof PaymentSheetResult.Failed) {
            Toast.makeText(this, "Payment failed.", Toast.LENGTH_SHORT).show();
        }
    }
}
