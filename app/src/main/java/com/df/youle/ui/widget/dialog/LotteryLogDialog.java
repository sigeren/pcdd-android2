package com.df.youle.ui.widget.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.df.youle.R;
import com.df.youle.network.bean.BetDetailInfo;
import com.df.youle.ui.adapter.LotteryLogAdapter;
import com.df.youle.ui.adapter.manager.FullyLinearLayoutManager;

import java.util.List;

/**
 * Created by hang on 2017/2/27.
 * 最近开奖记录
 */

public class LotteryLogDialog extends CommonDialog {

    private RecyclerView rvData;

    private List<BetDetailInfo.OpenTime> data;

    public LotteryLogDialog(Context context, List<BetDetailInfo.OpenTime> data) {
        super(context, R.layout.dlg_lottery_log, ViewGroup.LayoutParams.MATCH_PARENT, 330);
        this.data = data;
    }

    @Override
    public void initDlgView() {
        rvData = getView(R.id.rvData);
        rvData.setLayoutManager(new FullyLinearLayoutManager(context));
        rvData.setAdapter(new LotteryLogAdapter(context, data));
    }
}
