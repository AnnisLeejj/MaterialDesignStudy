package com.example.myapplication.recycleViewSuspension;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class MultiActivity extends AppCompatActivity {
    private RecyclerView mFeedList;
    private RelativeLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private int mCurrentPosition = 0;

    private int mSuspensionHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);

        mSuspensionBar = findViewById(R.id.suspension_bar);
        mSuspensionTv = findViewById(R.id.tv_time);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final MultiFeedAdapter adapter = new MultiFeedAdapter();

        mFeedList = findViewById(R.id.feed_list);
        mFeedList.setLayoutManager(linearLayoutManager);
        mFeedList.setAdapter(adapter);
        mFeedList.setHasFixedSize(true);

        mFeedList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = mSuspensionBar.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                if (adapter.getItemViewType(mCurrentPosition + 1) == MultiFeedAdapter.TYPE_TIME) {
                    if (view != null) {
                        if (view.getTop() <= mSuspensionHeight) {
                            mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                        } else {
                            mSuspensionBar.setY(0);
                        }
                    }
                }
//                Log.d("HHHH", "view.getTop(): " + view.getTop() + "          mSuspensionHeight:" + mSuspensionHeight + "     mCurrentPosition:" + mCurrentPosition + "    firstVisibleItemPosition:" + firstVisibleItemPosition);

                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    if (view.getTop() > mSuspensionHeight && adapter.getItemViewType(mCurrentPosition + 1) == MultiFeedAdapter.TYPE_TIME) {
                        mSuspensionBar.setY(-mSuspensionHeight);
                    } else {
                        mSuspensionBar.setY(0);
                    }
                    updateSuspensionBar();
                }
            }
        });
        updateSuspensionBar();
    }

    private void updateSuspensionBar() {
        mSuspensionTv.setText(getTime(mCurrentPosition));
    }

    private String getTime(int position) {
        return "NOVEMBER " + (1 + position / 4);
    }
}