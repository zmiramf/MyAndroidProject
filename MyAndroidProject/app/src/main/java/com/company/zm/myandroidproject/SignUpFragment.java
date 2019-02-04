package com.company.zm.myandroidproject;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SignUpFragment extends DialogFragment {

    public static final String MY_FILE_TXT = "my_users.txt";
    public static final int REQUEST_CODE = 123;

    private EditText txtUserName;
    private EditText txtPassword;
    private EditText txtConf;
    private ImageButton btnSignup;
    private TextView errorMsg;
    private User user;
    public static String users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment, container, false);
        txtUserName = view.findViewById(R.id.txtUserName);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtConf = view.findViewById(R.id.txtConfirmPassword);
        btnSignup = view.findViewById(R.id.btnRealSignup);
        errorMsg = view.findViewById(R.id.errorsMsg);
        users = readStringFromFile();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                String confPass = txtConf.getText().toString();


                //checking the inputs
                if(ifPassMatch(password, confPass)) {
                    errorMsg.setText("passwords do not match");
                }else if(userName.isEmpty() || userName == null || password.isEmpty() || password == null ||
                        confPass.isEmpty() || confPass == null) {
                    errorMsg.setText("fields can't be empty");
                }else if(ifUsernameTaken(userName)) {
                    errorMsg.setText("user name already taken");
                }else if(ifSlashinString(userName) || ifSlashinString(password)) {
                    errorMsg.setText("the char '/' can't be used");
                }else if(userName.length() > 15 || userName.length() < 5){
                    errorMsg.setText("user name between 5 - 15 chars");
                }else if(password.length() > 20 || password.length() < 5){
                    errorMsg.setText("password between 5 - 20 chars");
                }else {
                    //create the user
                    user = new User(userName, password);
                    //saving to a file the user
                    String allUsers = readStringFromFile();
                    String userAsString = allUsers + user.toString() + "/";
                    saveUserToFile(userAsString);
                    //going to ChangeMoneyActivity
                    Intent intent = new Intent(getContext(), ExchangeActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, user.getUserName());
                    startActivityForResult(intent, REQUEST_CODE);
                    getDialog().dismiss();
                }

            }
        });
        return view;
    }

    public static Boolean ifPassMatch(String pass1, String pass2) {
        return pass1.equals(pass2) ? false : true;
    }

    private void saveUserToFile(String s) {
        OutputStream outputStream = null;
        try {
            outputStream = this.getContext().openFileOutput(MY_FILE_TXT, MODE_PRIVATE);
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

    private String readStringFromFile() {
        InputStream inputStream = null;
        try {
            inputStream = this.getContext().openFileInput(MY_FILE_TXT);
            byte[] buffer = new byte[1024];
            int actuallyRead = inputStream.read(buffer);
            if (actuallyRead != -1) {
                String contentOfFile = new String(buffer, 0, actuallyRead);
                return contentOfFile;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Map<String, User> getAllUsers() {
        Map<String, User> allUsers = new HashMap<>();
        String[] arrUsers = users.split("/");
        for (int i = 0; i < arrUsers.length; i++) {
            String[] userString = arrUsers[i].split("&");
            User user = new User(userString[0], userString[1]);
            allUsers.put(user.getUserName(), user);
        }
        return allUsers;
    }

    public static boolean ifUsernameTaken(String username) {
        Map<String, User> allUsers = getAllUsers();
        for (User userFromMap : allUsers.values()) {
            if (username.equals(userFromMap.getUserName()))
                return true;
        }
        return false;
    }

    public static boolean ifSlashinString(String str){
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '/')
                return true;
        }
        return false;
    }



}
