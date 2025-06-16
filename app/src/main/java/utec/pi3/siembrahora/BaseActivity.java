package utec.pi3.siembrahora;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

// All other activities will extend this instead of AppCompatActivity
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        // This applies the selected locale to the activity's context
        super.attachBaseContext(LocaleManager.updateBaseContext(newBase));
    }
}