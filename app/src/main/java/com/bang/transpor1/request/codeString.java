package com.bang.transpor1.request;

/**
 * Created by bang on 2018/10/9.
 */

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class codeString {
    /**
     * 判断文件的编码格式
     * @param fileName :file
     * @return 文件编码格式
     * @throws Exception
     */
    public static String codeString(String fileName) throws Exception{
        BufferedInputStream bin = new BufferedInputStream(
                new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;
    }
}
