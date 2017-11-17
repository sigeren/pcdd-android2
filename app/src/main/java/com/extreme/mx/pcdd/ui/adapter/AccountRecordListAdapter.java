package com.extreme.mx.pcdd.ui.adapter;

import android.content.Context;

import com.extreme.mx.pcdd.R;
import com.extreme.mx.pcdd.network.bean.AccountRecordInfo;
import com.extreme.mx.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.extreme.mx.pcdd.ui.adapter.base.ViewHolder;
import com.extreme.mx.pcdd.util.DateUtil;

import java.util.List;

/**
 * Created by hang on 2017/1/23.
 */

public class AccountRecordListAdapter extends BaseRecyclerAdapter<AccountRecordInfo> {

    public AccountRecordListAdapter(Context context, List<AccountRecordInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_account_record);
    }

    @Override
    public void onBind(ViewHolder holder, AccountRecordInfo item, int position) {
        holder.setText(R.id.tvAccountTime, DateUtil.getTime(item.create_time, 0));
        holder.setText(R.id.tvAccountPoint, item.point+"");
        holder.setText(R.id.tvAccountDesc, item.point_desc);
    }
}
