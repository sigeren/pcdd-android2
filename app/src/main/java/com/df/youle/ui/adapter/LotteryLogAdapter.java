package com.df.youle.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.df.youle.R;
import com.df.youle.network.bean.BetDetailInfo;
import com.df.youle.ui.adapter.base.BaseRecyclerAdapter;
import com.df.youle.ui.adapter.base.ViewHolder;

import java.util.List;

/**
 * Created by hang on 2017/2/27.
 */

public class LotteryLogAdapter extends BaseRecyclerAdapter<BetDetailInfo.OpenTime> {

    public LotteryLogAdapter(Context context, List<BetDetailInfo.OpenTime> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_lottery_log);
    }

    @Override
    public void onBind(ViewHolder holder, BetDetailInfo.OpenTime item, int position) {
        TextView tvNum = holder.getView(R.id.tvLotteryNum);
        tvNum.setText(Html.fromHtml(mContext.getString(R.string.pre_game_num, item.game_num+"")));
        TextView tvFormula = holder.getView(R.id.tvLotteryResult);
        tvFormula.setText(item.get_result );
        holder.setText(R.id.tvBetResultType, item.game_result_desc);
    }
}
