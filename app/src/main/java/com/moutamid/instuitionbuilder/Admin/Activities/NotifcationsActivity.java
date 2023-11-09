package com.moutamid.instuitionbuilder.Admin.Activities;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NotifcationsActivity extends AppCompatActivity {
    EditText edt_message;
    TextView send_notification;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifcations);
        edt_message = findViewById(R.id.edt_message);
        send_notification = findViewById(R.id.send_notification);
        progress_bar = findViewById(R.id.progress_bar);

        send_notification.setOnClickListener(view -> {
            if (edt_message.getText().toString().isEmpty()) {
                edt_message.setError("Please enter message!");
            } else {
                sendFCMPush(edt_message.getText().toString());

            }
        });
    }


    private void sendFCMPush(String message) {
        progress_bar.setVisibility(View.VISIBLE);
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", "Instuition Builder");
            notifcationBody.put("message", message);
             notification.put("to", "/topics/" + getString(R.string.app_name)+"general");
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.d("error", e.toString());
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Config.NOTIFICATIONAPIURL, notification,
                response -> {
                    Log.e("True", response + "");
                    Log.d("Responce", response.toString());
                    progress_bar.setVisibility(View.GONE);
                    Toast.makeText(NotifcationsActivity.this, "Successfully send a notification", Toast.LENGTH_SHORT).show();

                },
                error -> {
                    progress_bar.setVisibility(View.GONE);
                    Log.e("False", error.getMessage() + "  " + error.toString() + "");
                    Toast.makeText(NotifcationsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "key=" + Config.ServerKey);
//                params.put("Content-Type", "application/json");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }
}