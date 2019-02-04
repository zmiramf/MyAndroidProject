package com.company.zm.myandroidproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveUserToFile("zmira&1234/");
    }

    public void ShowLoginFragment(View view) {
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.show(getFragmentManager(), "");
    }

    public void ShowSignUpFragment(View view) {
        SignUpFragment signUpFragment = new SignUpFragment();
        signUpFragment.show(getFragmentManager(), "");
    }

    public void saveUserToFile(String s){
        OutputStream outputStream = null;
        try{
            outputStream = openFileOutput(SignUpFragment.MY_FILE_TXT, MODE_PRIVATE);
            outputStream.write(s.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
