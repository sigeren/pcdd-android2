package com.extreme.mx.pcdd.network.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hang on 2017/2/21.
 */

public class GameTypeInfo implements Serializable {

    public String name;
    public ArrayList<GameOddsInfo> te_shu;
    public ArrayList<GameOddsInfo> da_xiao;
    public ArrayList<GameOddsInfo> shu_zi;
}
