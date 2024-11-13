package ro.pub.cs.systems.eim.Colocviu1_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Colocviu1_2SecondaryActivity extends Activity {

    private static final String TAG = "Colocviu1_2SecondaryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String allTerms = intent.getStringExtra("all_terms");
        int sum = 0;
        if (allTerms != null && !allTerms.isEmpty()) {
            String[] terms = allTerms.split(" \\+ ");
            for (String term : terms) {
                if (term.matches("\\d+")) { // Check if the term is a valid integer
                    try {
                        sum += Integer.parseInt(term);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Invalid number format: " + term, e);
                    }
                } else {
                    Log.e(TAG, "Invalid term: " + term);
                }
            }
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", sum);
        setResult(RESULT_OK, resultIntent);
        Log.d(TAG, "Sum calculated: " + sum);
        finish();
    }
}