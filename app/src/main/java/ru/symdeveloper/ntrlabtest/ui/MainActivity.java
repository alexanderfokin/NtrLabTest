package ru.symdeveloper.ntrlabtest.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ru.symdeveloper.ntrlabtest.R;
import ru.symdeveloper.ntrlabtest.events.DataReceivedEvent;
import ru.symdeveloper.ntrlabtest.managers.DataManager;
import ru.symdeveloper.ntrlabtest.model.SimpleListItem;
import uk.co.ribot.easyadapter.EasyAdapter;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    DataManager dataManager;

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.emptyView)
    View emptyView;

    private EasyRecyclerAdapter<SimpleListItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataManager = DataManager.getInstance(this);
        EventBus.getDefault().register(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        refreshData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            refreshData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshData() {
        dataManager.requestData();
    }

    public void onEventMainThread(DataReceivedEvent event) {
        if (event.hasData()) {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            ArrayList<SimpleListItem> contentList = event.getEntity().fillListData(getResources());
            adapter = new EasyRecyclerAdapter<>(this, SimpleItemHolder.class, contentList);
            recyclerView.setAdapter(adapter);
        } else {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            Snackbar.make(coordinatorLayout, R.string.snack_network_error, Snackbar.LENGTH_LONG)
                    .setAction(R.string.snack_action_retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            refreshData();
                        }
                    }).show();
        }
    }
}
