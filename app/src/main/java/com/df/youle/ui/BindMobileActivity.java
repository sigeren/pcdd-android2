package com.df.youle.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.df.youle.R;
import com.df.youle.manager.UserInfoManager;
import com.df.youle.network.ApiInterface;
import com.df.youle.network.HttpResultCallback;
import com.df.youle.network.MySubcriber;
import com.df.youle.network.bean.UserInfo;
import com.df.youle.network.request.BindMobileRequest;
import com.df.youle.ui.base.BaseTopActivity;
import com.df.youle.ui.widget.CountDownButton;
import com.df.youle.util.T;
import com.df.youle.util.ViewUtil;

/**
 * Created by hang on 2017/2/27.
 * 绑定手机
 */

public class BindMobileActivity extends BaseTopActivity implements View.OnClickListener {

    private EditText edMobile;
    private EditText edVCode;
    private CountDownButton btnVCode;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_mobile);
        init();
    }

    private void init() {
        initTopBar("手机绑定");
        edMobile = getView(R.id.edBindMobile);
        edVCode = getView(R.id.edVCode);
        btnVCode = getView(R.id.btnVCode);

        userInfo = UserInfoManager.getUserInfo(this);
        edMobile.setText(userInfo.mobile);

        btnVCode.setOnClickListener(this);
        getView(R.id.btnOK).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnVCode.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        btnVCode.onStop();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnVCode:
                if (ViewUtil.checkEditEmpty(edMobile, "请输入手机号"))
                    return;
                btnVCode.getVCode(edMobile.getText().toString(), null);
                break;

            case R.id.btnOK:
                if(ViewUtil.checkEditEmpty(edMobile, "请输入手机号"))
                    return;
                if(ViewUtil.checkEditEmpty(edVCode, "请输入验证码"))
                    return;
                bind();
                break;
        }
    }

    public void bind() {
        final BindMobileRequest req = new BindMobileRequest();
        req.mobile = edMobile.getText().toString();
        req.msg_id = btnVCode.getVCodeId();
        req.msg_code = edVCode.getText().toString();
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("绑定成功");
                userInfo.mobile = req.mobile;
                UserInfoManager.saveUserInfo(BindMobileActivity.this, userInfo);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(String s) {
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "绑定中");
        ApiInterface.bindMobile(req, s);
    }
}
