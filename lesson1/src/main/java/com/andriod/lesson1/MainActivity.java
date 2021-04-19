package com.andriod.lesson1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PopupMenu.OnMenuItemClickListener, SearchView.OnQueryTextListener {

    private DrawerLayout drawer;
    private View mainView;
    private final int SEARCH_ID = View.generateViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);

        initDrawer(toolbar);
        initFab();

        mainView = findViewById(R.id.main_view);
        mainView.setOnLongClickListener(v -> {
            MainActivity.this.registerForContextMenu(v);
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        MenuItem item = menu.add(0, SEARCH_ID, 1, "Search");
        item.setIcon(android.R.drawable.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setActionView(new androidx.appcompat.widget.SearchView(this));

        ((SearchView) item.getActionView()).setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onMenuItemClick(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onMenuItemClick(item);
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(MainActivity.this, v);
            getMenuInflater().inflate(R.menu.drawer_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(this);
            menu.show();
        });
    }

    private void initDrawer(Toolbar toolbar) {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.drawer_menu_about:
                Toast.makeText(this, "You pressed: ABOUT", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_menu_settings:
                Toast.makeText(this, "You pressed: SETTINGS", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.drawer_menu_about)
            Snackbar.make(mainView, "You pressed: ABOUT", Snackbar.LENGTH_SHORT).show();
        else if (id == R.id.drawer_menu_settings)
            Snackbar.make(mainView, "You pressed: SETTINGS", Snackbar.LENGTH_SHORT).show();
        else if (id == SEARCH_ID) {
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, String.format("You are searching for: %s", query), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(this, String.format("You typed: %s", newText), Toast.LENGTH_SHORT).show();
        return true;
    }
}