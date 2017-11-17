package com.extreme.mx.pcdd.network.request;

import com.extreme.mx.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/2/6.
 */

public class RegisterRequest extends BaseRequest {
    public String account;
    public String code; //邀请码

    @Encrypt
    public String password;
}