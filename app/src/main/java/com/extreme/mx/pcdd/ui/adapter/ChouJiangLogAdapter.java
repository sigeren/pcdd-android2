package com.extreme.mx.pcdd.ui.adapter;

import android.content.Context;

import com.extreme.mx.pcdd.R;
import com.extreme.mx.pcdd.network.bean.LotteryLogInfo;
import com.extreme.mx.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.extreme.mx.pcdd.ui.adapter.base.ViewHolder;
import com.extreme.mx.pcdd.util.DateUtil;

import java.util.List;

/**
 * Created by hang on 2017/6/18.
 */

public class ChouJiangLogAdapter extends BaseRecyclerAdapter<LotteryLogInfo> {

    public ChouJiangLogAdapter(Context context, List<LotteryLogInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_choujiang_log);
    }

    @Override
    public void onBind(ViewHolder holder, LotteryLogInfo item, int position) {
        holder.setText(R.id.tvLotteryTime, DateUtil.getTime(item.create_time, 0));
        holder.setText(R.id.tvLotteryOption, item.chou_jiang_option);
        holder.setText(R.id.tvLotteryPoint, item.point+"元宝");
    }
}
