package utec.pi3.siembrahora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import utec.pi3.siembrahora.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends BaseActivity {

    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // When the user selects a language, recreate the activity to show the text in that language
        binding.languageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = findViewById(checkedId);
                if (checkedRadioButton != null && checkedRadioButton.isPressed()) { // Check if the change was from a user press
                    String languageCode = checkedRadioButton.getTag().toString();
                    LocaleManager.setLocale(WelcomeActivity.this, languageCode);

                    // IMPORTANT: Temporarily disable the listener to prevent the loop
                    group.setOnCheckedChangeListener(null);

                    recreate(); // Reload the activity with the new language
                }
            }
        });

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The language is already saved by the listener above
                markWelcomeScreenAsSeen();

                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                // Clear the activity stack so the user can't go back to the Welcome/Splash screens
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Finish this activity
            }
        });
    }

    private void markWelcomeScreenAsSeen() {
        getSharedPreferences("app_prefs", MODE_PRIVATE)
                .edit()
                .putBoolean("has_seen_welcome_screen_v2", true)
                .apply();
    }

    // This is a new helper method to set the currently selected language on the RadioGroup
    @Override
    protected void onResume() {
        super.onResume();
        String currentLang = LocaleManager.getLanguage(this);
        if ("ay".equals(currentLang)) binding.radioAy.setChecked(true);
        else if ("cni".equals(currentLang)) binding.radioCni.setChecked(true);
        else if ("de".equals(currentLang)) binding.radioDe.setChecked(true);
        else if ("en".equals(currentLang)) binding.radioEn.setChecked(true);
        else if ("pt".equals(currentLang)) binding.radioPt.setChecked(true);
        else if ("qu".equals(currentLang)) binding.radioQu.setChecked(true);
        else binding.radioEs.setChecked(true); // Default to Spanish
    }
}