package com.thinkdifferent.convertopic.service;

import net.sf.json.JSONObject;

import java.util.Map;

public interface ConvertPicService {
    /**
     * 将传入的JSON对象中记录的文件，转换为MP4，输出到指定的目录中；回调应用系统接口，将数据写回。
     * @param parameters 输入的参数，JSON格式数据对象
     */
    JSONObject ConvertPic(Map<String, Object> parameters);

    /**
     * 将传入的JSON对象中记录的文件，转换为JPG，输出到指定的目录中；回调应用系统接口，将数据写回。
     * @param parameters 输入的参数，JSON格式数据对象
     * @param blnJpgTemp 是否存留PDF转换过程中的jpg文件
     */
    JSONObject ConvertPic(Map<String, Object> parameters, boolean blnJpgTemp);

}
