package com.muthusoft.edi;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.muthusoft.edi.helper.DataBaseHelper;
import com.muthusoft.edi.manager.Edimanager;
import com.muthusoft.edi.model.BankCategories;
import com.muthusoft.edi.model.Category;
import com.muthusoft.edi.model.Colleges;
import com.muthusoft.edi.model.Districts;
import com.muthusoft.edi.model.Gender;
import com.muthusoft.edi.model.Industries;
import com.muthusoft.edi.model.Mentor;
import com.muthusoft.edi.model.Prefixes;
import com.muthusoft.edi.model.Specilazation;
import com.muthusoft.edi.model.University;
import com.muthusoft.edi.model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class LoginActivity extends AppCompatActivity {
    private static int PERMISSION_REQUEST_CODE = 10;
    private final int REQUEST_PERMISSION_REQ_CODE = 11;
    String PREFS = "MyPrefs";
    SharedPreferences mPrefs;
    String username = null;
    String password = null;
    boolean rememberMe;
    DataBaseHelper db = new DataBaseHelper(LoginActivity.this);
    TextView btnSignup;
    ImageButton btnLogin;
    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private Button btnReset;
    private int remember = 1;
    private CheckBox saveLoginCheckBox;
    private Boolean saveLogin;
    private String user_name, user_password;
    private int user_keepme_logged_in = 0;
    private boolean flag_new_user;
    private String MyPREFERENCES = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db.deleteNonMasterTable();
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (TextView) findViewById(R.id.btn_signup);
        btnLogin = (ImageButton) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.checkBox);


        // Requesting User Permission //
        getPermissions();

        // getting Master Tables //


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("CHANGEPASSWORD", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("PASSWORD", password);
                editor.commit();
                if (username.equals("") || username.equals(null) || username.length() < 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(LoginActivity.this, R.style.myDialog));
                    builder.setTitle("EDI")
                            .setMessage("Please Enter Email")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(R.drawable.ic_launcher)
                            .show();
                } else if (password.equals("") || password.equals(null) || password.length() < 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(LoginActivity.this, R.style.myDialog));
                    builder.setTitle("EDI")
                            .setMessage("Please Enter Password")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(R.drawable.ic_launcher)
                            .show();
                } else if (Edimanager.haveNetworkConnection(LoginActivity.this)) {

                    JSONObject loginOBJ = new JSONObject();
                    try {

                        loginOBJ.put("email", username);
                        loginOBJ.put("password", password);
                        user_name = username;
                        user_password = password;
                        if (saveLoginCheckBox.isChecked()) {
                            user_keepme_logged_in = 1;
                            Log.v("Keep Me Logged In", "true");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String login_query_string = Edimanager.getQueryString(loginOBJ.toString());
                    new loginOnline(login_query_string).execute();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("No Internet Connection");
                    builder.setMessage("Please Enable Internet Conncetion to continue.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    builder.setIcon(R.drawable.ic_launcher);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(forget);
                finish();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((db.getPrefixesCount() == 0 || db.getDistrictCount() == 0 || db.getCategorycount() == 0
                        || db.getGendercount() == 0 || db.getDistrictcount() == 0) && Edimanager.haveNetworkConnection(LoginActivity.this)) {
                    new getAllMasterTables().execute();
                } else {
                    Intent reg = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(reg);
                    finish();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem settingsMenu = menu.findItem(R.id.action_settings);
        settingsMenu.setEnabled(false);
        settingsMenu.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setCancelable(true);
            builder.setTitle(R.string.app_name);
            builder.setInverseBackgroundForced(true);
            builder.setIcon(R.drawable.ic_launcher);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            /*Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);*/
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void getPermissions() {
        if ((ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    // Copying Master Tables //


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("EDI")
                            .setMessage("Please give Permissions to EDI for better perfomance!!!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getPermissions();
                                }
                            })
                            .setIcon(R.drawable.ic_launcher)
                            .show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    /**
     * Async task class to get json by making HTTP call
     * <p/>
     * Login
     */

    private class loginOnline extends AsyncTask<Void, Void, Void> {

        String loginResult;
        String jsonLoginObj = "";
        ProgressDialog pDialog;

        loginOnline(String jsonLoginObj) {
            this.jsonLoginObj = jsonLoginObj;
            Log.v("loginOnline", "executed");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            publishProgress();
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = null;
            try {
                jsonStr = sh.makeServiceCall(Edimanager.getInstance().getBaseURL() + "app_login/" + Edimanager.stringToHashcode(jsonLoginObj + Edimanager.hashKey) + "/1/?rand" + Math.random(), jsonLoginObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("Response: ", ">loginResult " + jsonStr);

            loginResult = jsonStr;


            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            //      DataBaseHelper db;
            //    db = new DataBaseHelper(getApplication().getBaseContext());


            super.onPostExecute(result);

            if (loginResult != null) {
                try {
                    JSONObject jsonObj = new JSONObject(loginResult);
                    // Getting JSON Array node
                    // Getting Services
                    int is_error = jsonObj.getInt("iserr");
                    //Toast.makeText(getApplication().getBaseContext(), " " + jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                    if (is_error == 1) {
                        Log.d("Response: ", "> login Failed. " + jsonObj.getString("message"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Login Error");
                        builder.setMessage(jsonObj.getString("message"));

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                //     finish();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        Log.d("Response: ", "> login Success. " + jsonObj.getString("message"));
                        // Inserting Users //
                        JSONObject userObj = jsonObj.getJSONObject("Entrepreneur");
                        Users user = new Users();
                        user.setUser_id(userObj.getInt("id"));
                        user.setPrifix_id(userObj.getInt("prefix_id"));
                        user.setName(userObj.getString("name"));
                        user.setDob(userObj.getString("dob"));
                        user.setGender_id(userObj.getInt("gender_id"));
                        user.setMobile(userObj.getString("mobile"));
                        user.setEmail(userObj.getString("email"));
                        user.setPassword(user_password);
                        user.setPassword_hashed(userObj.getString("password"));
                        user.setCompany_name(userObj.getString("company_name"));
                        user.setDistricid(userObj.getInt("district_id"));
                        user.setPincode(userObj.getInt("pincode"));
                        user.setAddress(userObj.getString("address"));
                        user.setIndustry_id(userObj.getString("industry_id"));
                        user.setSpecialization_id(userObj.getString("specialization_id"));
                        user.setSupport_required(userObj.getString("support_required"));
                        user.setAadhar_no(userObj.getString("aadhar_no"));
                        user.setIs_active(userObj.getInt("active"));
                        user.setPhoto_path(userObj.getString("photo_path"));

                        // System.out.println("USER OBJECT : " + userObj);
                        int id = db.createUser(user);
                        if (id != 1) {
                            Toast.makeText(LoginActivity.this, "Login Failed due to some internal Error", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject loginOBJ = new JSONObject();
                            try {
                                db = new DataBaseHelper(LoginActivity.this);
                                loginOBJ.put("email", userObj.getString("email"));
                                loginOBJ.put("password", user_password);
                                loginOBJ.put("user_id", userObj.getInt("id"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String query_string = Edimanager.getQueryString(loginOBJ.toString());
                            if (flag_new_user) {
                                Intent ias = new Intent(LoginActivity.this, ChangePassword.class);
                                ias.putExtra("FLAG_NEW_USER", true);
                                startActivity(ias);
                                finish();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                new getAllComplaints(query_string).execute();
                            }
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        }
    }

    private class getAllComplaints extends AsyncTask<Void, Void, Void> {

        String allMentorResult;
        String jsonAllComplaintsObj = "";
        ProgressDialog pDialog;

        getAllComplaints(String jsonAllComplaintsObj) {
            this.jsonAllComplaintsObj = jsonAllComplaintsObj;
            Log.v("getAllComplaints", "executed");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            publishProgress();
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = null;
            try {
                jsonStr = sh.makeServiceCall(Edimanager.getInstance().getBaseURL() + "app_mentor_list/" + Edimanager.stringToHashcode(jsonAllComplaintsObj + Edimanager.hashKey) + "/1", jsonAllComplaintsObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("Response: ", ">allMentorResult " + jsonStr);

            allMentorResult = jsonStr;


            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            //      DataBaseHelper db;
            //    db = new DataBaseHelper(getApplication().getBaseContext());


            super.onPostExecute(result);

            if (allMentorResult != null) {
                try {
                    JSONObject jsonObj = new JSONObject(allMentorResult);
                    // Getting JSON Array node
                    // Getting Services
                    int is_error = jsonObj.getInt("iserr");
                    //Toast.makeText(getApplication().getBaseContext(), " " + jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                    if (is_error == 1) {
                        Log.d("Response: ", "> All Complaints Failed. " + jsonObj.getString("message"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Complaint List");
                        builder.setMessage(jsonObj.getString("message"));
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                //     finish();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        Log.d("Response: ", "> AllMentor Success. " + jsonObj.getString("message"));
                        // Inserting Complaints //
                        if (!jsonObj.isNull("Mentor")) {
                            JSONArray mentorList = jsonObj.getJSONArray("Mentor");
                            for (int i = 0; i < mentorList.length(); i++) {

                                JSONObject mentor_obj = mentorList.getJSONObject(i);
                                Mentor mentor = new Mentor();
                                mentor.setMentor_id(mentor_obj.getInt("id"));
                                mentor.setMentor_name(mentor_obj.getString("name"));
                                mentor.setIndustry(mentor_obj.getString("industry"));
                                mentor.setSpecialization(mentor_obj.getString("specialization"));
                                mentor.setExperience(mentor_obj.getString("experience"));
                                mentor.setLast_use(mentor_obj.getString("last_use"));
                                mentor.setProficiency_level(mentor_obj.getString("proficiency_level"));
                                mentor.setQualification(mentor_obj.getString("qualification"));
                                mentor.setAchivements(mentor_obj.getString("achivements"));
                                mentor.setResume(mentor_obj.getString("resume"));
                                mentor.setStatus(mentor_obj.getString("status"));

                                db.createMentor(mentor);
                                db = new DataBaseHelper(LoginActivity.this);
                                if (db.getMentorByMentorID(mentor_obj.getInt("id")) == null) {
                                    db.createMentor(mentor);
                                    db.close();
                                } else {
                                    db.updateMentor(mentor);
                                    db.close();
                                }

                            }
                        }
                        SharedPreferences mPrefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPrefs.edit();
                        editor.putString("LOGIN", "Login");
                        editor.commit();
                        Users users = new Users();
                        Intent ias = new Intent(LoginActivity.this, MainActivity.class);
                        ias.putExtra("UNAME", users.getName());
                        startActivity(ias);
                        finish();
                        progressBar.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        }
    }

    private class getAllMasterTables extends AsyncTask<Void, Void, Void> {

        String MastersResult;
        ProgressDialog pDialog;

        getAllMasterTables() {
        }

        @Override
        protected void onPreExecute() {
            Log.v("AllMasterTables", "executed");
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            publishProgress();

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = null;
            try {
                jsonStr = sh.makeServiceCall(Edimanager.getInstance().getBaseURL() + "app_all_masters/" + Edimanager.stringToHashcode(Edimanager.hashKey) + "/1//?rand" + Math.random(), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("Response: ", ">MastersResult " + jsonStr);

            //jsonStr.replaceAll("null", "0");
            MastersResult = jsonStr;

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            //   DatabaseHelper db;
            //   db = new DatabaseHelper(ComplaintPopupView.this);
            super.onPostExecute(result);
            if (MastersResult != null) {
                try {
                    JSONObject jsonObj = new JSONObject(MastersResult);
                    //int is_error = jsonObj.getInt("iserr"); is_error == 1
                    if (false) {
                        Log.d("Response: ", ">Masters Failed. " + jsonObj.getString("message"));
                    } else {

                        // Clearing Master Tables //
                        db.deleteMasterTable();

                        // Updating With New Values //
                        //Complaint Status //
                        JSONObject complaintStatus = jsonObj.getJSONObject("Prefixes");
                        Iterator<String> items = complaintStatus.keys();
                        while (items.hasNext()) {
                            String key = items.next();
                            try {
                                Object value = complaintStatus.get(key);
                                Prefixes prefixes = new Prefixes();
                                prefixes.setId(Integer.parseInt(key));
                                prefixes.setPrefixes_name(String.valueOf(value));
                                // System.out.println("KEY COMPLETED " + String.valueOf(value));
                                db.createPrefixes(prefixes);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        // Districts //
                        JSONObject districts1 = jsonObj.getJSONObject("Districts");
                        Iterator<String> scheme_items = districts1.keys();
                        while (scheme_items.hasNext()) {
                            String key = scheme_items.next();
                            try {
                                Object value = districts1.get(key);
                                Districts districts = new Districts();
                                districts.setId(Integer.parseInt(key));
                                districts.setDistrict_name(String.valueOf(value));
                                db.createDistrict(districts);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }

                        // Categories //
                        JSONObject Categories = jsonObj.getJSONObject("Categories");
                        Iterator<String> Categories_items = Categories.keys();
                        while (Categories_items.hasNext()) {
                            String key = Categories_items.next();
                            try {
                                Object value = Categories.get(key);
                                Category category = new Category();
                                category.setId(Integer.parseInt(key));
                                category.setCategory_name(String.valueOf(value));
                                db.createCategory(category);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        // Gender //
                        JSONObject GenderObject = jsonObj.getJSONObject("Genders");
                        Iterator<String> gender_items = GenderObject.keys();
                        while (gender_items.hasNext()) {
                            String key = gender_items.next();
                            try {
                                Object value = GenderObject.get(key);
                                Gender gender = new Gender();
                                gender.setId(Integer.parseInt(key));
                                gender.setGender_name(String.valueOf(value));
                                db.createGender(gender);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        // Industries //
                        JSONObject IndustriesObject = jsonObj.getJSONObject("Industries");
                        Iterator<String> industries_items = IndustriesObject.keys();
                        while (industries_items.hasNext()) {
                            String key = industries_items.next();
                            try {
                                Object value = IndustriesObject.get(key);
                                Industries industries = new Industries();
                                industries.setId(Integer.parseInt(key));
                                industries.setIndusties_name(String.valueOf(value));
                                db.createIndustries(industries);
                                JSONObject SpecilazationObject = jsonObj.getJSONObject("Specializations");
                                Iterator<String> specilazation_items = SpecilazationObject.keys();
                                while (specilazation_items.hasNext()) {
                                    String keys = specilazation_items.next();
                                    try {
                                        Object values = SpecilazationObject.get(keys);
                                        Specilazation specilazation = new Specilazation();
                                        specilazation.setId(Integer.parseInt(keys));
                                        specilazation.setIndust_id(Integer.parseInt(key));
                                        specilazation.setSpecilazation_name(String.valueOf(values));
                                        db.createSpecilazation(specilazation);
                                    } catch (JSONException e) {
                                        // Something went wrong!
                                    }
                                }
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        // Specilazation //
// Complaint Categories //
                        // Bank_categroy //
                        JSONObject Bank_categroy = jsonObj.getJSONObject("BankCategories");
                        Iterator<String> bank_items = Bank_categroy.keys();
                        while (bank_items.hasNext()) {
                            String key = bank_items.next();
                            try {
                                Object value = Bank_categroy.get(key);
                                BankCategories bankCategories = new BankCategories();
                                bankCategories.setId(Integer.parseInt(key));
                                bankCategories.setBank_name(String.valueOf(value));
                                db.createBankCategories(bankCategories);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }

                        // UNIVERSITY //
                        JSONObject UniversityObject = jsonObj.getJSONObject("UniversityTypes");
                        Iterator<String> univercity_items = UniversityObject.keys();
                        while (univercity_items.hasNext()) {
                            String key = univercity_items.next();
                            try {
                                Object value = UniversityObject.get(key);
                                University university = new University();
                                university.setId(Integer.parseInt(key));
                                university.setUnivercity_name(String.valueOf(value).replace("'", " "));
                                db.createUniversity(university);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }

                        // Colleges_categroy //
                        JSONObject Colleges_categroyObject = jsonObj.getJSONObject("TypeOfColleges");
                        Iterator<String> college_items = Colleges_categroyObject.keys();
                        while (college_items.hasNext()) {
                            String key = college_items.next();
                            try {
                                Object value = Colleges_categroyObject.get(key);
                                Colleges colleges = new Colleges();
                                colleges.setId(Integer.parseInt(key));
                                colleges.setColleges_name(String.valueOf(value));
                                db.createColleges(colleges);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }


                        Intent reg = new Intent(LoginActivity.this, SignupActivity.class);
                        startActivity(reg);
                        finish();
                        progressBar.setVisibility(View.GONE);
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
