package com.example.edutools_011.permisossistema;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class Main2Activity extends AppCompatActivity {

    RelativeLayout l;
    Snackbar snak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        l = findViewById(R.id.milayout);
        String mensaje = "Faltan otorgar algunos permisos.";
        snak = Snackbar.make(l,mensaje,Snackbar.LENGTH_INDEFINITE);
        snak.setAction(android.R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Main2Activity.this,new String[]{
                        CAMERA,
                        ACCESS_COARSE_LOCATION,
                        ACCESS_FINE_LOCATION
                },100);
            }
        });
        if(verificarPermisos())
        {
            iniciarApp();
        }
        else
        {
            solicitarPermisos();
        }
    }

    private void solicitarPermisos() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,CAMERA) ||
            ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,ACCESS_COARSE_LOCATION) ||
            ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,ACCESS_FINE_LOCATION))
        {
            snak.show();
        }
        else
        {
            ActivityCompat.requestPermissions(Main2Activity.this,new String[]{
                    CAMERA,
                    ACCESS_COARSE_LOCATION,
                    ACCESS_FINE_LOCATION
            },100);
        }
    }

    private boolean verificarPermisos() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return true;
        }
        if(ContextCompat.checkSelfPermission(Main2Activity.this,CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(Main2Activity.this,ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(Main2Activity.this,ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        return false;
    }

    private void iniciarApp() {
        Context c = getApplicationContext();
        String m = "La aplicacion ya tiene todos los permisos necesarios para iniciar.";
        Toast.makeText(c,m,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                grantResults[2] == PackageManager.PERMISSION_GRANTED)
            {
               iniciarApp();
            }
            else
            {
                Context c = getApplicationContext();
                String mensaje = "No has otorgado todos los permisos";
                Toast.makeText(c,mensaje,Toast.LENGTH_LONG).show();
                snak.show();
            }
        }
    }
}
