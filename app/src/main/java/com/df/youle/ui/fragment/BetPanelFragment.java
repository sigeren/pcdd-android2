package com.df.youle.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.df.youle.R;
import com.df.youle.impl.IBetOdds;
import com.df.youle.network.bean.GameOddsInfo;
import com.df.youle.network.bean.GameTypeInfo;
import com.df.youle.ui.adapter.OddsDXDSAdapter;
import com.df.youle.ui.adapter.base.BaseRecyclerAdapter;
import com.df.youle.ui.adapter.manager.FullyGridLayoutManager;
import com.df.youle.ui.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by hang on 2017/7/3.
 */

public class BetPanelFragment extends BaseFragment
        implements BaseRecyclerAdapter.OnRecyclerItemClickListener, IBetOdds, View.OnClickListener{

    private LinearLayout llPanel;
    private TextView tvTitle;
    private TextView tvResult;
    private RecyclerView rvOdds;
    public ViewPager viewPager;

    private String name;
    private ArrayList<GameOddsInfo> data;
    private OddsDXDSAdapter oddsAdapter;

    private int pageIndex;
    private String[] colors = {"#007cef", "#e54a4a", "#18a478"};

    public static BetPanelFragment getInstance(ViewPager viewPager, GameTypeInfo data, int page) {
        BetPanelFragment instance = new BetPanelFragment();
        Bundle b = new Bundle();
        b.putInt("page", page);
        b.putSerializable("title", data.name);
        b.putSerializable("data", data.list);
        instance.setArguments(b);
        instance.viewPager = viewPager;
        return instance;
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bet_panel;
    }

    @Override
    protected void init(View rootView) {
        pageIndex = getArguments().getInt("page");
        name = getArguments().getString("title");
        data = (ArrayList<GameOddsInfo>) getArguments().getSerializable("data");

        llPanel = getView(R.id.llBetPanel);
        tvTitle = getView(R.id.tvTitle);
        tvResult = getView(R.id.tvOddsResult);
        rvOdds = getView(R.id.rvBetOdds);

        rvOdds.setLayoutManager(new FullyGridLayoutManager(activity, 5));
        oddsAdapter = new OddsDXDSAdapter(activity, data);
        oddsAdapter.setOnRecyclerItemClickListener(this);
        rvOdds.setAdapter(oddsAdapter);

        tvTitle.setText(name);
        tvResult.setText(data.get(0).result_desc);
        llPanel.setBackgroundColor(Color.parseColor(colors[pageIndex % 3]));

        getView(R.id.ivPreviousPage).setOnClickListener(this);
        getView(R.id.ivNextPage).setOnClickListener(this);
    }

    @Override
    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, int position) {
        oddsAdapter.selectedIndex = position;
        oddsAdapter.notifyDataSetChanged();
        tvResult.setText(getString(R.string.bet_result_number, oddsAdapter.getmData().get(position).result));
    }

    @Override
    public GameOddsInfo getSelectedOdds() {
        return oddsAdapter.getSelectedItem();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ivPreviousPage:
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
                break;

            case R.id.ivNextPage:
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                break;
        }
    }
}
