package utec.pi3.siembrahora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

// You need to import the binding class for your layout.
// It's named based on your XML file, e.g., activity_main.xml -> ActivityMainBinding
import utec.pi3.siembrahora.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    // A native method that is implemented by the 'siembrahora' native library,
    // which is packaged with this application.
    public native String stringFromJNI();

    // Used to load the 'siembrahora' library on application startup.
    static {
        System.loadLibrary("siembrahora");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This replaces the Kotlin synthetic binding.
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // We will initialize our telemetry system here.
        // It's a good practice to do this once when the main activity starts.
        NativeBridge.initialize(this.getApplicationContext());

        // Log that the main screen has been viewed.
        NativeBridge.logScreenView("MainActivity");

        // Example of a call to a native method
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, SeedSelectionActivity.class);
            startActivity(intent);
            finish(); // Opcional: si no quieres volver a MainActivity con el botón "atrás"
        }, 500); // delay opcional de 500ms

    }
}