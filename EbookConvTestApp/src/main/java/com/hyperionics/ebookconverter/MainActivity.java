package com.hyperionics.ebookconverter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.adobe.dp.fb2.convert.FB2Converter;
import com.adobe.dp.office.conv.DOCXConverter;
import com.adobe.dp.office.conv.RTFConverter;
import com.ebookconvlibrary.MOBIConverter;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private static final int CONVERT_EBOOK_REQUEST = 200;
    private String lastPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lastPath = Environment.getExternalStorageDirectory().getPath();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GenericFileDialog.class);
                intent.putExtra(GenericFileDialog.START_PATH, lastPath);
                intent.putExtra(GenericFileDialog.SELECTION_MODE, GenericFileDialog.MODE_OPEN);
                intent.putExtra(GenericFileDialog.SET_TITLE_TEXT, getString(R.string.sel_eb_file));
                intent.putExtra(GenericFileDialog.FORMAT_FILTER, new String[] {
                        "mobi", "prc", "azw", "azw3", "kf8", "fb2", "fb2.zip", "docx", "rtf"
                });
                startActivityForResult(intent, CONVERT_EBOOK_REQUEST);
            }
        });

        checkPermissions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED || data == null)
            return;

        if (requestCode == CONVERT_EBOOK_REQUEST) {
            String inName = data.getStringExtra(GenericFileDialog.RESULT_PATH);
            if (inName != null) {
                Lt.d("Selected: " + inName);
                lastPath = new File(inName).getParent();
                String outName = inName + ".epub";

                int msg = R.string.conv_success;
                try {
                    if (isMobi(inName)) {
                        MOBIConverter.convert(inName, outName);
                    } else if (isFb2(inName)) {
                        FB2Converter.convert(inName, outName);
                    } else if (isDocx(inName)) {
                        DOCXConverter.convert(inName, outName);
                    } else if (isRtf(inName)) {
                        RTFConverter.convert(inName, outName);
                    } else {
                        msg = R.string.unknown_file;
                    }
                } catch (Exception e) {
                    msg = R.string.conv_error;
                }
                Snackbar.make(findViewById(R.id.fab), msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }

    private boolean isRtf(String fileName) {
        if (fileName == null) {
            return false;
        }
        String lowerCaseName = fileName.toLowerCase();
        return lowerCaseName.endsWith(".rtf");
    }

    private boolean isDocx(String fileName) {
        if (fileName == null) {
            return false;
        }
        String lowerCaseName = fileName.toLowerCase();
        return lowerCaseName.endsWith(".docx");
    }

    private boolean isMobi(String fileName) {
        int n = fileName.lastIndexOf('.');
        if (n < 0)
            return false;
        String ext = fileName.substring(n).toLowerCase();
        return ".mobi".equals(ext) || ".prc".equals(ext) ||
                ".azw".equals(ext) || ".azw3".equals(ext) || ".kf8".equals(ext);
    }

    private boolean isFb2(String fileName) {
        int n = fileName.lastIndexOf('.');
        if (n < 0)
            return false;
        String ext = fileName.substring(n).toLowerCase();
        if (".fb2".equals(ext))
            return true;
        if (".zip".equals(ext)) {
            String name2 = fileName.substring(0, n);
            n = name2.lastIndexOf('.');
            if (n < 0)
                return false;
            ext = name2.substring(n).toLowerCase();
            return ".fb2".equals(ext);
        }
        return false;
    }

    private void checkPermissions() {
        int prm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (prm == PackageManager.PERMISSION_GRANTED)
            return;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.storage_perm_prompt);
        builder.setNeutralButton(R.string.next_arrow, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                dialog.dismiss();
            }
        });
        final Dialog dlg = builder.create();
        dlg.setCancelable(false);
        dlg.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.no_perm_exit);
                builder.setNeutralButton(R.string.close_app, null);
                Dialog dlg = builder.create();
                dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });
                dlg.show();
            }
        }
    }

}
