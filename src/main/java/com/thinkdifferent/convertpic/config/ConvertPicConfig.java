package com.thinkdifferent.convertpic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class ConvertPicConfig {

    public static String inPutTempPath;
    @Value("${convert.pic.inPutTempPath}")
    public void setInPutTempPath(String strInPutTempPath) {
        ConvertPicConfig.inPutTempPath = strInPutTempPath;
    }

    public static String outPutPath;
    @Value("${convert.pic.outPutPath}")
    public void setOutPutPath(String strOutPutPath) {
        ConvertPicConfig.outPutPath = strOutPutPath;
    }

}
