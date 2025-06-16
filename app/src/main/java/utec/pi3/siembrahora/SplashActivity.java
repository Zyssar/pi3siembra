package utec.pi3.siembrahora;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

// Make sure it extends BaseActivity
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The locale is already set by BaseActivity. We just need to navigate.
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean hasSeenWelcome = prefs.getBoolean("has_seen_welcome_screen_v2", false);

        Intent intent;
        if (hasSeenWelcome) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, WelcomeActivity.class);
        }
        startActivity(intent);
        finish();
    }
}