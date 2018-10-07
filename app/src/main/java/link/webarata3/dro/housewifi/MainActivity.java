package link.webarata3.dro.housewifi;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0;

    private TextView tv;
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.ssid);

        button = findViewById(R.id.button);
        button.setEnabled(false);
        button.setOnClickListener(view -> {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            Objects.requireNonNull(wifiManager);
            wifiManager.startScan();

            List<ScanResult> scanResults = wifiManager.getScanResults();
            String str = "";
            for (ScanResult scanResult : scanResults) {
                double hinshitsu = 2.0 * (scanResult.level + 100.0);
                hinshitsu = hinshitsu >= 100.0 ? 100.0 : hinshitsu;
                str = str + scanResult.SSID + " " + scanResult.level + "\n" + hinshitsu + "\n";
            }

            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            str = str + "===================\n";
            str = str + wifiInfo.getSSID() + " " + wifiInfo.getIpAddress() + " " + " " + wifiInfo.getLinkSpeed();
            tv.setText(str);
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(view -> {
            checkPermission();
        });

        findViewById(R.id.button3).setOnClickListener(view -> {
            Intent localIntent = new Intent(MainWidget.ACTION_CHANGE_LIST);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        });

        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean firstAccesss = preferences.getBoolean("firstAccess", true);

        if (firstAccesss) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstAccess", false);
            editor.apply();

            checkPermission();
        }
    }

    private void checkPermission() {
        // versionは6.0以上なので、バージョンチェックは不要
        // if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        // 既に許可されているか確認
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 許可されていなかったらリクエストする
            // ダイアログが表示される
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
        } else {
            button.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            button.setEnabled(true);
        } else {
            // 許可されなかった場合
            // 何らかの対処が必要
        }
    }
}
