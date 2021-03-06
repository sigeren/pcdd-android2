package com.df.youle.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.df.youle.R;
import com.df.youle.network.ApiInterface;
import com.df.youle.network.HttpResultCallback;
import com.df.youle.network.MySubcriber;
import com.df.youle.network.bean.NoticeInfo;
import com.df.youle.network.request.NoticeListRequest;
import com.df.youle.ui.adapter.MsgListAdapter;
import com.df.youle.ui.adapter.divider.DividerItemDecoration;
import com.df.youle.ui.base.BaseFragment;
import com.df.youle.ui.widget.refresh.PullLoadMoreRecyclerView;
import com.df.youle.util.T;

import java.util.List;

/**
 * Created by hang on 2017/1/17.
 */

public class MsgListFragment extends BaseFragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private PullLoadMoreRecyclerView rvData;

    private int pageNo;
    private int pageSize = 10;
    private MsgListAdapter adapter;

    private int type;   //1系统通知  2我的消息

    public static MsgListFragment getInstance(int type) {
        MsgListFragment instance = new MsgListFragment();
        Bundle b = new Bundle();
        b.putInt("type", type);
        instance.setArguments(b);
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg_list;
    }

    @Override
    protected void init(View rootView) {
        type = getArguments().getInt("type", 1);

        rvData = getView(R.id.rvData);
        rvData.setLinearLayout();
        rvData.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
        rvData.setOnPullLoadMoreListener(this);

        rvData.setRefreshing(true);
        onRefresh();
    }

    public void loadData() {
        NoticeListRequest req = new NoticeListRequest();
        req.type = type+"";
        req.page_no = pageNo+"";
        req.page_size = pageSize+"";
        HttpResultCallback<List<NoticeInfo>> callback = new HttpResultCallback<List<NoticeInfo>>() {
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
            public void onNext(List<NoticeInfo> noticeInfos) {
                updateView(noticeInfos);
            }
        };
        MySubcriber<List<NoticeInfo>> s = new MySubcriber<List<NoticeInfo>>(activity, callback, false, "");
        ApiInterface.getNoticeList(req, s);
    }

    public void updateView(List<NoticeInfo> data) {
        if(pageNo == 1) {
            adapter = new MsgListAdapter(activity, data);
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
