package br.com.digitaldreams.popularmovies2.UI;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import br.com.digitaldreams.popularmovies2.R;

public class SettingsActivity extends PreferenceActivity {

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

        /*
            public void addPreferencesFromResource (int preferencesResId)
                Inflates the given XML resource and adds the preference hierarchy to the
                current preference hierarchy.

            Parameters
                preferencesResId : The XML resource ID to inflate.
        */
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}
