package com.muthusoft.edi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.muthusoft.edi.helper.DataBaseHelper;
import com.muthusoft.edi.manager.Edimanager;
import com.muthusoft.edi.model.BankCategories;
import com.muthusoft.edi.model.Category;
import com.muthusoft.edi.model.Colleges;
import com.muthusoft.edi.model.Districts;
import com.muthusoft.edi.model.Gender;
import com.muthusoft.edi.model.Industries;
import com.muthusoft.edi.model.Prefixes;
import com.muthusoft.edi.model.Specilazation;
import com.muthusoft.edi.model.University;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SignupActivity extends AppCompatActivity {

    DataBaseHelper db = new DataBaseHelper(SignupActivity.this);
    LinearLayout entrepreneur, bankfinancialinstiute, supportlinear, serviceoffered, collegelinear, govermentdepartment, msmeLinear;
    ArrayList<String> BlockArrayList;
    private EditText inputName, inputMobile, inputEmail, inputPassword, supportrequired, supportoffer, collgename, departmentname, product, cluster;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private TextView prefix, category, dob, gender, district, specialization,
            industries, bankcategory, service_offer, univercitytype, typecollege, govermentype;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private String imeiNo = "";
    private String imsiNo = "";
    private String imeNum = "";
    private String tokrenid = "";
    private ArrayAdapter<String> blockAdapter;
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Firebase.setAndroidContext(this);
        //Get Firebase auth instance

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        prefix = (TextView) findViewById(R.id.prefix);
        category = (TextView) findViewById(R.id.category);
        industries = (TextView) findViewById(R.id.industries);
        specialization = (TextView) findViewById(R.id.specialization);
        bankcategory = (TextView) findViewById(R.id.bankcategory);
        supportrequired = (EditText) findViewById(R.id.supportrequired);
        service_offer = (TextView) findViewById(R.id.service_offer);
        univercitytype = (TextView) findViewById(R.id.univercitytype);
        typecollege = (TextView) findViewById(R.id.typecollege);
        govermentype = (TextView) findViewById(R.id.govermentype);
        supportoffer = (EditText) findViewById(R.id.supportoffer);
        collgename = (EditText) findViewById(R.id.collgename);
        departmentname = (EditText) findViewById(R.id.departmentname);
        product = (EditText) findViewById(R.id.product);
        cluster = (EditText) findViewById(R.id.cluster);
        dob = (TextView) findViewById(R.id.dob);
        gender = (TextView) findViewById(R.id.gender);
        district = (TextView) findViewById(R.id.district);
        inputName = (EditText) findViewById(R.id.uname);
        inputMobile = (EditText) findViewById(R.id.mobile);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        entrepreneur = (LinearLayout) findViewById(R.id.entrepreneur);
        bankfinancialinstiute = (LinearLayout) findViewById(R.id.bankfinancialinstiute);
        serviceoffered = (LinearLayout) findViewById(R.id.serviceoffered);
        supportlinear = (LinearLayout) findViewById(R.id.supportlinear);
        collegelinear = (LinearLayout) findViewById(R.id.collegelinear);
        govermentdepartment = (LinearLayout) findViewById(R.id.govermentdepartment);
        msmeLinear = (LinearLayout) findViewById(R.id.msmeLinear);
        entrepreneur.setVisibility(View.GONE);
        bankfinancialinstiute.setVisibility(View.GONE);
        supportlinear.setVisibility(View.GONE);
        serviceoffered.setVisibility(View.GONE);
        collegelinear.setVisibility(View.GONE);
        govermentdepartment.setVisibility(View.GONE);
        msmeLinear.setVisibility(View.GONE);
        // final String[] prefix_array = getResources().getStringArray(R.array.prefix);
        final ArrayList<String> prefixesArrayList = new ArrayList<String>();

        for (Prefixes prefixes : db.getAllPrefixes()) {
            prefixesArrayList.add(prefixes.getPrefixes_name());
        }
        final ArrayAdapter<String> prifixesAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_list_item_activated_1, prefixesArrayList);
        prefix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setAdapter(prifixesAdapter, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                prefix.setText(prefixesArrayList.get(which).toString());
                                // Log.v("SpinnerDistrict", prefixesArrayList.get(which).toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
        final ArrayList<String> categoryArrayList = new ArrayList<String>();
        categoryArrayList.add("Select Category");
        for (Category category : db.getAllCategory()) {
            categoryArrayList.add(category.getCategory_name());
        }
        final ArrayAdapter<String> catogeryAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_list_item_activated_1, categoryArrayList);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setAdapter(catogeryAdapter, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                category.setText(categoryArrayList.get(which).toString());
                                // Log.v("SpinnerCategory", categoryArrayList.get(which).toString());
                                if (categoryArrayList.get(which).toString().equals("Select Category")) {
                                    entrepreneur.setVisibility(View.GONE);
                                    bankfinancialinstiute.setVisibility(View.GONE);
                                    supportlinear.setVisibility(View.GONE);
                                    serviceoffered.setVisibility(View.GONE);
                                    collegelinear.setVisibility(View.GONE);
                                    govermentdepartment.setVisibility(View.GONE);
                                    msmeLinear.setVisibility(View.GONE);
                                } else if (categoryArrayList.get(which).toString().equals("Entrepreneur")) {
                                    entrepreneur.setVisibility(View.VISIBLE);
                                    bankfinancialinstiute.setVisibility(View.GONE);
                                    supportlinear.setVisibility(View.VISIBLE);
                                    serviceoffered.setVisibility(View.GONE);
                                    collegelinear.setVisibility(View.GONE);
                                    govermentdepartment.setVisibility(View.GONE);
                                    msmeLinear.setVisibility(View.GONE);
                                } else if (categoryArrayList.get(which).toString().equals("Bank and Financial Institute")) {
                                    bankfinancialinstiute.setVisibility(View.VISIBLE);
                                    entrepreneur.setVisibility(View.GONE);
                                    supportlinear.setVisibility(View.VISIBLE);
                                    serviceoffered.setVisibility(View.GONE);
                                    collegelinear.setVisibility(View.GONE);
                                    govermentdepartment.setVisibility(View.GONE);
                                    msmeLinear.setVisibility(View.GONE);
                                } else if (categoryArrayList.get(which).toString().trim().equals("Training Institution")) {
                                    bankfinancialinstiute.setVisibility(View.GONE);
                                    entrepreneur.setVisibility(View.GONE);
                                    supportlinear.setVisibility(View.VISIBLE);
                                    serviceoffered.setVisibility(View.VISIBLE);
                                    collegelinear.setVisibility(View.GONE);
                                    govermentdepartment.setVisibility(View.GONE);
                                    msmeLinear.setVisibility(View.GONE);
                                } else if (categoryArrayList.get(which).toString().trim().equals("Colleges")) {
                                    bankfinancialinstiute.setVisibility(View.GONE);
                                    entrepreneur.setVisibility(View.GONE);
                                    supportlinear.setVisibility(View.GONE);
                                    serviceoffered.setVisibility(View.GONE);
                                    collegelinear.setVisibility(View.VISIBLE);
                                    govermentdepartment.setVisibility(View.GONE);
                                    msmeLinear.setVisibility(View.GONE);
                                } else if (categoryArrayList.get(which).toString().trim().equals("Govt. Department")) {
                                    bankfinancialinstiute.setVisibility(View.GONE);
                                    entrepreneur.setVisibility(View.GONE);
                                    supportlinear.setVisibility(View.GONE);
                                    serviceoffered.setVisibility(View.GONE);
                                    collegelinear.setVisibility(View.GONE);
                                    govermentdepartment.setVisibility(View.VISIBLE);
                                    msmeLinear.setVisibility(View.GONE);
                                } else if (categoryArrayList.get(which).toString().trim().equals("MSME Associations")) {
                                    bankfinancialinstiute.setVisibility(View.GONE);
                                    entrepreneur.setVisibility(View.GONE);
                                    supportlinear.setVisibility(View.GONE);
                                    serviceoffered.setVisibility(View.GONE);
                                    collegelinear.setVisibility(View.GONE);
                                    govermentdepartment.setVisibility(View.GONE);
                                    msmeLinear.setVisibility(View.VISIBLE);
                                }
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
        final ArrayList<String> genderArrayList = new ArrayList<String>();
        genderArrayList.add("Select Gender");
        for (Gender gender : db.getAllGender()) {
            genderArrayList.add(gender.getGender_name());
        }
        final ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_list_item_activated_1, genderArrayList);
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setAdapter(genderAdapter, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                gender.setText(genderArrayList.get(which).toString());
                                //  Log.v("SpinnerGender", genderArrayList.get(which).toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
        final ArrayList<String> districtArrayList = new ArrayList<String>();
        districtArrayList.add("Select District");
        for (Districts districts : db.getAllDistrict()) {
            districtArrayList.add(districts.getDistrict_name());
        }
        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_list_item_activated_1, districtArrayList);
        district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setAdapter(districtAdapter, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                district.setText(districtArrayList.get(which).toString());
                                // Log.v("SpinnerDistrict", districtArrayList.get(which).toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
        final ArrayList<String> bankArrayList = new ArrayList<String>();
        bankArrayList.add("Select Bank");
        for (BankCategories bankCategories : db.getAllBank()) {
            bankArrayList.add(bankCategories.getBank_name());
        }
        final ArrayAdapter<String> bankAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_list_item_activated_1, bankArrayList);
        bankcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setAdapter(bankAdapter, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                bankcategory.setText(bankArrayList.get(which).toString());
                                //Log.v("SpinnerBank", bankArrayList.get(which).toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        final ArrayList<String> univercityArrayList = new ArrayList<String>();
        univercityArrayList.add("Select University");
        for (University university : db.getAllUnivercity()) {
            univercityArrayList.add(university.getUnivercity_name());
        }
        final ArrayAdapter<String> univercityAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_list_item_activated_1, univercityArrayList);
        univercitytype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setAdapter(univercityAdapter, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                univercitytype.setText(univercityArrayList.get(which).toString());
                                // Log.v("SpinnerUniversity", univercityArrayList.get(which).toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
        final ArrayList<String> collegesArrayList = new ArrayList<String>();
        collegesArrayList.add("Select College Type");
        for (Colleges colleges : db.getAllColleges()) {
            collegesArrayList.add(colleges.getColleges_name());
        }
        final ArrayAdapter<String> collegeAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_list_item_activated_1, collegesArrayList);
        typecollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setAdapter(collegeAdapter, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                typecollege.setText(collegesArrayList.get(which).toString());
                                // Log.v("SpinnerUniversity", collegesArrayList.get(which).toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        final String[] depatmentarray = getResources().getStringArray(R.array.govtdepartment);
        final ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_list_item_activated_1, depatmentarray);
        govermentype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setAdapter(departmentAdapter, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                govermentype.setText(depatmentarray[which].toString());
                                // Log.v("SpinnerDepartment", depatmentarray[which].toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        List<Industries> industriesList = db.getAllIndustries();
        final ArrayList<String> indsList = new ArrayList<String>();
        indsList.add("Select Industry");
        for (Industries industries : industriesList) {
            indsList.add(industries.getIndusties_name());
        }
        final ArrayAdapter<String> IndustriesAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_list_item_activated_1, indsList);
        industries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setAdapter(IndustriesAdapter, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                industries.setText(indsList.get(which).toString());
                                setBlockAdapter(indsList.get(which).toString());
                                //Select Subcategory
                                specialization.setText("Select Specialization");
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
        specialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setAdapter(blockAdapter, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                specialization.setText(BlockArrayList.get(which).toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                //finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Edimanager.haveNetworkConnection(getApplicationContext())) {
                    if (inputName.getText().toString().isEmpty() || inputName.getText().toString().length() < 0) {
                        inputName.setError("Please Enter Name");
                        inputName.requestFocus();
                    }
                }
                progressBar.setVisibility(View.VISIBLE);

                tokrenid = FirebaseInstanceId.getInstance().getToken();
                // System.out.println("TOKEN ID : " + tokrenid);
                TelephonyManager TM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                // IMEI No.
                imeiNo = TM.getDeviceId();
                // System.out.println("Android ImeNumber : " + imeiNo);
                // IMSI No.
                imsiNo = TM.getSubscriberId();
                imeNum = imeiNo + imsiNo;
                // System.out.println("IME ID : " + imeNum);

                JSONObject loginOBJ = new JSONObject();
                try {

                    loginOBJ.put("device_id", imeNum);
                    loginOBJ.put("register_id", tokrenid);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String login_query_string = Edimanager.getQueryString(loginOBJ.toString());
                new appTokenregister(login_query_string).execute();

            }


        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @SuppressWarnings("deprecation")
    public void dob(View view) {
        showDialog(999);
        /*Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();*/
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            DatePickerDialog dialog = new DatePickerDialog(this,
                    myDateListener,
                    year, month, day);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        // Do Stuff
                        showDate(year, month + 1, day);
                    }
                }

            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        // Do Stuff
                        dob.setText("Date of Birth *");
                    }
                }
            });
            return dialog;
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        dob.setText(new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year));
    }

    public void setBlockAdapter(String selectedIndustries) {
        // Log.v("setBlockAdapter", "Executed Value" + selectedIndustries);
        int id = db.getIndustriesIdByName(selectedIndustries);
        // Log.v("setIDNAME", "Executed ID NAME" + id);
        BlockArrayList = new ArrayList<String>();
        List<Specilazation> specilazationsList = db.getSpecilazationByIndustriesId(id);
        BlockArrayList.add("Select Specialization");
        if (id != 0) {
            for (Specilazation specilazation : specilazationsList) {
                BlockArrayList.add(specilazation.getSpecilazation_name());
            }
        }
        blockAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_list_item_activated_1, BlockArrayList);
    }

    private class appTokenregister extends AsyncTask<Void, Void, Void> {

        String loginResult;
        String jsonLoginObj = "";
        ProgressDialog pDialog;

        appTokenregister(String jsonLoginObj) {
            this.jsonLoginObj = jsonLoginObj;
            //   Log.v("loginOnline", "executed");
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
                jsonStr = sh.makeServiceCall(Edimanager.getInstance().getBaseURL() + "app_geting_resistration/" + Edimanager.stringToHashcode(jsonLoginObj + Edimanager.hashKey) + "/1/?rand" + Math.random(), jsonLoginObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Log.d("Response: ", ">tokenResult " + jsonStr);

            loginResult = jsonStr;


            return null;
        }

        @Override

        protected void onPostExecute(Void result) {

            // System.out.println("SUCESSFULLY RESULT : " + result);
            progressBar.setVisibility(View.GONE);
        }
    }
}