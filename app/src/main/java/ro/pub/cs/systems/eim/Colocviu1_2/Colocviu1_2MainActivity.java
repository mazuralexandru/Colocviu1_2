package ro.pub.cs.systems.eim.Colocviu1_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Colocviu1_2MainActivity extends AppCompatActivity {

    private EditText nextTermEditText;
    private TextView allTermsTextView;
    private Button addButton;
    private Button computeButton;

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
                Intent intent = new Intent(Colocviu1_2MainActivity.this, Colocviu1_2SecondaryActivity.class);
                intent.putExtra("all_terms", allTermsTextView.getText().toString());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            int result = data.getIntExtra("result", 0);
            allTermsTextView.setText("Result: " + result);
        }
    }
}