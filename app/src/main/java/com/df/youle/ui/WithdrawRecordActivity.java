package com.df.youle.ui;

import android.os.Bundle;

import com.df.youle.R;
import com.df.youle.network.ApiInterface;
import com.df.youle.network.HttpResultCallback;
import com.df.youle.network.MySubcriber;
import com.df.youle.network.bean.WithdrawRecordInfo;
import com.df.youle.network.request.WithdrawRecordRequest;
import com.df.youle.network.response.WithdrawRecordResponse;
import com.df.youle.ui.adapter.WithdrawRecordListAdapter;
import com.df.youle.ui.adapter.divider.DividerItemDecoration;
import com.df.youle.ui.base.BaseTopActivity;
import com.df.youle.ui.widget.refresh.PullLoadMoreRecyclerView;
import com.df.youle.util.T;

import java.util.List;

/**
 * Created by hang on 2017/1/23.
 * 提现记录
 */

public class WithdrawRecordActivity extends BaseTopActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {
    
    private PullLoadMoreRecyclerView rvData;

    private int pageNo = 1;
    private int pageSize = 10;
    private WithdrawRecordListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_record);
        init();
    }

    private void init() {
        initTopBar("提现记录");
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
        WithdrawRecordRequest req = new WithdrawRecordRequest();
        req.page_no = pageNo+"";
        req.page_size = pageSize+"";
        HttpResultCallback<WithdrawRecordResponse> callback = new HttpResultCallback<WithdrawRecordResponse>() {
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
            public void onNext(WithdrawRecordResponse data) {
                updateView(data.list);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.getWithdrawRecord(req, s);
    }

    private void updateView(List<WithdrawRecordInfo> data) {
        if(data.size() == 0)
            T.showShort("暂无数据");

        if(pageNo == 1) {
            adapter = new WithdrawRecordListAdapter(this, data);
            rvData.setAdapter(adapter);
            rvData.setLessDataLoadMore();
        } else {
            adapter.getmData().addAll(data);
            adapter.notifyDataSetChanged();
        }
        pageNo++;
    }
}
