package com.df.youle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.df.youle.R;
import com.df.youle.manager.UserInfoManager;
import com.df.youle.network.ApiInterface;
import com.df.youle.network.HttpResultCallback;
import com.df.youle.network.MySubcriber;
import com.df.youle.network.bean.UserInfo;
import com.df.youle.network.request.BaseRequest;
import com.df.youle.ui.base.BaseFragmentActivity;
import com.df.youle.util.CheckUtil;

/**
 * Created by hang on 2017/1/16.
 * 首页侧边栏
 */
public class MoreActivity extends BaseFragmentActivity implements View.OnClickListener {

    private TextView tvMyPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        init();
        loadUserInfo();
    }

    public void init() {
        tvMyPoint = getView(R.id.tvMyPoint);

        String avatarUrl = UserInfoManager.getUserInfo(this).user_photo;
        if(!TextUtils.isEmpty(avatarUrl))
            setImageByURL(R.id.ivAvatar, avatarUrl);

        initListener();
    }

    public void initListener() {
        getView(R.id.ivSidebarOff).setOnClickListener(this);
        getView(R.id.ivBack).setOnClickListener(this);
        getView(R.id.llMyWallet).setOnClickListener(this);
        getView(R.id.btnRecharge).setOnClickListener(this);
        getView(R.id.btnWithdraw).setOnClickListener(this);
        getView(R.id.llMyMessage).setOnClickListener(this);
    }

    public void loadUserInfo() {
        HttpResultCallback<UserInfo> callback = new HttpResultCallback<UserInfo>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(UserInfo userInfo) {
                tvMyPoint.setText(userInfo.point+"元宝");
                UserInfoManager.saveUserInfo(MoreActivity.this, userInfo);
            }
        };
        MySubcriber s = new MySubcriber(this, callback, false, "");
        ApiInterface.getUserInfo(new BaseRequest(), s);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ivBack:
            case R.id.ivSidebarOff:
                back();
                break;

            case R.id.llMyWallet:
                startActivity(new Intent(this, WalletActivity.class));
                break;

            case R.id.btnRecharge:
                startActivity(new Intent(this, RechargeActivity.class));
                break;

            case R.id.btnWithdraw:
                if(CheckUtil.checkBind(this))
                    startActivity(new Intent(this, WithdrawActivity.class));
                break;

            case R.id.llMyMessage:
                startActivity(new Intent(this, MyMessageActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    public void back() {
        finish();
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
    }
}
