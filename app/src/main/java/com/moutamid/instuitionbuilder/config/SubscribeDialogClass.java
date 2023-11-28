package com.moutamid.instuitionbuilder.config;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.moutamid.instuitionbuilder.Home.WalkThroughActivity;
import com.moutamid.instuitionbuilder.R;


import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;

import java.util.ArrayList;
import java.util.List;

public class SubscribeDialogClass extends Dialog implements
        View.OnClickListener {
    public Activity c;
    public Dialog d;
    private BillingClient billingClient;
    SkuDetails skuDetails;
    public SubscribeDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.subscribe);
        TextView move_next = findViewById(R.id.move_next);
        move_next.setOnClickListener(this);
        billingClient = BillingClient.newBuilder(c)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        // Connect BillingClient
        connectBillingClient();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_next:
               launchBillingFlow();
                dismiss();
                break;


            default:
                break;
        }
        dismiss();
    }
    private void connectBillingClient() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Billing client is ready
                    loadSubscriptionDetails();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to reconnect or handle the disconnect
            }
        });
    }

    private void loadSubscriptionDetails() {
        List<String> skuList = new ArrayList<>();
        skuList.add("your_subscription_sku_id"); // Replace with your actual subscription SKU ID

        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);

        billingClient.querySkuDetailsAsync(params.build(),
                (billingResult, skuDetailsList) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                        // Process the SKU details list
                        if (!skuDetailsList.isEmpty()) {
                            skuDetails = skuDetailsList.get(0);
                            // Update UI or store SKU details for later use
                        }
                    }
                });
    }

    private void launchBillingFlow() {
        String skuId = "your_subscription_sku_id"; // Replace with your actual subscription SKU ID

        if (skuDetails != null) {
            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                    .setSkuDetails(skuDetails)
                    .build();

            int responseCode = billingClient.launchBillingFlow(c, flowParams).getResponseCode();
        }
    }

    // Implement PurchasesUpdatedListener
    private final PurchasesUpdatedListener purchasesUpdatedListener = (billingResult, purchases) -> {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            // Process the purchase
            handlePurchase(purchases.get(0));
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle user cancellation
            Toast.makeText(c, "Subscription purchase canceled", Toast.LENGTH_SHORT).show();
        } else {
            // Handle other errors
            Toast.makeText(c, "Subscription purchase failed", Toast.LENGTH_SHORT).show();
        }
    };

    private void handlePurchase(Purchase purchase) {
        // Handle the purchase details, validate on your server, and grant access to the subscribed content
        if (purchase != null) {
            String purchaseToken = purchase.getPurchaseToken();
            // Send the purchase token to your server for validation
        }
    }

 
}