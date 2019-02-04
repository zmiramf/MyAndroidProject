package com.company.zm.myandroidproject;

import android.app.DialogFragment;
import android.content.Context;
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
import java.util.HashMap;
import java.util.Map;

import static com.company.zm.myandroidproject.SignUpFragment.MY_FILE_TXT;


public class LoginFragment extends DialogFragment {

    private EditText txtUserName;
    private EditText txtPassword;
    private ImageButton btnLogin;
    private TextView errorMsg;
    private User user;
    public static String users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_fragment, container, false);
        txtUserName = view.findViewById(R.id.txtUserName);
        txtPassword = view.findViewById(R.id.txtPassword);
        btnLogin = view.findViewById(R.id.btnRealLogin);
        errorMsg = view.findViewById(R.id.errorsMsg);
        users = readStringFromFile();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                user = new User(username, password);
                //checking if pass or username aren't correct
                if (checkingDetailsOfUser(user)) {
                    //going to changeMoney activity
                    Intent intent = new Intent(getContext(), ExchangeActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, user.getUserName());
                    startActivityForResult(intent, SignUpFragment.REQUEST_CODE);
                    getDialog().dismiss();
                }else if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                    errorMsg.setText("fields can't be empty");
                }else {
                    errorMsg.setText("password or username aren't correct");
                }
            }
        });
        return view;
    }


    public String readStringFromFile() {
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

    public static boolean checkingDetailsOfUser(User user) {
        Map<String, User> allUsers = getAllUsers();
        for (User userFromMap : allUsers.values()) {
            if (user.getUserName().equals(userFromMap.getUserName()) &&
                    user.getPassword().equals(userFromMap.getPassword()))
                return true;
        }
        return false;
    }


}
