package com.example.thinkpad.kh10a;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 产生
 * 图片、普通文本、缓存文件
 * 选择保存目录
 * 1. 私有内部存储 FileOutputStream
 * 2. 私有外部存储 sd
 * 3. 公共图片存储区 共享文件
 * 4. 内部或外部缓存目录 cache
 *
 *
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnAddImage;
    private Button btnAddText;
    private Button btnAddCache;
    private Button btnShowText;
    private ListView listView;
    private FileOutputStream fos;
    private static String currentType = "a";
    private Intent intent;
    private ArrayList alist;
    private InputStream is;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

        Intent intent2=getIntent();
        String btn_tag = intent2.getStringExtra("btn_tag");
        if (btn_tag != null) {
            makeFile(currentType,btn_tag);
        }
    }


    private void makeFile(String CurrentType, String btn_tag) {
        if (CurrentType.equals("text")) {
            if (btn_tag.equals("image")) {
                imageStorage(1);
            } else if (btn_tag.equals("inner")) {
                privateInnerStorage(1);
            } else if (btn_tag.equals("sd")) {
                sdStorage(1);
            } else if (btn_tag.equals("cache")) {
                cacheStorage(1);
            } else{Log.d(TAG,"makeFile里 btn_tag啥都没对上");}
        } else if (CurrentType.equals("cache")) {
            if (btn_tag.equals("image")) {
                imageStorage(2);
            } else if (btn_tag.equals("inner")) {
                privateInnerStorage(2);
            } else if (btn_tag.equals("sd")) {
                sdStorage(2);
            } else if (btn_tag.equals("cache")) {
                cacheStorage(2);
            } else{Log.d(TAG,"makeFile里 btn_tag啥都没对上");}
        } else if (CurrentType.equals("image")) {
            if (btn_tag.equals("image")) {
                imageStorage(3);
            } else if (btn_tag.equals("inner")) {
                privateInnerStorage(3);
            } else if (btn_tag.equals("sd")) {
                sdStorage(3);
            } else if (btn_tag.equals("cache")) {
                cacheStorage(3);
            } else{Log.d(TAG,"makeFile里 btn_tag啥都没对上");}
        }

    }

    private void initListener() {
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentType = "image";
                intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        btnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentType="text";
                intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        btnAddCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentType = "cache";
                intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        btnShowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText();
            }
        });


    }

    private void showText() {
        String[] from = {"tag", "name"};
        int[] to = {R.id.tv1, R.id.tv2};
        SimpleAdapter sa=new SimpleAdapter(this, alist,R.layout.list_item,from,to);
        listView.setAdapter(sa);
    }

    private void initView() {
        alist=Constant.getList();
        btnAddImage = (Button) findViewById(R.id.btn_add_image);
        btnAddText = (Button) findViewById(R.id.btn_add_text);
        btnAddCache = (Button) findViewById(R.id.btn_add_cache);
        btnShowText = (Button) findViewById(R.id.btn_show_text);
        listView = (ListView) findViewById(R.id.list_view);
    }

    private void imageStorage(int tag) {
        if (tag == 1) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            Log.d(TAG, "path==" + path);
            String fileName = "普通文本" + Constant.getTextcount();
            Constant.setTextcount(Constant.getTextcount() + 1);
            if (!path.exists()) {
                path.mkdirs();
            }
            File saveFile = new File(path, fileName + ".txt");
            Log.d(TAG, "image666");

            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                Log.d(TAG, "image4");

                fos = new FileOutputStream(saveFile);
                fos.write(("abc").getBytes());
                Map map = new HashMap<String, String>();
                map.put("tag", "公共图片存储区:");
                map.put("name", fileName + ".txt");
                Constant.addToList(map);
                Log.d(TAG, "image5");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (tag == 3) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String fileName = "图片" + Constant.getImagecount();
            Constant.setImagecount(Constant.getImagecount() + 1);
            if (!path.exists()) {
                path.mkdirs();
            }
            File saveFile = new File(path, fileName + ".jpg");
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                is = getResources().openRawResource(R.raw.cat1);
                fos = new FileOutputStream(saveFile);
                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                    if (is != null) is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (tag == 2) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            Log.d(TAG, "path==" + path);
            String fileName = "缓存文件" + Constant.getCachecount();
            Constant.setCachecount(Constant.getCachecount() + 1);
            if (!path.exists()) {
                path.mkdirs();
            }
            File saveFile = new File(path, fileName + ".cache");
            Log.d(TAG, "image666");
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                fos = new FileOutputStream(saveFile);
                fos.write(("abc").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                    if (is != null) is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sdStorage(int tag) {
        if (tag == 1) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);
            File exDir= Environment.getExternalStorageDirectory();
            String fileName="普通文本"+Constant.getTextcount();
            Constant.setTextcount(Constant.getTextcount()+1);
            File saveFile = new File(exDir, fileName + ".txt");
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                fos = new FileOutputStream(saveFile);
                fos.write(("abc").getBytes());
                Map map=new HashMap<String,String>();
                map.put("tag","私有外部存储:");
                map.put("name",fileName+".txt");
                Constant.addToList(map);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (tag == 2) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);

            Log.d(TAG, "sd");
            File exDir= Environment.getExternalStorageDirectory();
            Log.d(TAG, "sdDir==" + exDir);
            String fileName="缓存"+Constant.getCachecount();
            Constant.setCachecount(Constant.getCachecount()+1);
            File saveFile = new File(exDir, fileName + ".cache");
            Log.d(TAG, "sd666");
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                Log.d(TAG, "sd4");
                fos = new FileOutputStream(saveFile);
                fos.write(("abc").getBytes());
                Log.d(TAG, "sd5");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (tag == 3) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);
            File exDir= Environment.getExternalStorageDirectory();
            Log.d(TAG, "path==" + exDir);
            String fileName = "图片" + Constant.getImagecount();
            Constant.setImagecount(Constant.getImagecount() + 1);
            File saveFile = new File(exDir, fileName + ".jpg");
            Log.d(TAG, "image666");
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                is = getResources().openRawResource(R.raw.cat1);
                fos = new FileOutputStream(saveFile);
                Log.d(TAG, "image4");
                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                    if (is != null) is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cacheStorage(int tag) {
        if (tag == 1) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);

            Log.d(TAG, "cache");
            File cacheDir=this.getCacheDir();
            Log.d(TAG, "cacheDir==" + cacheDir);
            String fileName="普通文本"+Constant.getTextcount();
            Constant.setTextcount(Constant.getTextcount()+1);
            File saveFile = new File(cacheDir, fileName + ".txt");
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                fos = new FileOutputStream(saveFile);
                fos.write(("abc").getBytes());
                Map map=new HashMap<String,String>();
                map.put("tag","缓存:");
                map.put("name",fileName+".txt");
                Constant.addToList(map);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (tag == 2) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);

            File cacheDir=this.getCacheDir();
            String fileName="缓存"+Constant.getCachecount();
            Constant.setCachecount(Constant.getCachecount()+1);
            File saveFile = new File(cacheDir, fileName + ".cache");
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                fos = new FileOutputStream(saveFile);
                fos.write(("abc").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (tag == 3) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);
            File cacheDir=this.getCacheDir();
            Log.d(TAG, "path==" + cacheDir);
            String fileName = "图片" + Constant.getImagecount();
            Constant.setImagecount(Constant.getImagecount() + 1);
            File saveFile = new File(cacheDir, fileName + ".jpg");
            Log.d(TAG, "image666");
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                is = getResources().openRawResource(R.raw.cat1);
                fos = new FileOutputStream(saveFile);
                Log.d(TAG, "image4");
                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                    if (is != null) is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void privateInnerStorage(int tag) {
        if (tag == 1) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);

            Log.d(TAG,"私有内部存储");
            File filesDir=this.getFilesDir();
            Log.d(TAG,"filesDir=="+filesDir);
            String fileName="普通文本"+Constant.getTextcount();
            Constant.setTextcount(Constant.getTextcount()+1);
            File saveFile = new File(filesDir, fileName + ".txt");
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                fos = new FileOutputStream(saveFile);
                fos.write(("abc").getBytes());
                Map map=new HashMap<String,String>();
                map.put("tag","私有类型存储:");
                map.put("name",fileName+".txt");
                Constant.addToList(map);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (tag == 2) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);

            Log.d(TAG,"私有内部存储");
            File filesDir=this.getFilesDir();
            Log.d(TAG,"filesDir=="+filesDir);
            String fileName="缓存"+Constant.getCachecount();
            Constant.setCachecount(Constant.getCachecount()+1);
            File saveFile = new File(filesDir, fileName + ".cache");
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                fos = new FileOutputStream(saveFile);
                fos.write(("abc").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (tag == 3) {
            Log.d(TAG, "currentType=="+currentType+"  tag==" + tag);
            File filesDir=this.getFilesDir();
            Log.d(TAG, "path==" + filesDir);
            String fileName = "图片" + Constant.getImagecount();
            Constant.setImagecount(Constant.getImagecount() + 1);
            File saveFile = new File(filesDir, fileName + ".jpg");
            Log.d(TAG, "image666");
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                is = getResources().openRawResource(R.raw.cat1);
                fos = new FileOutputStream(saveFile);
                Log.d(TAG, "image4");
                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                    if (is != null) is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
