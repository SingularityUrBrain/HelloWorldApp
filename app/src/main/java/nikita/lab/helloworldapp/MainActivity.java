package nikita.lab.helloworldapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_READ_PHONE_STATE = 7;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        permissionManager = new PermissionManager(this);
        Button showIdButton = findViewById(R.id.display_id_button);

        showIdButton.setOnClickListener(this);
    }


    public void displayVersionName(View view) {
        Toast versionToast = Toast.makeText(this, "version name: " + BuildConfig.VERSION_NAME, Toast.LENGTH_LONG);
        versionToast.show();
    }

    public void displayId() {
        Toast idToast = Toast.makeText(this, "android id: " + Secure.getString(getContentResolver(), Secure.ANDROID_ID), Toast.LENGTH_LONG);
        idToast.show();
    }

    public void displayToastForSettings(){
        Toast.makeText(this, "Permission Denied, you should change it in the settings", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission was granted. Now you can call your method to open camera, fetch contact or whatever
                    displayId();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }
    private void showIdRational() {
        new AlertDialog.Builder(this).setTitle("Permission Denied").setMessage("Without this permission this app is unable show android ID. Are you sure you want to deny this permission.")
                .setCancelable(false)
                .setNegativeButton("I'M SURE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                        dialog.dismiss();
                    }
                }).show();

    }

    @Override
    public void onClick(View view) {
        permissionManager.checkPermission(this, Manifest.permission.READ_PHONE_STATE, new PermissionManager.PermissionAskListener() {
            @Override
            public void onNeedPermission() {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            }

            @Override
            public void onPermissionPreviouslyDenied() {
                showIdRational();
            }

            @Override
            public void onPermissionPreviouslyDeniedWithNeverAskAgain() {
                displayToastForSettings();
            }

            @Override
            public void onPermissionGranted() {
                displayId();
            }
        });
    }
}
