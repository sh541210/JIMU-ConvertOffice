package com.thinkdifferent.convertoffice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 文件存储位置
 */
@Configuration
@RefreshScope
public class ConvertOfficeConfig {
    /**
     * 接收的输入文件存储的临时文件夹
     */
    public static String inPutTempPath;
    /**
     * 默认本地输出文件所在文件夹
     */
    public static String outPutPath;

    @Value("${convert.office.inPutTempPath}")
    public void setInPutTempPath(String inPutTempPath) {
        ConvertOfficeConfig.inPutTempPath = inPutTempPath;
    }

    @Value("${convert.office.outPutPath}")
    public void setOutPutPath(String outPutPath) {
        ConvertOfficeConfig.outPutPath = outPutPath;
    }


}
