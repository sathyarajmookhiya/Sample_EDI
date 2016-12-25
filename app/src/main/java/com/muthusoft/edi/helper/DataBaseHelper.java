package com.muthusoft.edi.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {


    //Prefixes Table Fields
    public static final String PREFIX_ID = "prefix_id";
    public static final String PREFIX_NAME = "prefix_name";

    //Prefixes Table Fields
    public static final String CATEGORY_ID = "Category_id";
    public static final String CATEGORY_NAME = "Category_name";

    //District Table Fields
    public static final String DIST_ID = "district_id";
    public static final String DIST_NAME = "district_name";


    // Industries Table Fields
    public static final String INDUS_ID = "indus_id";
    public static final String INDUS_NAME = "indus_name";

    //Specializations Table Fields
    public static final String SPEC_ID = "spec_id";
    public static final String SPEC_IND_ID = "ind_id";
    public static final String SPEC_NAME = "spec_name";

    //UniversityTypes Table Fields
    public static final String UNIVER_ID = "univer_id";
    public static final String UNIVER_NAME = "univer_name";

    //TypeOfColleges Table Fields
    public static final String COLLEGE_ID = "college_id";
    public static final String COLLEGE_NAME = "college_name";

    //Genders Table Fields

    public static final String GENDER_ID = "gender_id";
    public static final String GENDER_NAME = "gender_name";

    //BankCategories Table Fields

    public static final String BANK_ID = "bank_id";
    public static final String BANK_NAME = "bank_name";


    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mslabs_Edi";
    // TABLES //
    private static final String TABLE_USER = "user";
    private static final String TABLE_MENTOR = "mentor";
    private static final String TABLE_PREFIXES = "prefixes";
    private static final String TABLE_DISTRICTS = "district";
    private static final String TABLE_INDUSTRIES = "industries";
    private static final String TABLE_SPECILIZATION = "specilaization";
    private static final String TABLE_GENDER = "gender";
    private static final String TABLE_UNIVERSITY = "university";
    private static final String TABLE_COLLEGE_TYPE = "collegetype";
    private static final String TABLE_BANK = "bank";
    private static final String TABLE_CATEGORY = "category";


    private static final String KEY_ID = "id";
    // COLUMN NAMES Users //
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PREFIX_ID = "prefix_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DOB = "dob";
    private static final String KEY_GENDER_ID = "gender_id";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PASSWORD_HASHED = "password_hashed";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_COMPANY_NAME = "company_name";
    private static final String KEY_DISTRICT_ID = "district_id";
    private static final String KEY_PINCODE = "pincode";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_INDUSTRY_ID = "industry_id";
    private static final String KEY_SPECIALIZATION_ID = "specialization_id";
    private static final String KEY_SUPPORT_REQURIED = "support_required";
    private static final String KEY_AADHAR_NO = "aadhar_no";
    private static final String KEY_STATUS_ID = "status_id";
    private static final String KEY_ACTIVITY = "active";
    private static final String KEY_PHOTO_PATH = "photo_path";


    //COLUMN NAMES MENTOR //
    private static final String KEY_MENTOR_ID = "mid";
    private static final String KEY_MENTOR_NAME = "name";
    private static final String KEY_INDUSTRY = "industry";
    private static final String KEY_SPECIALIZATION = "specialization";
    private static final String KEY_EXPERIENCE = "experience";
    private static final String KEY_LAST_USE = "last_use";
    private static final String KEY_PROFICIENCY_LEVEL = "proficiency_level";
    private static final String KEY_QUALIFICATION = "qualification";
    private static final String KEY_ACHIVEMENTS = "achivements";
    private static final String KEY_RESUME = "resume";
    private static final String KEY_STATUS = "status";


    // Table create statements User Table //
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" + KEY_USER_ID
            + " INTEGER PRIMARY KEY," + KEY_PREFIX_ID + " INTEGER," + KEY_NAME + " TEXT," + KEY_PASSWORD_HASHED + " TEXT,"
            + KEY_DOB + " TEXT," + KEY_GENDER_ID + " INTEGER,"
            + KEY_MOBILE + " TEXT," + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT," +
            KEY_COMPANY_NAME + " TEXT," + KEY_DISTRICT_ID + " INTEGER,"
            + KEY_PINCODE + " INTEGER," + KEY_ADDRESS + " TEXT," + KEY_INDUSTRY_ID + " TEXT," +
            KEY_SPECIALIZATION_ID + " TEXT, " + KEY_SUPPORT_REQURIED + " INTEGER, " + KEY_AADHAR_NO + " TEXT," + KEY_ACTIVITY + " INTEGER, "
            + KEY_PHOTO_PATH + " TEXT " + ")";


    // Table create statements User Table //
    private static final String CREATE_TABLE_MENTOR = "CREATE TABLE " + TABLE_MENTOR + " ("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MENTOR_ID + " INTEGER," + KEY_MENTOR_NAME + " TEXT," + KEY_INDUSTRY + " TEXT,"
            + KEY_SPECIALIZATION + " TEXT," + KEY_EXPERIENCE + " TEXT," + KEY_LAST_USE + " TEXT," + KEY_PROFICIENCY_LEVEL + " TEXT," +
            KEY_COMPANY_NAME + " TEXT," + KEY_DISTRICT_ID + " INTEGER,"
            + KEY_QUALIFICATION + " TEXT," +
            KEY_ACHIVEMENTS + " TEXT," + KEY_RESUME + " TEXT,"
            + KEY_STATUS + " TEXT " + ")";


    // Table create statements PREFIXES Table //
    private static final String CREATE_TABLE_PREFIXES = "CREATE TABLE " + TABLE_PREFIXES + " ("
            + KEY_ID + " INTEGER PRIMARY KEY," + PREFIX_ID + " INTEGER," + PREFIX_NAME + " TEXT " + ")";


    // Table create statements Category Table //
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + " ("
            + KEY_ID + " INTEGER PRIMARY KEY," + CATEGORY_ID + " INTEGER," + CATEGORY_NAME + " TEXT " + ")";

    // Table create statements DISRICTS Table //
    private static final String CREATE_TABLE_DISTRICTS = "CREATE TABLE " + TABLE_DISTRICTS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY," + DIST_ID + " INTEGER," + DIST_NAME + " TEXT " + ")";

    // Table create statements INDUSTRIES Table //
    private static final String CREATE_TABLE_INDUSTRIES = "CREATE TABLE " + TABLE_INDUSTRIES + " ("
            + KEY_ID + " INTEGER PRIMARY KEY," + INDUS_ID + " INTEGER," + INDUS_NAME + " TEXT " + ")";
    // Table create statements SPECILAZATION Table //
    private static final String CREATE_TABLE_SPECILIZATION = "CREATE TABLE " + TABLE_SPECILIZATION + " ("
            + KEY_ID + " INTEGER PRIMARY KEY," + SPEC_ID + " INTEGER," + SPEC_IND_ID + " INTEGER," + SPEC_NAME + " TEXT " + ")";

    // Table create statements GENDER Table //
    private static final String CREATE_TABLE_GENDER = "CREATE TABLE " + TABLE_GENDER + " ("
            + KEY_ID + " INTEGER PRIMARY KEY," + GENDER_ID + " INTEGER," + GENDER_NAME + " TEXT " + ")";

    // Table create statements UNIVERSITY Table //
    private static final String CREATE_TABLE_UNIVERSITY = "CREATE TABLE " + TABLE_UNIVERSITY + " ("
            + KEY_ID + " INTEGER PRIMARY KEY," + UNIVER_ID + " INTEGER," + UNIVER_NAME + " TEXT " + ")";

    // Table create statements COLLEGES Table //
    private static final String CREATE_TABLE_COLLEGES = "CREATE TABLE " + TABLE_COLLEGE_TYPE + " ("
            + KEY_ID + " INTEGER PRIMARY KEY," + COLLEGE_ID + " INTEGER," + COLLEGE_NAME + " TEXT " + ")";

    // Table create statements BANK Table //
    private static final String CREATE_TABLE_BANK = "CREATE TABLE " + TABLE_BANK + " ("
            + KEY_ID + " INTEGER PRIMARY KEY," + BANK_ID + " INTEGER," + BANK_NAME + " TEXT " + ")";

    private static String DB_PATH;
    private static String DB_NAME = "master.db";
    public SQLiteDatabase myDataBase;
    private Context mycontext;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mycontext = context;
    }

    public void copydatabase() {
        try {
            //Open your local db as the input stream
            //DB_PATH = "/data/data/" + mycontext.getPackageName() + "/databases/";
            DB_PATH = Environment.getExternalStorageDirectory() + "/EDI/";
            InputStream myinput = mycontext.getAssets().open(DB_NAME);
            File mediaFile = new File(DB_PATH);
            if (!mediaFile.exists()) {
                mediaFile.mkdirs();
            }
            // Path to the just created empty db
            String outfilename = DB_PATH + DB_NAME;

            //Open the empty db as the output stream
            OutputStream myoutput = new FileOutputStream(outfilename);

            // transfer byte to inputfile to outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myinput.read(buffer)) > 0) {
                myoutput.write(buffer, 0, length);
            }

            //Close the streams
            myoutput.flush();
            myoutput.close();
            myinput.close();
            Log.v("CopyDatabase", "Executed");
            ImportDatabase();
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImportDatabase() {
        DataBaseHelper myDbHelper = new DataBaseHelper(mycontext);
        myDbHelper = new DataBaseHelper(mycontext);

        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Log.v("checkdatabase", String.valueOf(checkdatabase()));
//        db.execSQL("ATTACH DATABASE "+ DB_PATH + DB_NAME + " AS New_DB");
        // db.execSQL("attach database ? as New_DB", new String[]{"/storage/emulated/0/TACTV-LF/"+DB_NAME});
        Log.v("Path", DB_PATH + DB_NAME);
        db.execSQL("attach database ? as master", new String[]{DB_PATH + DB_NAME});
        db.execSQL("INSERT OR REPLACE INTO category SELECT * FROM master.category");

        Log.v("ImportDatabase", "Executed");
        File file = new File(DB_PATH, DB_NAME);
        if (file.exists())
            file.delete();
    }

    public void openDataBase() {
        try {
            //Open the database
            String mypath = DB_PATH + DB_NAME;
            myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            //  System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MENTOR);
        db.execSQL(CREATE_TABLE_PREFIXES);
        db.execSQL(CREATE_TABLE_DISTRICTS);
        db.execSQL(CREATE_TABLE_INDUSTRIES);
        db.execSQL(CREATE_TABLE_SPECILIZATION);
        db.execSQL(CREATE_TABLE_GENDER);
        db.execSQL(CREATE_TABLE_UNIVERSITY);
        db.execSQL(CREATE_TABLE_COLLEGES);
        db.execSQL(CREATE_TABLE_BANK);
        db.execSQL(CREATE_TABLE_CATEGORY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFIXES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRICTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDUSTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECILIZATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLEGE_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIVERSITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);

    }

    public void deleteNonMasterTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_USER, null, null);
        db.delete(TABLE_MENTOR, null, null);
        db.close();

    }

    public void deleteMasterTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PREFIXES, null, null);
        db.delete(TABLE_DISTRICTS, null, null);
        db.delete(TABLE_INDUSTRIES, null, null);
        db.delete(TABLE_SPECILIZATION, null, null);
        db.delete(TABLE_UNIVERSITY, null, null);
        db.delete(TABLE_COLLEGE_TYPE, null, null);
        db.delete(TABLE_GENDER, null, null);
        db.delete(TABLE_BANK, null, null);
        db.delete(TABLE_CATEGORY, null, null);
        db.close();
    }


    /*
     * User Table Methods
     */

    public int createUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_USER + "(user_id,prefix_id,name,password_hashed,dob,gender_id," +
                "mobile,email,password,company_name,district_id," +
                "pincode,address,industry_id,specialization_id,support_required,aadhar_no," +
                "active,photo_path) VALUES (" + user.getUser_id() + ","
                + user.getPrifix_id() + ",'" + user.getName() + "','" + user.getPassword_hashed() + "','" + user.getDob() + "',"
                + user.getGender_id() + ",'" + user.getMobile() + "','" + user.getEmail() + "','" + user.getPassword() + "','"
                + user.getCompany_name() + "'," + user.getDistricid() + "," + user.getPincode() + ",'" + user.getAddress() + "','"
                + user.getIndustry_id() + "','" + user.getSpecialization_id() + "','" + user.getSupport_required() + "','" + user.getAadhar_no() + "',"
                + user.getIs_active() + ",'" + user.getPhoto_path() + "')");
        db.close();
        return 1;
    }

    public int createMentor(Mentor mentor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_MENTOR_ID, mentor.getMentor_id());
        initialValues.put(KEY_MENTOR_NAME, mentor.getMentor_name());
        initialValues.put(KEY_INDUSTRY, mentor.getIndustry());
        initialValues.put(KEY_SPECIALIZATION, mentor.getSpecialization());
        initialValues.put(KEY_EXPERIENCE, mentor.getExperience());
        initialValues.put(KEY_LAST_USE, mentor.getLast_use());
        initialValues.put(KEY_PROFICIENCY_LEVEL, mentor.getProficiency_level());
        initialValues.put(KEY_QUALIFICATION, mentor.getQualification());
        initialValues.put(KEY_ACHIVEMENTS, mentor.getAchivements());
        initialValues.put(KEY_RESUME, mentor.getResume());
        initialValues.put(KEY_STATUS, mentor.getStatus());


        int id = (int) db.insert(TABLE_MENTOR, null, initialValues);
        db.close();
        return id;
    }


    /*
   * Category Table PREFIXES
   * */
    public int createPrefixes(Prefixes prefixes) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_PREFIXES + " (" + PREFIX_ID + "," + PREFIX_NAME + ") VALUES (" + prefixes.getId() + ",'" + prefixes.getPrefixes_name() + "')");
        db.close();
        return 1;
    }

    /*
       * Category Table INDUSTRIES
       * */
    public int createIndustries(Industries industries) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_INDUSTRIES + " (" + INDUS_ID + "," + INDUS_NAME + ") VALUES (" + industries.getId() + ",'" + industries.getIndusties_name() + "')");
        db.close();
        return 1;
    }

    /*
      * Category Table SPECILAZAIONS
      * */
    public int createSpecilazation(Specilazation specilazation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_SPECILIZATION + " (" + SPEC_ID + "," + SPEC_IND_ID + "," + SPEC_NAME + ") VALUES (" + specilazation.getId() + "," + specilazation.getIndust_id() + ",'" + specilazation.getSpecilazation_name() + "')");
        db.close();
        return 1;
    }

    /*
     * Category Table DISTRICTES
     * */
    public int createDistrict(Districts districts) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_DISTRICTS + " (" + DIST_ID + "," + DIST_NAME + ") VALUES (" + districts.getId() + ",'" + districts.getDistrict_name() + "')");
        db.close();
        return 1;
    }


    /*
    * Category Table GENDER
    * */
    public int createGender(Gender gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_GENDER + " (" + GENDER_ID + "," + GENDER_NAME + ") VALUES (" + gender.getId() + ",'" + gender.getGender_name() + "')");
        db.close();
        return 1;
    }

    /*
    * Category Table UNIVERSITY
    * */
    public int createUniversity(University university) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_UNIVERSITY + " (" + UNIVER_ID + "," + UNIVER_NAME + ") VALUES (" + university.getId() + ",'" + university.getUnivercity_name() + "')");
        db.close();
        return 1;
    }

    /*
    * Category Table COLLEGES
    * */
    public int createColleges(Colleges colleges) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_COLLEGE_TYPE + " (" + COLLEGE_ID + "," + COLLEGE_NAME + ") VALUES (" + colleges.getId() + ",'" + colleges.getColleges_name() + "')");
        db.close();
        return 1;
    }

    /*
   * BankCategories Table COLLEGES
   * */
    public int createBankCategories(BankCategories bankCategories) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_BANK + " (" + BANK_ID + "," + BANK_NAME + ") VALUES (" + bankCategories.getId() + ",'" + bankCategories.getBank_name() + "')");
        db.close();
        return 1;
    }

    /*
      * CATEGORY Table COLLEGES
      * */
    public int createCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_CATEGORY + " (" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES (" + category.getId() + ",'" + category.getCategory_name() + "')");
        return 1;
    }

    public int updateMentor(Mentor mentor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();


        initialValues.put(KEY_MENTOR_ID, mentor.getMentor_id());
        initialValues.put(KEY_MENTOR_NAME, mentor.getMentor_name());
        initialValues.put(KEY_INDUSTRY, mentor.getIndustry());
        initialValues.put(KEY_SPECIALIZATION, mentor.getSpecialization());
        initialValues.put(KEY_EXPERIENCE, mentor.getExperience());
        initialValues.put(KEY_LAST_USE, mentor.getLast_use());
        initialValues.put(KEY_PROFICIENCY_LEVEL, mentor.getProficiency_level());
        initialValues.put(KEY_QUALIFICATION, mentor.getQualification());
        initialValues.put(KEY_ACHIVEMENTS, mentor.getAchivements());
        initialValues.put(KEY_RESUME, mentor.getResume());
        initialValues.put(KEY_STATUS, mentor.getStatus());


        return db.update(TABLE_MENTOR, initialValues, KEY_MENTOR_ID + " = ?",
                new String[]{String.valueOf(mentor.getMentor_id())});
    }

    public Users getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USER;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            Users user = new Users();
            user.setUser_id(c.getInt(c.getColumnIndex(KEY_USER_ID)));
            user.setUsername(c.getString(c.getColumnIndex(KEY_EMAIL)));
            user.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
            user.setPassword_hashed(c.getString(c.getColumnIndex(KEY_PASSWORD_HASHED)));
            user.setMobile(c.getString(c.getColumnIndex(KEY_MOBILE)));
            user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
            user.setIs_active(c.getInt(c.getColumnIndex(KEY_ACTIVITY)));
            c.close();
            return user;
        }

        db.close();
        return null;
    }

    public Users getUserName(String uname) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_MOBILE + "='" + uname + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            Users user = new Users();
            user.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            user.setMobile(c.getString(c.getColumnIndex(KEY_MOBILE)));
            user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
            // user.setRemember_me(c.getInt(c.getColumnIndex(KEY_REMEMBER_ME)));
            // user.setFlatnumber(c.getString(c.getColumnIndex(KEY_FLATNUMBER)));

            return user;
        }
        c.close();
        return null;
    }

    // Update User Password //
    public int updateUserPassword(String password, int user_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, password);

        db.close();
        // updating row
        return db.update(TABLE_USER, values, KEY_USER_ID + " = ?",
                new String[]{String.valueOf(user_id)});

    }

    public Mentor getMentorByMentorID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_MENTOR + " where " + KEY_MENTOR_ID + " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);
        Mentor mentor = new Mentor();
        if (c.moveToFirst()) {
            mentor.setMentor_id(c.getInt(c.getColumnIndex(KEY_MENTOR_ID)));
            mentor.setMentor_name(c.getString(c.getColumnIndex(KEY_MENTOR_NAME)));
            mentor.setIndustry(c.getString(c.getColumnIndex(KEY_INDUSTRY)));
            mentor.setSpecialization(c.getString(c.getColumnIndex(KEY_SPECIALIZATION)));
            mentor.setExperience(c.getString(c.getColumnIndex(KEY_EXPERIENCE)));
            mentor.setLast_use(c.getString(c.getColumnIndex(KEY_LAST_USE)));
            mentor.setProficiency_level(c.getString(c.getColumnIndex(KEY_PROFICIENCY_LEVEL)));
            mentor.setQualification(c.getString(c.getColumnIndex(KEY_QUALIFICATION)));
            mentor.setAchivements(c.getString(c.getColumnIndex(KEY_ACHIVEMENTS)));
            mentor.setResume(c.getString(c.getColumnIndex(KEY_RESUME)));
            mentor.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));

            return mentor;
        }
        c.close();
        db.close();
        return null;
    }

    public List<Mentor> getAllMentor() {
        List<Mentor> allMentor = new ArrayList<Mentor>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MENTOR + " ORDER BY " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Mentor mentor = new Mentor();

                mentor.setMentor_id(c.getInt(c.getColumnIndex(KEY_MENTOR_ID)));
                mentor.setMentor_name(c.getString(c.getColumnIndex(KEY_MENTOR_NAME)));
                mentor.setIndustry(c.getString(c.getColumnIndex(KEY_INDUSTRY)));
                mentor.setSpecialization(c.getString(c.getColumnIndex(KEY_SPECIALIZATION)));
                mentor.setExperience(c.getString(c.getColumnIndex(KEY_EXPERIENCE)));
                mentor.setLast_use(c.getString(c.getColumnIndex(KEY_LAST_USE)));
                mentor.setProficiency_level(c.getString(c.getColumnIndex(KEY_PROFICIENCY_LEVEL)));
                mentor.setQualification(c.getString(c.getColumnIndex(KEY_QUALIFICATION)));
                mentor.setAchivements(c.getString(c.getColumnIndex(KEY_ACHIVEMENTS)));
                mentor.setResume(c.getString(c.getColumnIndex(KEY_RESUME)));
                mentor.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));
                allMentor.add(mentor);

            } while (c.moveToNext());
        }
        c.close();
        db.close();
        // return contact list
        return allMentor;
    }

    public int getMentorCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MENTOR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getPrefixesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PREFIXES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        db.close();
        return count;
    }

    public List<Prefixes> getAllPrefixes() {
        List<Prefixes> allprefixes = new ArrayList<Prefixes>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PREFIXES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Prefixes prefixes = new Prefixes();

                prefixes.setId(c.getInt(c.getColumnIndex(PREFIX_ID)));
                prefixes.setPrefixes_name(c.getString(c.getColumnIndex(PREFIX_NAME)));

                allprefixes.add(prefixes);

            } while (c.moveToNext());
        }
        c.close();
        // return contact list
        db.close();
        return allprefixes;
    }

    public List<Category> getAllCategory() {
        List<Category> allCategory = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Category category = new Category();

                category.setId(c.getInt(c.getColumnIndex(CATEGORY_ID)));
                category.setCategory_name(c.getString(c.getColumnIndex(CATEGORY_NAME)));

                allCategory.add(category);

            } while (c.moveToNext());
        }
        c.close();
        // return contact list
        db.close();
        return allCategory;
    }


    public List<Gender> getAllGender() {
        List<Gender> allgender = new ArrayList<Gender>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GENDER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Gender gender = new Gender();

                gender.setId(c.getInt(c.getColumnIndex(GENDER_ID)));
                gender.setGender_name(c.getString(c.getColumnIndex(GENDER_NAME)));

                allgender.add(gender);

            } while (c.moveToNext());
        }
        c.close();
        // return gender list
        db.close();
        return allgender;
    }

    public List<Districts> getAllDistrict() {
        List<Districts> allDistricts = new ArrayList<Districts>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DISTRICTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Districts districts = new Districts();

                districts.setId(c.getInt(c.getColumnIndex(DIST_ID)));
                districts.setDistrict_name(c.getString(c.getColumnIndex(DIST_NAME)));

                allDistricts.add(districts);

            } while (c.moveToNext());
        }
        c.close();
        // return gender list
        db.close();
        return allDistricts;
    }

    public List<BankCategories> getAllBank() {
        List<BankCategories> allIndustries = new ArrayList<BankCategories>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BANK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                BankCategories industries = new BankCategories();

                industries.setId(c.getInt(c.getColumnIndex(BANK_ID)));
                industries.setBank_name(c.getString(c.getColumnIndex(BANK_NAME)));

                allIndustries.add(industries);

            } while (c.moveToNext());
        }
        c.close();
        // return gender list
        db.close();
        return allIndustries;
    }

    public List<Industries> getAllIndustries() {
        List<Industries> allIndustries = new ArrayList<Industries>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INDUSTRIES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Industries industries = new Industries();

                industries.setId(c.getInt(c.getColumnIndex(INDUS_ID)));
                industries.setIndusties_name(c.getString(c.getColumnIndex(INDUS_NAME)));

                allIndustries.add(industries);

            } while (c.moveToNext());
        }
        c.close();
        // return gender list
        db.close();
        return allIndustries;
    }

    public int getIndustriesIdByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_INDUSTRIES + " where " + INDUS_NAME + " ='" + name + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            return c.getInt(c.getColumnIndex(INDUS_ID));
        }
        c.close();
        db.close();
        return 0;
    }


    public String getSpecilatzationNameByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SPECILIZATION + " where " + SPEC_ID + " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            return c.getString(c.getColumnIndex(SPEC_NAME));
        }
        c.close();
        db.close();
        return null;
    }

    public int getSpecilatzationIdByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SPECILIZATION + " where " + SPEC_NAME + " ='" + name + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            return c.getInt(c.getColumnIndex(SPEC_ID));
        }
        c.close();
        db.close();
        return 0;
    }

    public List<Specilazation> getSpecilazationByIndustriesId(int id) {
        List<Specilazation> specilazations = new ArrayList<Specilazation>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SPECILIZATION + " WHERE " + SPEC_IND_ID + " = " + id + " ORDER BY " + SPEC_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Specilazation specilazation = new Specilazation();
                specilazation.setId(cursor.getInt(cursor.getColumnIndex(SPEC_ID)));
                specilazation.setIndust_id(cursor.getInt(cursor.getColumnIndex(SPEC_IND_ID)));
                specilazation.setSpecilazation_name(cursor.getString(cursor.getColumnIndex(SPEC_NAME)));

                specilazations.add(specilazation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return District list
        return specilazations;
    }

    public List<University> getAllUnivercity() {
        List<University> universities = new ArrayList<University>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_UNIVERSITY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                University university = new University();
                university.setId(cursor.getInt(cursor.getColumnIndex(UNIVER_ID)));
                university.setUnivercity_name(cursor.getString(cursor.getColumnIndex(UNIVER_NAME)));

                universities.add(university);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return District list
        return universities;
    }

    public List<Colleges> getAllColleges() {
        List<Colleges> colleges = new ArrayList<Colleges>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COLLEGE_TYPE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Colleges colleges1 = new Colleges();
                colleges1.setId(cursor.getInt(cursor.getColumnIndex(COLLEGE_ID)));
                colleges1.setColleges_name(cursor.getString(cursor.getColumnIndex(COLLEGE_NAME)));

                colleges.add(colleges1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return District list
        return colleges;
    }

    // Get Categories Count //
    public int getSubCategoriesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SPECILIZATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getDistrictCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DISTRICTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public int getCategorycount() {
        String countQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        db.close();
        return count;
    }

    public int getGendercount() {
        String countQuery = "SELECT  * FROM " + TABLE_GENDER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        db.close();
        return count;
    }

    public int getDistrictcount() {
        String countQuery = "SELECT  * FROM " + TABLE_DISTRICTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        db.close();
        return count;
    }
}