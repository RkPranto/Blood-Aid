package com.example.bloodaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout mPhone, mPassword;
    Button mLogin, mRegister;
    TextView mForgotPass;
    String userphone, userpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Login API response call

                //Take inputs
                userphone = mPhone.getEditText().getText().toString().trim();
                userpass = mPassword.getEditText().getText().toString().trim();

                //check validation
                if(userphone.isEmpty()){
                    mPhone.setError("Phone number can't be empty !");
                }
                else if(userpass.isEmpty()){
                    mPassword.setError("Password can't be empty !");
                }
                else{
                    //do actual login check
                    mPassword.setError(null);
                    mPhone.setError(null);
                    final Call<ResponseBody> call = RetrofitInstance.getRetrofitInstance()
                            .create(BloodAidService.class)
                            .loginUser(userphone, userpass);


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        String s = response.body().string();

                                        //Response parsing
                                        Boolean status;
                                        if(s.isEmpty()){
                                            status = false;
                                        }
                                        else{
                                            JSONObject object = new JSONObject(s);
                                            status = object.getBoolean("validity"); // true or false will be returned as response
                                        }

                                        if(status){
                                            finish();
                                            startActivity( new Intent(LoginActivity.this, MainActivity.class) );
                                            AllToasts.successToast(LoginActivity.this, "Successfully Logged In");
                                        }
                                        else{
                                            AllToasts.errorToast(LoginActivity.this,"Phone or Password is not correct!" );
                                        }

                                    }catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(LoginActivity.this, e.getMessage()+" - JSON", Toast.LENGTH_LONG).show();

                                    }catch (IOException e) {
                                        e.printStackTrace();
                                        Toast.makeText(LoginActivity.this, e.getMessage()+" - IO", Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(LoginActivity.this, t.getMessage()+" .", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).start();


                }


            }
        });

        //divert to sign up
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


    }

    private void init() {
        mPhone = findViewById(R.id.textInputLoginPhone);
        mPassword = findViewById(R.id.textInputLoginPass);
        mLogin = findViewById(R.id.login_btn);
        mRegister = findViewById(R.id.sign_up_btn);
        mForgotPass = findViewById(R.id.forgot_text);
    }

}
