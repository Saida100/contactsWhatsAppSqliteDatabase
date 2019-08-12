package com.example.contactswhatsapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.contactswhatsapp.DBHelper;
import com.example.contactswhatsapp.R;
import com.example.contactswhatsapp.adapter.CustomAdapter;
import com.example.contactswhatsapp.model.Data;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Main2ActivityShowChat extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;
    public static final int SELECT_IMAGE = 1234;
    private int request_codeChooseDocument = 1, FILE_SELECT_CODE = 101;
    public String actualfilepath = "";

    String name, number, photo_path;
    Toolbar toolbar;
    ImageView imageView;
    EditText edtMessage;
    Button btnSend, btnAttach, btnTakePhoto;
    String userId;
    DBHelper dbHelper;
    boolean checkDataInserted;
    List<Data> dataList;
    List<Data> randomDataList;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    CustomAdapter customAdapter;
    String currentTime;
    String fileName;
    String randomSelectedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_show_chat);
        toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.photo);
        linearLayout = findViewById(R.id.linear_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        toolbar.setTitleMarginStart(30);
        dataList = new ArrayList<>();
        randomDataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        dbHelper = new DBHelper(getApplicationContext());
        edtMessage = findViewById(R.id.text);
        btnSend = findViewById(R.id.send);
        btnAttach = findViewById(R.id.attachFile);
        btnTakePhoto = findViewById(R.id.takePhoto);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("id");
        name = bundle.getString("name");
        Log.e("bundleName", name);
        Log.e("bundleUserId", userId);
        number = bundle.getString("number");
        toolbar.setTitle(name);
        setRecyclerView2();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEdtText = edtMessage.getText().toString().trim();
                Log.e("getEditText", getEdtText);
                currentTime = getCurrentTimeUsingDate();
                setDataToDataObject(userId, 0, getEdtText, currentTime, 100);
                setRecyclerView2();
                edtMessage.setText("");
                callAutoAnswer(userId);
                setRecyclerView2();
            }
        });


        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }

            }
        });
        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // start runtime permission
                    Boolean hasPermission = (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED);
                    if (!hasPermission) {
                        Log.e("getPermission", "get permision   ");
                        ActivityCompat.requestPermissions(Main2ActivityShowChat.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, request_codeChooseDocument);
                    } else {
                        Log.e("getPermission", "get permision-- already granted ");
                        showFileChooser();
                    }
                } else {
                    //readfile();
                    showFileChooser();
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermissionWRITE_EXTERNAL_STORAGE(this);
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                Log.e("calledStartActivity", "startActivityForResult");
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "write external granted", Toast.LENGTH_LONG).show();
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //readfile();
                    showFileChooser();

                    Toast.makeText(this, "read external granted", Toast.LENGTH_LONG).show();
                } else {
                    // show a msg to user
                }
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photo_path = getImageUri(getApplicationContext(), photo);
            currentTime = getCurrentTimeUsingDate();
            Log.e("photo_path=", photo_path);
            setDataToDataObject(userId, 1, photo_path, currentTime, 100);
            setRecyclerView2();
        }
        if (requestCode == FILE_SELECT_CODE) {
            if (resultCode == RESULT_OK) {
                selectFile(data);
                Log.e("fileName=", fileName);
                currentTime = getCurrentTimeUsingDate();
                setDataToDataObject(userId, 3, fileName, currentTime, 100);
                setRecyclerView2();
            }
        }


    }

    public String getColunmData(Uri uri, String selection, String[] selectarg) {
        String filepath = "";
        Cursor cursor = null;
        String colunm = "_data";
        String[] projection = {colunm};
        cursor = getContentResolver().query(uri, projection, selection, selectarg, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Log.e("filePath", cursor.getString(cursor.getColumnIndex(colunm)));
            filepath = cursor.getString(cursor.getColumnIndex(colunm));
            fileName = filepath.substring(filepath.lastIndexOf("/") + 1);
            Log.e("fileName", fileName);
        }
        if (cursor != null)
            cursor.close();
        return filepath;
    }


    public String getImageUri(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createBitmap(inImage);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return path;
    }


    public boolean checkPermissionWRITE_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "WRITE_EXTERNAL_STORAGE granted", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }


    public void setRecyclerView2() {
        dataList = dbHelper.getAllData(Integer.parseInt(userId));
        Log.e("sizeDataList", String.valueOf(dataList.size()));
        customAdapter = new CustomAdapter(this, dataList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.scrollToPosition(dataList.size() - 1);
        customAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(customAdapter);
    }

    public String getCurrentTimeUsingDate() {
        Date date = new Date();
        String strDateFormat = "hh:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        Log.e("currentTime", formattedDate);
        return formattedDate;
    }


    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, FILE_SELECT_CODE);
    }


    public void setDataToDataObject(String userId, int data_type, String dataInfo, String currentTime, int senderId) {
        Data data = new Data();
        data.setReciverId(Integer.parseInt(userId));
        data.setData_type(data_type);
        data.setData(dataInfo);
        data.setSendDataTime(currentTime);
        data.setSenderId(senderId);
        checkDataInserted = dbHelper.insertedData(data);
        Log.e("checkDataInserted", String.valueOf("datatype=" + data_type + "  " + checkDataInserted));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void selectFile(Intent data) {
        try {
            String fullerror = "";
            Uri imageuri = data.getData();
            InputStream stream = null;
            String tempID = "", id = "";
            Uri uri = data.getData();
            Log.e("file auth    ", uri.getAuthority());
            //   fullerror = fullerror +"file auth is "+uri.getAuthority();
            fullerror = fullerror + uri.getAuthority();
            if (imageuri.getAuthority().equals("media")) {
                Log.e("imageuri.Media", String.valueOf(imageuri.getAuthority()));
                tempID = imageuri.toString();
                tempID = tempID.substring(tempID.lastIndexOf("/") + 1);
                id = tempID;
                Uri contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String selector = MediaStore.Images.Media._ID + "=?";
                actualfilepath = getColunmData(contenturi, selector, new String[]{id});
            } else if (imageuri.getAuthority().equals("com.android.providers.media.documents")) {
                Log.e("imageuri.Media.Doc", String.valueOf(imageuri.getAuthority()));
                tempID = DocumentsContract.getDocumentId(imageuri);
                String[] split = tempID.split(":");
                String type = split[0];
                id = split[1];
                Uri contenturi = null;
                if (type.equals("image")) {
                    contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if (type.equals("video")) {
                    contenturi = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if (type.equals("audio")) {
                    contenturi = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selector = "_id=?";
                actualfilepath = getColunmData(contenturi, selector, new String[]{id});
            } else if (imageuri.getAuthority().equals("com.android.providers.downloads.documents")) {
                Log.e("imageuri.Downloads", String.valueOf(imageuri.getAuthority()));

                tempID = imageuri.toString();
                tempID = tempID.substring(tempID.lastIndexOf("/") + 1);
                id = tempID;
                Uri contenturi = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                actualfilepath = getColunmData(contenturi, null, null);
            } else if (imageuri.getAuthority().equals("com.android.externalstorage.documents")) {
                tempID = DocumentsContract.getDocumentId(imageuri);
                String[] split = tempID.split(":");
                String type = split[0];
                id = split[1];
                Uri contenturi = null;
                if (type.equals("primary")) {
                    actualfilepath = Environment.getExternalStorageDirectory() + "/" + id;
                    Log.e("actualfilepath",actualfilepath);
                    fileName=actualfilepath.substring(actualfilepath.lastIndexOf("/")+1);
                }
            }

            File myFile = new File(actualfilepath);
            String temppath = uri.getPath();
            Log.e("temppath",temppath);
            if (temppath.contains("//")) {
                temppath = temppath.substring(temppath.indexOf("//") + 1);
            }
            Log.e("temppath is", temppath);
            fullerror = fullerror + "\n" + " file details -  " + actualfilepath + "\n --" + uri.getPath() + "\n--" + temppath;
            if (actualfilepath.equals("") || actualfilepath.equals(" ")) {
                myFile = new File(temppath);
            } else {
                myFile = new File(actualfilepath);
            }

            Log.e(" myfile is ", myFile.getAbsolutePath());
             String fileAbsolutePath=myFile.getAbsolutePath();
             fileName=fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("/")+1);
            Log.e(" fileName", fileName);

            //   readfile(myFile);
        } catch (Exception e) {
            Log.e(" read errro ", e.toString());
        }
    }

    public void callAutoAnswer(String userId) {
        randomDataList = dbHelper.getAllMessages();
        if (dataList.size() > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(randomDataList.size());
            randomSelectedText = randomDataList.get(randomIndex).getData();
            Log.e("randomSelectedText=", randomSelectedText);
            Log.e("randomDataList=", randomDataList.toString());
        } else randomSelectedText = "Salam";

        currentTime = getCurrentTimeUsingDate();
        setDataToDataObject(userId, 2, randomSelectedText, currentTime, 100);

    }



}
