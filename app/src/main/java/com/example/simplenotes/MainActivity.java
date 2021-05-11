package com.example.simplenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.simplenotes.domain.Note;
import com.example.simplenotes.ui.NoteDetailsFragment;
import com.example.simplenotes.ui.NotesFragment;
import com.example.simplenotes.ui.Publisher;
import com.example.simplenotes.ui.PublisherHolder;
import com.example.simplenotes.ui.about.AboutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NotesFragment.OnNoteClicked, PublisherHolder {

    private final Publisher publisher = new Publisher();
    private boolean isLandscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        isLandscape = getResources().getBoolean(R.bool.isLandscape);

        if (!isLandscape) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            Fragment fragment = fragmentManager.findFragmentById(R.id.container);

            if (fragment == null) {

                fragmentManager.beginTransaction()
                        .replace(R.id.container, new NotesFragment())
                        .commit();
            }
        }
    }

    private void initView() {
        initDrawer();
    }

    private void initDrawer() {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (navigateFragment(id)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (navigateFragment(id)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean navigateFragment(int id) {
        if (id == R.id.nav_about) {
            addFragment(new AboutFragment(), AboutFragment.TAG);
            return true;
        }
        return false;
    }

    private void addFragment(Fragment fragment, String tag) {
        Fragment addedFragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (addedFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onNoteClicked(Note note) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (isLandscape) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_note_details, NoteDetailsFragment.newInstance(note))
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, NoteDetailsFragment.newInstance(note))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }
}