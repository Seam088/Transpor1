package com.bang.testt.bean;

import java.util.List;

/**
 * Created by 12457 on 2018/11/9.
 */

public class Transs {


    /**
     * tSpeakUrl : http://openapi.youdao.com/ttsapi?q=The+query&langType=en&sign=C58ABF237F31BD0D1B2331437F7A1649&salt=1541765885368&voice=4&format=mp3&appKey=206cee1ff2411bdc
     * web : [{"value":["Query","Inquiry","quota -vu","Enquiry"],"key":"查询"},{"value":["Spatial Query","Spatial Queries","ISpatialFilter"],"key":"空间查询"},{"value":["TRADE MARK ENQUIRIES","Registration queries"],"key":"注册查询"}]
     * query : 查询
     * translation : ["The query"]
     * errorCode : 0
     * dict : {"url":"yddict://m.youdao.com/dict?le=eng&q=%E6%9F%A5%E8%AF%A2"}
     * webdict : {"url":"http://m.youdao.com/dict?le=eng&q=%E6%9F%A5%E8%AF%A2"}
     * basic : {"explains":["inquire","query","demand","refer","see"]}
     * l : zh-CHS2EN
     * speakUrl : http://openapi.youdao.com/ttsapi?q=%E6%9F%A5%E8%AF%A2&langType=zh-CHS&sign=8B4ADA9E69A95852969AF0A73B9B536E&salt=1541765885368&voice=4&format=mp3&appKey=206cee1ff2411bdc
     */

    private String tSpeakUrl;             //错误返回码	一定存在
    private String query;                 //源语言	查询正确时，一定存在
    private String errorCode;             //错误返回码	一定存在
    private DictBean dict;                //词典deeplink	查询语种为支持语言时，存在
    private WebdictBean webdict;          //webdeeplink	查询语种为支持语言时，存在
    private BasicBean basic;              //词义	基本词典,查词时才有
    private String l;                     //源语言和目标语言	一定存在
    private String speakUrl;             //源语言发音地址	翻译成功一定存在
    private List<WebBean> web;          //词义	网络释义，该结果不一定存在
    private List<String> translation;   //翻译结果	查询正确时一定存在

    public String getTSpeakUrl() {
        return tSpeakUrl;
    }

    public void setTSpeakUrl(String tSpeakUrl) {
        this.tSpeakUrl = tSpeakUrl;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public DictBean getDict() {
        return dict;
    }

    public void setDict(DictBean dict) {
        this.dict = dict;
    }

    public WebdictBean getWebdict() {
        return webdict;
    }

    public void setWebdict(WebdictBean webdict) {
        this.webdict = webdict;
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getSpeakUrl() {
        return speakUrl;
    }

    public void setSpeakUrl(String speakUrl) {
        this.speakUrl = speakUrl;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public static class DictBean {
        /**
         * url : yddict://m.youdao.com/dict?le=eng&q=%E6%9F%A5%E8%AF%A2
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class WebdictBean {
        /**
         * url : http://m.youdao.com/dict?le=eng&q=%E6%9F%A5%E8%AF%A2
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class BasicBean {
        private List<String> explains;

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class WebBean {
        /**
         * value : ["Query","Inquiry","quota -vu","Enquiry"]
         * key : 查询
         */

        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return "Transs{" +
                "tSpeakUrl='" + tSpeakUrl + '\'' +
                ", query='" + query + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", dict=" + dict +
                ", webdict=" + webdict +
                ", basic=" + basic +
                ", l='" + l + '\'' +
                ", speakUrl='" + speakUrl + '\'' +
                ", web=" + web +
                ", translation=" + translation +
                '}';
    }
}
