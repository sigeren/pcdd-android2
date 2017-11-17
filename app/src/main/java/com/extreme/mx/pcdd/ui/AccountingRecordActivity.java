package com.extreme.mx.pcdd.ui;

import android.os.Bundle;

import com.extreme.mx.pcdd.R;
import com.extreme.mx.pcdd.network.ApiInterface;
import com.extreme.mx.pcdd.network.HttpResultCallback;
import com.extreme.mx.pcdd.network.MySubcriber;
import com.extreme.mx.pcdd.network.bean.AccountRecordInfo;
import com.extreme.mx.pcdd.network.request.AccountRecordRequest;
import com.extreme.mx.pcdd.ui.adapter.AccountRecordListAdapter;
import com.extreme.mx.pcdd.ui.adapter.divider.DividerItemDecoration;
import com.extreme.mx.pcdd.ui.base.BaseTopActivity;
import com.extreme.mx.pcdd.ui.widget.refresh.PullLoadMoreRecyclerView;
import com.extreme.mx.pcdd.util.T;

import java.util.List;

/**
 * Created by hang on 2017/1/23.
 * 帐变记录
 */

public class AccountingRecordActivity extends BaseTopActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private PullLoadMoreRecyclerView rvData;

    private int pageNo = 1;
    private int pageSize = 10;
    private AccountRecordListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting_record);
        init();
    }

    private void init() {
        initTopBar("帐变记录");
        rvData = getView(R.id.rvData);

        rvData.setLinearLayout();
        rvData.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rvData.setOnPullLoadMoreListener(this);

        rvData.setRefreshing(true);
        onRefresh();
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

    public void loadData() {
        AccountRecordRequest req = new AccountRecordRequest();
        req.page_no = pageNo+"";
        req.page_size = pageSize+"";
        HttpResultCallback<List<AccountRecordInfo>> callback = new HttpResultCallback<List<AccountRecordInfo>>() {
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
            public void onNext(List<AccountRecordInfo> accountRecordInfos) {
                updateView(accountRecordInfos);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.getAccountRecord(req, s);
    }

    private void updateView(List<AccountRecordInfo> data) {
        if(data.size() == 0)
            T.showShort("暂无数据");

        if(pageNo == 1) {
            adapter = new AccountRecordListAdapter(this, data);
            rvData.setAdapter(adapter);
            rvData.setLessDataLoadMore();
        } else {
            adapter.getmData().addAll(data);
            adapter.notifyDataSetChanged();
        }
        pageNo++;
    }
}