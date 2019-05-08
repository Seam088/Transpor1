package com.bang.testt.fanyi;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class YouDaoTransApi {
    private static final String TRANS_API_HOST = "http://openapi.youdao.com/api";

    private String appid;
    private String securityKey;

    public YouDaoTransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appKey", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        try {
			params.put("sign", MD5.md5(src));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return params;
    }

}
