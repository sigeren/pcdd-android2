package com.extreme.mx.pcdd.network.request;

import com.extreme.mx.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/2/28.
 */

public class ExchangeGiftRequest extends BaseRequest {
    public String gift_id;
    @Encrypt
    public String gift_count;
    public String address;
}