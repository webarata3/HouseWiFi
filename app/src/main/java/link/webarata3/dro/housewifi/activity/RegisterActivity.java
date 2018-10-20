package link.webarata3.dro.housewifi.activity;

import android.os.Bundle;

import java.util.Objects;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import link.webarata3.dro.housewifi.R;
import link.webarata3.dro.housewifi.fragment.RegisterActivityFragment;

public class RegisterActivity extends AppCompatActivity
        implements RegisterActivityFragment.OnRegisterFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onClickRegisterButton() {

    }
}
