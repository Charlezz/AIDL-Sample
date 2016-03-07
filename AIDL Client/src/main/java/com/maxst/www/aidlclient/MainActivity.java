package com.maxst.www.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.maxst.www.test.IRemoteService;


public class MainActivity extends AppCompatActivity {

    private Button btn;
    private boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String str = mService.getMessage();
                    Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBinding();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopBinding();
    }

    private void stopBinding() {
        if (isBound) {
            unbindService(mConnection);
        }
    }


    private void startBinding() {
        if (!isBound) {
            Intent intent = new Intent(IRemoteService.class.getName());
            intent.setPackage("com.maxst.www.aidl");
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private IRemoteService mService;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            mService = IRemoteService.Stub.asInterface(binder);
            isBound = true;
            Toast.makeText(MainActivity.this, "bound", Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
            isBound = false;
            Toast.makeText(MainActivity.this, "unBound", Toast.LENGTH_SHORT).show();
        }
    };


}
