package br.com.digitaldreams.popularmovies2.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import br.com.digitaldreams.popularmovies2.R;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add a button to the header list.=
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onOptionsItemSelected(item)) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
