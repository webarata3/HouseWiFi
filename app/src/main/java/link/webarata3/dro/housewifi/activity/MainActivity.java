package link.webarata3.dro.housewifi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import link.webarata3.dro.housewifi.R;

public class MainActivity extends AppCompatActivity {
    public static final int REGISTER_SSID_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addFab = findViewById(R.id.addFab);
        addFab.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, REGISTER_SSID_CODE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //SecondActivityから戻ってきた場合
            case (REGISTER_SSID_CODE):
                if (resultCode == RESULT_OK) {
                    Snackbar.make(findViewById(android.R.id.content), "登録しました。", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
