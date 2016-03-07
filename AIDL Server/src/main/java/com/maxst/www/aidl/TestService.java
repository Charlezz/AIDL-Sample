package com.maxst.www.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.maxst.www.test.IRemoteService;

public class TestService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {

        @Override
        public String getMessage() throws RemoteException {
            return "Hello World";
        }
    };


}
