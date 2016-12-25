package com.muthusoft.edi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muthusoft.edi.helper.DataBaseHelper;
import com.muthusoft.edi.manager.Edimanager;
import com.muthusoft.edi.model.Users;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {

    EditText old_password, password, c_password;
    Button submit;
    DataBaseHelper db;
    boolean flag_new_user;
    String oldpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        flag_new_user = getIntent().getBooleanExtra("FLAG_NEW_USER", false);
        SharedPreferences sharedPreferences = getSharedPreferences("CHANGEPASSWORD", MODE_PRIVATE);
        oldpass = sharedPreferences.getString("PASSWORD", null);
        System.out.println("OLD PASS " + oldpass);
        if (!flag_new_user)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        password = (EditText) findViewById(R.id.newPassword);
        c_password = (EditText) findViewById(R.id.newCpassword);
        old_password = (EditText) findViewById(R.id.oldPassword);
        submit = (Button) findViewById(R.id.newPasswordSubmit);
        db = new DataBaseHelper(ChangePassword.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (old_password.getText().toString().isEmpty()) {
                    old_password.setError("Please enter the Old Password");
                    old_password.requestFocus();
                } else if (!oldpass.equals(old_password.getText().toString())) {
                    old_password.setError("Please Enter Valid Old Password");
                    old_password.requestFocus();
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Please enter the Password");
                    password.requestFocus();
                } else if (password.getText().toString().length() < 5) {

                    password.setError("Please Enter at least 5 Characters");
                    password.requestFocus();

                } else if (old_password.getText().toString().equals(password.getText().toString()) && old_password.getText().toString().equals(password.getText().toString())) {
                    password.setText("");
                    c_password.setText("");
                    password.setError("Please Enter Different Password");
                    password.requestFocus();


                } else if (password.getText().toString().equals(c_password.getText().toString())) {
                    JSONObject changePassword = new JSONObject();
                    Users active_user = db.getUser();
                    try {

                        changePassword.put("email", active_user.getUsername());
                        changePassword.put("password", active_user.getPassword());
                        changePassword.put("id", active_user.getUser_id());
                        changePassword.put("old_password", old_password.getText().toString());
                        changePassword.put("new_password", password.getText().toString());
                        changePassword.put("confirm_password", password.getText().toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String query_string = Edimanager.getQueryString(changePassword.toString());
                    if (Edimanager.haveNetworkConnection(ChangePassword.this)) {
                        new changePassword(query_string).execute();
                    } else {
                        Toast toast = Toast.makeText(ChangePassword.this, "No Internet Connection", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(ChangePassword.this, "Password and Confirm Password Mismatching", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            //noinspection SimplifiableIfStatement
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Async task class to get json by making HTTP call
     * <p/>
     * change Password
     */
    private class changePassword extends AsyncTask<Void, Void, Void> {

        String changePasswordResult;
        String jsonchangePassObj = "";
        ProgressDialog pDialog;


        changePassword(String jsonchangePassObj) {
            this.jsonchangePassObj = jsonchangePassObj;
            Log.v("changePassword", "executed");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChangePassword.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //publishProgress();

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = null;
            try {
                jsonStr = sh.makeServiceCall(Edimanager.getInstance().getBaseURL() + "app_change_password/" + Edimanager.stringToHashcode(jsonchangePassObj + Edimanager.hashKey) + "/1/", jsonchangePassObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("Response: ", ">changePasswordResult " + jsonStr);
            changePasswordResult = jsonStr;
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (changePasswordResult != null) {
                try {
                    JSONObject jsonObj = new JSONObject(changePasswordResult);
                    int is_error = jsonObj.getInt("iserr");
                    if (is_error == 1) {
                        Log.d("Response: ", "> change Password Failed. " + jsonObj.getString("message"));
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                        builder.setTitle("Change Password");
                        builder.setMessage(jsonObj.getString("message") + " You will be redirected to Login Screen");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //updating Password
                                db = new DataBaseHelper(ChangePassword.this);
                                db.updateUserPassword(password.getText().toString(), db.getUser().getUser_id());

                                // Redirect to Login Page //
                                db.deleteNonMasterTable();
                                Edimanager.signOut = true;
                                //System.exit(0);
                                Intent login = new Intent(ChangePassword.this, LoginActivity.class);
                                startActivity(login);
                                finish();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        }
    }
}
