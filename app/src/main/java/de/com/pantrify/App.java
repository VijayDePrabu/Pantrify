package de.com.pantrify;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

public class App extends MultiDexApplication {


@Override
protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(base);
}

@Override
public void onCreate() {
    super.onCreate();


}
}