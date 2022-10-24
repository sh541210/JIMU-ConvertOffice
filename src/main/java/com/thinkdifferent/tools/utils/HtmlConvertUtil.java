//package com.thinkdifferent.tools.utils;
//
//import com.itextpdf.io.source.ByteArrayOutputStream;
//import com.itextpdf.text.Document;
//
//import javax.xml.parsers.DocumentBuilder;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.nio.charset.StandardCharsets;
//
///**
// * HTML转Word工具类
// * 注：HTML转文档支持在线图片，本地图片不支持显示
// *
// * @author fay
// * @date 2021-06-03
// */
//
//public class HtmlConvertUtil {
//
//    /**
//     * HTML转换
//     *
//     * @param htmlBytes html字节码
//     * @param saveFormat 转换格式 SaveFormat常量
//     * @return 转换后文件字节
//     */
//    @SneakyThrows(Exception.class)
//    public static byte[] htmlConvert(byte[] htmlBytes, int saveFormat) {
//        MatchLicense.init();
//        Document document = new Document();
//        DocumentBuilder builder = new DocumentBuilder(document);
//        builder.insertHtml(new String(htmlBytes), true);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        document.save(outputStream, saveFormat);
//        return outputStream.toByteArray();
//    }
//
//    /**
//     * HTML转换
//     *
//     * @param html html内容
//     * @param savePath 转换后的文件路径
//     * @param saveFormat 转换格式 SaveFormat常量
//     * @return 转换后文件
//     */
//    @SneakyThrows(Exception.class)
//    public static File htmlConvert(String html, String savePath, int saveFormat) {
//        MatchLicense.init();
//        LoadOptions loadOptions = initLoadOptions();
//        Document document = new Document(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)), loadOptions);
//        document.save(savePath, saveFormat);
//        return new File(savePath);
//    }
//
//    /**
//     * HTML转换
//     *
//     * @param header 追加的头部内容
//     * @param html html内容
//     * @param footer 追加的尾部内容
//     * @param saveFormat 转换格式 SaveFormat常量
//     * @return 生成的文件字节流
//     */
//    @SneakyThrows(Exception.class)
//    public static ByteArrayOutputStream htmlConvert(String header, String html, String footer, int saveFormat) {
//        MatchLicense.init();
//        LoadOptions loadOptions = initLoadOptions();
//        Document document = new Document(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)), loadOptions);
//        DocumentBuilder builder = new DocumentBuilder(document);
//        builder.moveToDocumentStart();
//        builder.insertHtml(header, true);
//        builder.writeln();
//        builder.moveToDocumentEnd();
//        builder.writeln();
//        builder.insertHtml(footer, true);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        document.save(outputStream, saveFormat);
//        return outputStream;
//    }
//
//    private static LoadOptions initLoadOptions() {
//        LoadOptions loadOptions = new HtmlLoadOptions();
//        loadOptions.getLanguagePreferences().setDefaultEditingLanguage(EditingLanguage.CHINESE_PRC);
//        return loadOptions;
//    }
//}