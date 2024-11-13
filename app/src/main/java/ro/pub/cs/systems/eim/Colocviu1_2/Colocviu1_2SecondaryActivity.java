package ro.pub.cs.systems.eim.Colocviu1_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Colocviu1_2SecondaryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String allTerms = intent.getStringExtra("all_terms");
        int sum = 0;
        if (allTerms != null && !allTerms.isEmpty()) {
            String[] terms = allTerms.split(" \\+ ");
            for (String term : terms) {
                try {
                    sum += Integer.parseInt(term);
                } catch (NumberFormatException e) {
                    Log.e("Colocviu1_2SecondaryActivity", "Invalid number format: " + term, e);
                }
            }
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", sum);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}