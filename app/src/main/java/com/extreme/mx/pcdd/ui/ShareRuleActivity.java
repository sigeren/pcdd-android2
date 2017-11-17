package com.extreme.mx.pcdd.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.extreme.mx.pcdd.R;
import com.extreme.mx.pcdd.network.ApiInterface;
import com.extreme.mx.pcdd.network.HttpResultCallback;
import com.extreme.mx.pcdd.network.MySubcriber;
import com.extreme.mx.pcdd.network.bean.ProxyRuleInfo;
import com.extreme.mx.pcdd.network.request.BaseRequest;
import com.extreme.mx.pcdd.ui.adapter.CommissionRuleAdapter;
import com.extreme.mx.pcdd.ui.adapter.manager.FullyLinearLayoutManager;
import com.extreme.mx.pcdd.ui.base.BaseTopActivity;
import com.extreme.mx.pcdd.util.T;


/**
 * Created by hang on 2017/4/19.
 * 分享规则
 */

public class ShareRuleActivity extends BaseTopActivity {

    private RecyclerView rvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_rule);
        init();
    }

    public void init() {
        initTopBar("分享规则");
        rvData = getView(R.id.rvData);
        rvData.setLayoutManager(new FullyLinearLayoutManager(this));

        loadData();
    }

    public void loadData() {
        HttpResultCallback<ProxyRuleInfo> callback = new HttpResultCallback<ProxyRuleInfo>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(ProxyRuleInfo proxyRuleInfo) {
                rvData.setAdapter(new CommissionRuleAdapter(ShareRuleActivity.this, proxyRuleInfo.bili_list));
                setText(R.id.tvProxyTip, getString(R.string.rule_vip_share, proxyRuleInfo.num));
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "加载数据");
        ApiInterface.getProxyRule(new BaseRequest(), s);
    }
}
