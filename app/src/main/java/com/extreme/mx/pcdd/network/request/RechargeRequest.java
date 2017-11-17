package com.extreme.mx.pcdd.network.request;

import com.extreme.mx.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/2/28.
 */

public class RechargeRequest extends BaseRequest {
    @Encrypt
    public String total_fee;
}
