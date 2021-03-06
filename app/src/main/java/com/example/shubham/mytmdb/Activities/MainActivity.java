package com.example.shubham.mytmdb.Activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shubham.mytmdb.Fragments.MainFragment;
import com.example.shubham.mytmdb.Fragments.SearchFragment;
import com.example.shubham.mytmdb.R;
import com.example.shubham.mytmdb.Retrofit.ApiClient.ApiClient;
import com.example.shubham.mytmdb.Retrofit.ResponseModels.MovieModel;
import com.example.shubham.mytmdb.Fragments.TVFragment;
import com.example.shubham.mytmdb.Retrofit.ResponseModels.SearchClass;
import com.example.shubham.mytmdb.Retrofit.ResponseModels.TVClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shubham.mytmdb.Activities.SearchActvity.API_key;
import static com.example.shubham.mytmdb.Activities.SearchActvity.LANGUGAGE;
import static com.example.shubham.mytmdb.Activities.SearchActvity.PAGE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MainFragment.MovieSelectedCallback,TVFragment.TVShowSelectedCallback {

    List<SearchClass.ResultsBean> searchClassList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setFragment(new MainFragment());

    }

    public void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainfragment,fragment)
                .setTransition(FragmentTransaction.TRANSIT_EXIT_MASK).commit();

    }

//    private void setFragment(TVFragment tvFragment){
//        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.mainfragment,tvFragment).commit();
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchActvity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchClassList = new ArrayList<>();

                if (newText.length() >= 3) {

                    final retrofit2.Call<SearchClass> searchClassCall = ApiClient.getInstance().getTmdbApiApi().getsearchall(API_key, LANGUGAGE, newText, PAGE, false);
                    searchClassCall.enqueue(new Callback<SearchClass>() {
                        @Override
                        public void onResponse(retrofit2.Call<SearchClass> call, Response<SearchClass> response) {
                            if (response.isSuccessful()) {


                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<SearchClass> call, Throwable t) {

                        }
                    });
                } else {

                }
                return true;
            }
        });
        return true;
    }

            @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            setFragment(new SearchFragment());
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.movie) {

            setFragment(new MainFragment());

        } else if (id == R.id.shows) {
            setFragment(new TVFragment());

        } else if (id == R.id.about) {

        }

         else if (id == R.id.fav) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMovieSelected(MovieModel.ResultsBean movie) {
        Intent intent = new Intent(this,MovieDescriptionActivity.class);
        int a = movie.getId();
        String name = movie.getTitle().toString();
        intent.putExtra("movieid",a);
        intent.putExtra("moviename",name);
        intent.putExtra("movieposter",movie.getPoster_path());
        intent.putExtra("moviebackdrop",movie.getBackdrop_path());
        intent.putExtra("description",movie.getOverview());
        startActivity(intent);
    }

    @Override
    public void onTvShowSelected(TVClass.Resultsbean show) {
        Intent intent = new Intent(this,TvDescriptionActivity.class);
        int a = show.getId();
        String name = show.getOriginal_name().toString();
        intent.putExtra("movieid",a);
        intent.putExtra("moviename",name);
        intent.putExtra("movieposter",show.getPoster_path());
        intent.putExtra("moviebackdrop",show.getBackdrop_path());
        intent.putExtra("description",show.getOverview());
        startActivity(intent);

    }
}











