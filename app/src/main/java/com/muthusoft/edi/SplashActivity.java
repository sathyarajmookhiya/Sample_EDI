package com.muthusoft.edi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.muthusoft.edi.helper.DataBaseHelper;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splach_activity);
        logo = (ImageView) findViewById(R.id.imageView1);
        db = new DataBaseHelper(SplashActivity.this);

        /****** Create Thread that will sleep for 3 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 3 seconds
                    sleep(3 * 1000);
                    // After 3 seconds redirect to another intent

                    Intent homeIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(homeIntent);

                    //Remove activity
                    SplashActivity.this.finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
       /* if ((db.getStatusCount() == 0 || db.getSchemeCount() == 0 || db.getCategoriesCount() == 0) && Iasmanager.haveNetworkConnection(SplashActivity.this)) {
            new getAllMasterTables().execute();
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /**
     * Async task class to get json by making HTTP call
     * <p/>
     * MasterTables
     */
   /* private class getAllMasterTables extends AsyncTask<Void, Void, Void> {

        String MastersResult;

        getAllMasterTables() {
        }

        @Override
        protected void onPreExecute() {
            Log.v("AllMasterTables", "executed");
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
                jsonStr = sh.makeServiceCall(Iasmanager.getInstance().getBaseURL() + "app_all_masters/" + Iasmanager.stringToHashcode(Iasmanager.hashKey) + "/1//?rand" + Math.random(), "");
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
                        JSONObject complaintStatus = jsonObj.getJSONObject("ComplaintStatus");
                        Iterator<String> items = complaintStatus.keys();
                        while (items.hasNext()) {
                            String key = items.next();
                            try {
                                Object value = complaintStatus.get(key);
                                com.mslabs.tnhb.Model.Status status = new com.mslabs.tnhb.Model.Status();
                                status.setId(Integer.parseInt(key));
                                status.setStatus(String.valueOf(value));
                                System.out.println("KEY COMPLETED " + String.valueOf(value));
                                db.createStatus(status);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        // SCHEME //
                        JSONObject complaintScheme = jsonObj.getJSONObject("Scheme");
                        Iterator<String> scheme_items = complaintScheme.keys();
                        while (scheme_items.hasNext()) {
                            String key = scheme_items.next();
                            try {
                                Object value = complaintScheme.get(key);
                                com.mslabs.tnhb.Model.Scheme status = new com.mslabs.tnhb.Model.Scheme();
                                status.setId(Integer.parseInt(key));
                                status.setScheme(String.valueOf(value));
                                db.createScheme(status);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        // Complaint Categories //
                        List<Category> categories = new ArrayList<Category>();
                        List<SubCategory> subcategories = new ArrayList<SubCategory>();
                        String complaint_category = jsonObj.getString("ComplaintCategory");
                        JSONObject obj = new JSONObject(complaint_category);
                        Iterator<String> iter = obj.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                Object value = obj.get(key);
                                JSONObject category_obj = new JSONObject(String.valueOf(value));
                                String category_id = key;
                                String subCatg_obj = category_obj.getString("sub_category");
                                Category CatgMaster = new Category(Integer.parseInt(category_id), category_obj.getString("category"));
                                Log.v("Categories", Integer.parseInt(category_id) + "--" + category_obj.getString("category"));
                                categories.add(CatgMaster);
                                JSONObject subCatg_obj_json = new JSONObject(subCatg_obj);
                                Iterator<String> iter_subCatg = subCatg_obj_json.keys();
                                while (iter_subCatg.hasNext()) {
                                    String key_subCatg = iter_subCatg.next();
                                    try {
                                        Object value_subCatg = subCatg_obj_json.get(key_subCatg);
                                        SubCategory subcategoryMaster = new SubCategory(
                                                Integer.parseInt(key_subCatg), Integer.parseInt(category_id), String.valueOf(value_subCatg));
                                        Log.v("SubCategories", Integer.parseInt(category_id) + "--" + Integer.parseInt(key_subCatg) + "---" + String.valueOf(value_subCatg));
                                        subcategories.add(subcategoryMaster);
                                    } catch (JSONException e) {
                                        // Something went wrong!
                                    }
                                }
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        for (Category categoryMaster : categories) {
                            db.createCategory(categoryMaster);
                        }
                        for (SubCategory subcategoryMaster : subcategories) {
                            db.createSubCategory(subcategoryMaster);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        }
    }*/
}
