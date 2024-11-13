package ro.pub.cs.systems.eim.Colocviu1_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Colocviu1_2MainActivity extends AppCompatActivity {

    private static final String TAG = "Colocviu1_2MainActivity";
    private EditText nextTermEditText;
    private TextView allTermsTextView;
    private Button addButton;
    private Button computeButton;

    private int lastComputedSum = 0;
    private String lastTerms = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_2_main);

        nextTermEditText = findViewById(R.id.next_term);
        allTermsTextView = findViewById(R.id.all_terms);
        addButton = findViewById(R.id.add_button);
        computeButton = findViewById(R.id.compute_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nextTerm = nextTermEditText.getText().toString();
                if (!TextUtils.isEmpty(nextTerm) && TextUtils.isDigitsOnly(nextTerm)) {
                    String currentTerms = allTermsTextView.getText().toString();
                    if (TextUtils.isEmpty(currentTerms)) {
                        allTermsTextView.setText(nextTerm);
                    } else {
                        allTermsTextView.setText(currentTerms + " + " + nextTerm);
                    }
                    nextTermEditText.setText("");
                }
            }
        });

        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentTerms = allTermsTextView.getText().toString();
                if (currentTerms.equals(lastTerms)) {
                    Toast.makeText(Colocviu1_2MainActivity.this, "Result: " + lastComputedSum, Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Colocviu1_2MainActivity.this, Colocviu1_2SecondaryActivity.class);
                    intent.putExtra("all_terms", currentTerms);
                    startActivityForResult(intent, 1);
                }
            }
        });

        if (savedInstanceState != null) {
            lastComputedSum = savedInstanceState.getInt("lastComputedSum", 0);
            lastTerms = savedInstanceState.getString("lastTerms", "");
            allTermsTextView.setText(savedInstanceState.getString("allTermsTextView", ""));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            lastComputedSum = data.getIntExtra("result", 0);
            lastTerms = allTermsTextView.getText().toString();
            Log.d(TAG, "Computed sum: " + lastComputedSum);
            Toast.makeText(this, "Result: " + lastComputedSum, Toast.LENGTH_LONG).show();

            if (lastComputedSum > 10) {
                Intent serviceIntent = new Intent(this, Colocviu1_2Service.class);
                serviceIntent.putExtra("sum", lastComputedSum);
                startService(serviceIntent);
                Log.d(TAG, "Service started with sum: " + lastComputedSum);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("lastComputedSum", lastComputedSum);
        outState.putString("lastTerms", lastTerms);
        outState.putString("allTermsTextView", allTermsTextView.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity destroyed, stopping service");
        stopService(new Intent(this, Colocviu1_2Service.class));
    }
}