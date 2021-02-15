package com.YandexIndia.GetBranchName;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button getApiBtn, postApiBtn;
    private TextView Bank, Address, city, Branch, State, Mcr, Contact, Bankcode;
    RequestQueue requestQueue;
    EditText Key;
    private ProgressBar progressBar;
    LinearLayout linearLayout;
    String url, mkey;
    String value = "https://ifsc.razorpay.com/";
    private static final String TAG = MainActivity.class.getSimpleName();
    String mbankname, maddress, mstate, mbranchname, mcity, mcr, mbankcode, mcontact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Key = (EditText) findViewById(R.id.ifscocde);
        Bank = (TextView) findViewById(R.id.bankname);
        Branch = (TextView) findViewById(R.id.branchname);
        city = (TextView) findViewById(R.id.city);
        State = (TextView) findViewById(R.id.state);
        Address = (TextView) findViewById(R.id.address);
        Mcr = (TextView) findViewById(R.id.micr);
        Contact = (TextView) findViewById(R.id.contact);
        Bankcode = (TextView) findViewById(R.id.bankcode);
        getApiBtn = (Button) findViewById(R.id.getApiBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) findViewById(R.id.sublinearlaylout);
        // RequestQueue For Handle Network Request
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //Click Listner for GET JSONObject
        getApiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();

                progressBar.setVisibility(ProgressBar.VISIBLE);
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(ProgressBar.GONE);
                        //Do something after 100ms
                    }
                }, 5000);

            }
        });
    }

    public void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        try {

            mkey = Key.getText().toString();
            url = value + mkey;
            JSONObject object = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        maddress = response.getString("ADDRESS");
                        mcity = response.getString("CITY");
                        mbranchname = response.getString("BRANCH");
                        mbankname = response.getString("BANK");
                        mstate = response.getString("STATE");
                        mcr = response.getString("MICR");
                        mbankcode = response.getString("BANKCODE");
                        mcontact = response.getString("CONTACT");
                        Bank.setText(mbankname);
                        Branch.setText(mbranchname);
                        city.setText(mcity);
                        Address.setText(maddress);
                        State.setText(mstate);
                        Bankcode.setText(mbankcode);
                        Mcr.setText(mcr);
                        Contact.setText(mcontact);
                        if (response != null) {
                            progressBar.setVisibility(ProgressBar.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        } else {

                        }
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, "ErrorString", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(), "Please! check IFSC code", Toast.LENGTH_LONG).show();
                }
            });

            requestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}