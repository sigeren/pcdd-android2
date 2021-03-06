package com.df.youle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.df.youle.R;
import com.df.youle.network.ApiInterface;
import com.df.youle.network.HttpResultCallback;
import com.df.youle.network.MySubcriber;
import com.df.youle.network.request.RechargeOfflineRequest;
import com.df.youle.ui.base.BaseTopActivity;
import com.df.youle.util.T;
import com.df.youle.util.ViewUtil;

/**
 * Created by hang on 2017/2/28.
 */

public class TransferAliActivity extends BaseTopActivity implements View.OnClickListener {

    private EditText edAliAccount;
    private EditText edRealName;
    private EditText edTransferFee;

    private int accountId;
    private int type;   // 2支付宝 3 微信
    private String qrUrl;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_ali);
        init();
    }

    public void init() {
        accountId = getIntent().getIntExtra("id", 0);
        qrUrl = getIntent().getStringExtra("qrUrl");
        type = getIntent().getIntExtra("type", 2);
        title = type==2? "支付宝" : "微信";

        initTopBar(title+"转账");
        edAliAccount = getView(R.id.edAliAccount);
        edRealName = getView(R.id.edRealName);
        edTransferFee = getView(R.id.edTransferFee);

        getView(R.id.btnOK).setOnClickListener(this);

        if(type == 2) {
            edRealName.setVisibility(View.GONE);
            getView(R.id.tvLabelName).setVisibility(View.GONE);
        }
        setText(R.id.tvLabelAccount, title+"账号");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnOK:
                if(type==3 && ViewUtil.checkEditEmpty(edRealName, "请填写户名"))
                    return;
                if(ViewUtil.checkEditEmpty(edAliAccount, "请填写账号"))
                    return;
                submit();
                break;
        }
    }

    public void submit() {
        RechargeOfflineRequest req = new RechargeOfflineRequest();
        req.account_id = accountId+"";
        req.account = edAliAccount.getText().toString();
        req.account_type = type+"";
        req.real_name = edRealName.getText().toString();
        req.point = edTransferFee.getText().toString();
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                Intent it = new Intent(TransferAliActivity.this, RechargeOnlineSecondActivity.class);
                it.putExtra("title", title+"转账");
                it.putExtra("qrUrl", qrUrl);
                startActivity(it);
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(String s) {
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "提交中");
        ApiInterface.rechargeOffline(req, s);
    }
}
