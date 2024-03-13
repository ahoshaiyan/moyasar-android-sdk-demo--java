package com.example.moyasarjavademo;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.moyasarjavademo.databinding.ActivityMainBinding;
import com.moyasar.android.sdk.PaymentConfig;
import com.moyasar.android.sdk.PaymentResult;
import com.moyasar.android.sdk.PaymentSheet;
import com.moyasar.android.sdk.PaymentSheetResultCallback;
import com.moyasar.android.sdk.payment.models.Payment;

import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PaymentSheet paymentSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Map<String, String> metadata = new HashMap<String, String>();
        metadata.put("foo", "bar");

        PaymentConfig config = new PaymentConfig(
                100,
                "SAR",
                "Sample Android SDK Payment (Java Edition)",
                "pk_test_vcFUHJDBwiyjsh73khFuPpTnRPY4gp2aaYdNddY3",
                "https://api.moyasar.com",
                metadata,
                false,
                false,
                false
        );

        paymentSheet = new PaymentSheet(this, (PaymentSheetResultCallback) paymentResult -> handlePaymentResult(paymentResult), config);

        binding.payButton.setOnClickListener(view -> {
            paymentSheet.present();
        });
    }

    public void handlePaymentResult(PaymentResult result) {
        if (result instanceof PaymentResult.Completed) {
            handleCompletedPayment(((PaymentResult.Completed) result).getPayment());
        } else if (result instanceof PaymentResult.Failed) {
            System.out.println("Could not create payment");
        } else if (result instanceof PaymentResult.Canceled) {
            System.out.println("User canceled payment");
        }
    }

    private void handleCompletedPayment(Payment payment) {
        switch (payment.getStatus()) {
            case "paid":
                System.out.println("Payment is paid, ID: " + payment.getId());
                break;
            case "failed":
                System.out.println("Payment failed, ID: " + payment.getId());
                break;
            default:
                System.out.println("Got status " + payment.getStatus());
        }
    }
}
