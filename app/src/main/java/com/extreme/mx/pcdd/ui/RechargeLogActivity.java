package com.extreme.mx.pcdd.ui;

import android.os.Bundle;

import com.extreme.mx.pcdd.R;
import com.extreme.mx.pcdd.network.ApiInterface;
import com.extreme.mx.pcdd.network.HttpResultCallback;
import com.extreme.mx.pcdd.network.MySubcriber;
import com.extreme.mx.pcdd.network.bean.RechargeLogInfo;
import com.extreme.mx.pcdd.network.request.RechargeLogRequest;
import com.extreme.mx.pcdd.ui.adapter.RechargeLogAdapter;
import com.extreme.mx.pcdd.ui.base.BaseTopActivity;
import com.extreme.mx.pcdd.ui.widget.refresh.PullLoadMoreRecyclerView;
import com.extreme.mx.pcdd.util.T;

import java.util.List;

/**
 * Created by hang on 2017/3/4.
 * 充值转账记录
 */

public class RechargeLogActivity extends BaseTopActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private PullLoadMoreRecyclerView rvData;

    private int pageNo = 1;
    private int pageSize = 10;
    private RechargeLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_log);
        init();
    }

    private void init() {
        initTopBar("转账记录");
        rvData = getView(R.id.rvData);
        rvData.setLinearLayout();
        rvData.setOnPullLoadMoreListener(this);

        rvData.setRefreshing(true);
        onRefresh();
    }

    public void loadData() {
        RechargeLogRequest req = new RechargeLogRequest();
        req.page_no = pageNo+"";
        req.page_size = pageSize+"";
        HttpResultCallback<List<RechargeLogInfo>> callback = new HttpResultCallback<List<RechargeLogInfo>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                rvData.setPullLoadMoreCompleted();
            }

            @Override
            public void onError(Throwable e) {
                rvData.setPullLoadMoreCompleted();
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(List<RechargeLogInfo> rechargeLogInfos) {
                updateView(rechargeLogInfos);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.getRechargeLog(req, s);
    }

    public void updateView(List<RechargeLogInfo> data) {
        if(data.size() == 0)
            T.showShort("暂无数据");

        if(pageNo == 1) {
            adapter = new RechargeLogAdapter(this, data);
            rvData.setAdapter(adapter);
            rvData.setLessDataLoadMore();
        } else {
            adapter.getmData().addAll(data);
            adapter.notifyDataSetChanged();
        }
        pageNo++;
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        loadData();
    }
}
