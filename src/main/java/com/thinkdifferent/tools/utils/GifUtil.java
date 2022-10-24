package com.thinkdifferent.tools.utils;


import cn.hutool.core.img.gif.AnimatedGifEncoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Gif工具类：实现功能
 * 1、批量将PDF转gif
 * 2、PDF转gif
 */
public class GifUtil {

    /**
     * 批量将PDF页转成gif，并输出到指定位置
     *
     * @param bufferedImages PDF页流的集合
     * @param target         gif输出目录(eg: d:/poi/)
     * @param filename       gif输出文件名称(eg: abc.gif)
     */
    public static void pdf2GifBatch(List<BufferedImage> bufferedImages, String target, String filename, StringBuilder printPath, StringBuilder jarPath) throws Exception {
        if (bufferedImages != null && bufferedImages.size() > 0) {
            createFile(target, true);
            int pageSize = bufferedImages.size();
            StringBuilder sb = null;
            String name = null;
            for (int i = 0; i < pageSize; i++) {
                sb = new StringBuilder(target);
                name = String.format(filename, i + 1);
                sb.append(name);
                printPath.append(",/").append(name);
                jarPath.append("webapps/print/template/clauseimages/").append(name).append("\n");
                pdf2Gif(bufferedImages.get(i), sb.toString());
            }
        }
    }

    /**
     * 创建文件
     *
     * @param filename    文件名称（文件完整路径）
     * @param isDirectory 是否文件夹
     * @throws Exception
     */
    private static void createFile(String filename, boolean isDirectory) throws Exception {
        File file = new File(filename);
        if (!file.exists()) {
            if (isDirectory) {
                file.mkdirs();
            } else {
                file.createNewFile();
            }
        }
    }

    /**
     * PDF转gif
     *
     * @param bufferedImage PDF流
     * @param filename      输出路径(目录+文件名 eg: d:/poi/abc1.gif)
     */
    public static void pdf2Gif(BufferedImage bufferedImage, String filename) {
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.setDelay(0);
        e.start(filename);
        e.addFrame(bufferedImage);
        e.finish();
    }
}