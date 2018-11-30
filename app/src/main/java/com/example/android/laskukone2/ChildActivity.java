package com.example.android.laskukone2;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.laskukone2.data.CalculationContract;
import com.example.android.laskukone2.data.CalculationDbHelper;
import com.example.android.laskukone2.data.TestUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ChildActivity extends AppCompatActivity  implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private  CalculationAdapter mAdapter;
    private RecyclerView mNumbersList;
    private static final String TAG = "Child";
    private SQLiteDatabase mDb;
    private static final int SQLITE_SEARCH_LOADER = 22;
    private static final String QUERY_EXTRA = "query";
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){


            //ArrayList<String> enteredList = intentThatStartedThisActivity.getStringArrayListExtra (Intent.EXTRA_TEXT);

            mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mNumbersList.setLayoutManager(layoutManager);
            mNumbersList.setHasFixedSize(true);


            CalculationDbHelper dbHelper = new CalculationDbHelper(this);
            mDb = dbHelper.getWritableDatabase();
            getSupportLoaderManager().initLoader(SQLITE_SEARCH_LOADER,null,  this);
            getAllCalculations();



            mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void getAllCalculations(){

        Bundle queryBundle = new Bundle();
        queryBundle.putString(QUERY_EXTRA,"getAllCalculations");

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Cursor> queryLoader = loaderManager.getLoader(SQLITE_SEARCH_LOADER);

        if (queryLoader == null) {
            loaderManager.initLoader(SQLITE_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(SQLITE_SEARCH_LOADER, queryBundle, this);
        }


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable final Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(this){



            @Override
            public void onStartLoading(){
                super.onStartLoading();


                if(bundle==null){
                    return;
                }
                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }


            @Override
            public Cursor loadInBackground() {

                String QueryString = bundle.getString(QUERY_EXTRA);
                if(QueryString==null || TextUtils.isEmpty(QueryString) || QueryString!="getAllCalculations"){
                    return null;}

                try {
                    return mDb.query(
                            CalculationContract.CalculationEntry.TABLE_NAME,
                            null,
                            null,
                            null,
                            null,
                            null,
                            CalculationContract.CalculationEntry._ID

                    );
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    return null;
                }
            }

        };
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        mAdapter = new CalculationAdapter(this, cursor);
        mNumbersList.setAdapter(mAdapter);
        mLoadingIndicator.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
