package com.thinkdifferent.convertpic.utils;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

@Component
public class WaterMarkUtil {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String srcImgPath = "D:/cvtest/1.jpg";
        String iconPath = "D:/IdeaProjects/JIMU-ConvertPic/watermark/watermark.png";
        String targerPath = "D:/cvtest/1_wm.jpg";
        // 给图片添加水印
//        WaterMarkUtil.markImageByIcon(iconPath, srcImgPath, targerPath);
//        // 给图片添加水印,水印旋转-45
        WaterMarkUtil.markImageByIcon(iconPath, srcImgPath, targerPath, 45,
                null, null, null, null);

    }

    /**
     * 给图片添加水印
     *
     * @param strIconPath 水印图片路径
     * @param strSourceImgPath 源图片路径
     * @param strTargetImgPath 目标图片路径
     */
    public static boolean markImageByIcon(String strIconPath, String strSourceImgPath, String strTargetImgPath) {
        return markImageByIcon(strIconPath, strSourceImgPath, strTargetImgPath,
                null, null, null, null, null);
    }

    /**
     * 给图片添加水印、可设置水印图片旋转角度
     *
     * @param strIconPath 水印图片路径
     * @param strSourceImgPath 源图片路径
     * @param strTargetImgPath 目标图片路径
     * @param intDegree 水印图片旋转角度
     * @param intIconLocateX 水印的位置，横轴
     * @param intIconLocateY 水印的位置，纵轴
     * @param intIconWidth 水印图片的宽度
     * @param intIconHeight 水印图片的高度
     */
    public static boolean markImageByIcon(String strIconPath, String strSourceImgPath, String strTargetImgPath, Integer intDegree,
                                          Integer intIconLocateX, Integer intIconLocateY,
                                          Integer intIconWidth, Integer intIconHeight) {
        OutputStream os = null;
        try {
            File fileSourceImg = new File(strSourceImgPath);
            BufferedImage buffSourceImg = ImageIO.read(new FileInputStream(fileSourceImg));

            BufferedImage buffImg = new BufferedImage(buffSourceImg.getWidth(), buffSourceImg.getHeight(), BufferedImage.TYPE_INT_RGB);
            // 获取图片的大小
            int intImageWidth = buffImg.getWidth();
            int intImageHeight = buffImg.getHeight();

            // 计算水印的位置，默认在图片的左下角
            if(intIconLocateX == null){
                intIconLocateX = intImageWidth/10;
            }
            if(intIconLocateY == null){
                intIconLocateY = intImageHeight - intImageHeight/5;
            }

            // 得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            // 绘制原图片，作为加水印的基础图片
            g.drawImage(buffSourceImg, 0, 0, buffSourceImg.getWidth(), buffSourceImg.getHeight(), null);

            if (null != intDegree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(intDegree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }

            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(strIconPath);
            // 得到Image对象。
            Image img = imgIcon.getImage();
            // 水印的默认宽度、高度，是图像长边的1/6
            if(intIconWidth == null || intIconHeight == null) {
                if(intImageHeight>intImageWidth){
                    intIconWidth = intImageHeight/6;
                    intIconHeight = intIconWidth;
                }else{
                    intIconWidth = intImageWidth/6;
                    intIconHeight = intIconWidth;
                }
            }

            // 表示水印图片的位置
            g.drawImage(img, intIconLocateX, intIconLocateY, intIconWidth, intIconHeight, null);
            // 关闭图像
            g.dispose();

            os = new FileOutputStream(strTargetImgPath);

            // 生成图片
            ImageIO.write(buffImg, "JPG", os);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != os)
                    os.close();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    //////////////////////////////////////////

    /**
     * 获取文本长度。汉字为1:1，英文和数字为2:1
     */
    private static int getTextLength(String text) {
        int length = text.length();
        for (int i = 0; i < text.length(); i++) {
            String s = String.valueOf(text.charAt(i));
            if (s.getBytes().length > 1) {
                length++;
            }
        }
        length = length % 2 == 0 ? length / 2 : length / 2 + 1;
        return length;
    }


    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     * @param strWaterMarkText 水印文字内容
     * @param strSourceImgPath 源图片路径和文件名
     * @param strTargetImgPath 加水印完毕后的新图片路径和文件名
     * @param intDegree 文字旋转角度
     * @param strServerRealPath 服务所在绝对路径
     * @param floatAlpha 透明度，小数。例如，0.7f
     * @param intFontSize 字号，整数。例如，90
     * @param strFontName 字体名称。例如，宋体
     * @param strFontColor 字体颜色。例如，gray
     * @param intXMove 水印文字之间横向间隔
     * @param intYMove 水印文字之间纵向间隔
     * @return
     */
    public static boolean markImageByText(String strWaterMarkText, String strSourceImgPath, String strTargetImgPath, Integer intDegree,
                                          String strServerRealPath,
                                          float floatAlpha, Integer intFontSize, String strFontName, String strFontColor,
                                          Integer intXMove, Integer intYMove) {
        String strPropPath = strServerRealPath + "/" + "properties/watermark.properties";
        // 水印透明度
        if(floatAlpha == 0){
            floatAlpha = 0.7f;
        }

        // 水印文字大小
        if(intFontSize == null){
            intFontSize = 90;
        }

        // 水印文字字体
        if(strFontName==null || "".equals(strFontName)){
            strFontName = "宋体";
        }
        Font font = new Font(strFontName, Font.PLAIN, intFontSize);

        // 水印文字颜色
        if(strFontColor==null || "".equals(strFontColor)){
            strFontColor = "gray";
        }
        Color color = null;
        try {
            Field field = Color.class.getField(strFontColor);
            color = (Color)field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 水印之间的间隔
        if(intXMove == null){
            intXMove = 700;
        }
        if(intYMove == null){
            intYMove = 600;
        }

        OutputStream os = null;
        try {
            // 源图片
            File fileSourceImg = new File(strSourceImgPath);
            BufferedImage buffSourceImg = ImageIO.read(new FileInputStream(fileSourceImg));

            BufferedImage buffImg = new BufferedImage(buffSourceImg.getWidth(), buffSourceImg.getHeight(), BufferedImage.TYPE_INT_RGB);
            // 获取图片的大小
            int intImageWidth = buffImg.getWidth();
            int intImageHeight = buffImg.getHeight();

            // 得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 绘制原图片，作为加水印的基础图片
            g.drawImage(buffSourceImg, 0, 0, buffSourceImg.getWidth(), buffSourceImg.getHeight(), null);

            // 设置水印旋转
            if (null != intDegree) {
                g.rotate(Math.toRadians(intDegree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            // 设置水印文字颜色
            g.setColor(color);
            // 设置水印文字Font
            g.setFont(font);
            // 设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, floatAlpha));

            int x = -intImageWidth / 2;
            int y = 0;
            int markWidth = intFontSize * getTextLength(strWaterMarkText);// 字体长度
            int markHeight = intFontSize;// 字体高度


             // 循环添加水印
            while (x < intImageWidth * 1.5) {
                y = -intImageHeight / 2;
                while (y < intImageHeight * 1.5) {
                    g.drawString(strWaterMarkText, x, y);

                    y += markHeight + intYMove;
                }
                x += markWidth + intXMove;
            }

            // 释放资源
            g.dispose();
            // 生成图片
            os = new FileOutputStream(strTargetImgPath);
            ImageIO.write(buffImg, "JPG", os);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
