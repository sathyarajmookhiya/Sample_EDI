package com.muthusoft.edi.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 23-11-2016.
 */
public class Edimanager {
    public static String hashKey = "dsbfsdbf234234bjb234b2k3bj4k2j3b4";
    public static int selectedPosition = -1;
    public static boolean developerMode = false; // false = Disable Developer Mode
    public static int boolean_true = 1;
    public static int boolean_false = 0;
    public static boolean signOut = false;
    public static int remember_me = 1;
    public static boolean isRefreshRequired;
    private static Edimanager ourInstance = new Edimanager();
    private static Pattern pattern;
    private static Matcher matcher;
    //private String baseURL = "http://muthusoftlabs.com/lmcts/resources/";
    private String baseURL = "http://muthusoftlabs.com/edi/Resources/";


    private String fireBaseURL = "https://fir-chat-59b2b.firebaseio.com/";
    private String uploadBaseURL = "https://s3-us-west-2.amazonaws.com/lmcts/";
    private String genOTP = "";
    private String lastAudioName = "";
    private String lastVideoName = "";
    private String lastImageName = "";

    private Edimanager() {
    }

    public static Edimanager getInstance() {
        return ourInstance;
    }

    /**
     * get formatedDate
     */
    public static String getFormatedDate(String current_format, String mydate, String new_format) {
        SimpleDateFormat srcDf = new SimpleDateFormat(current_format);

        try {
            Date date = srcDf.parse(mydate);
            SimpleDateFormat destDf = new SimpleDateFormat(new_format);
            mydate = destDf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return mydate;

    }

    /**
     * get datetime
     */
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.US);
        // dateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * get TimeStamp
     */
    public static String getTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        // dateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String stringToHashcode(String stringtohash) {
        try {
            //  stringtohash += hashKey;
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(stringtohash.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return ""; // Impossibru!
        }

    }

    public static String getQueryString(String unparsedString) {
        StringBuilder sb = new StringBuilder();
        JSONObject json = null;
        try {
            json = new JSONObject(unparsedString);

            Iterator<String> keys = json.keys();
            //      sb.append("?"); //start of query args
            while (keys.hasNext()) {
                String key = keys.next();
                sb.append(key);
                sb.append("=");
                String string = json.get(key).toString();
                sb.append(URLEncoder.encode(string, "UTF-8"));
                sb.append("&"); //To allow for another argument.

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str = sb.toString();
        return str.substring(0, str.length() - 1);
    }

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    //date validation
    public static boolean dateValidation(String date) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String Cur_date = df.format(new Date());
        Date d1 = null, d2 = null;
        try {
            d1 = df.parse(date);
            d2 = df.parse(Cur_date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        //Return true if date is null or Future date
        return !(d1 != null && d2 != null) || d1.after(d2);
    }

    public static Drawable drawableToBitmap(Context context, Drawable drawable, int width, int height) {
        Drawable d = null;
        if (drawable instanceof BitmapDrawable) {
            Log.i("ExpandableUtility", "Is Drawable");
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));
        }
        return d;
    }

    public String getGenOTP() {
        return genOTP;
    }

    public void setGenOTP(String genOTP) {
        this.genOTP = genOTP;
    }

    //observer.setTransferListener(new DocumentTransferListener());
    public String getBaseURL() {
        return baseURL;
    }

    public String getFireBaseURL() {
        return fireBaseURL;
    }

    public void setFireBaseURL(String fireBaseURL) {
        this.fireBaseURL = fireBaseURL;
    }

    public String getUploadBaseURL() {
        return uploadBaseURL;
    }
}

