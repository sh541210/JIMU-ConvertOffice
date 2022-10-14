package com.thinkdifferent.convertoffice.domain;

/**
 * 水印实体类
 *
 * @author json
 * @date 2022-10-13
 */
public class WaterMark {
    /**
     * 水印类型
     */
    private String waterMarkType;
    /**
     * 文件
     */
    private String waterMarkFile;
    /**
     *
     */
    private String degree;

    private String alpha;

    private Double LocateX;

    private Double LocateY;

    private String waterMarkWidth;

    private String waterMarkHeight;

    public String getWaterMarkType() {
        return waterMarkType;
    }

    public void setWaterMarkType(String waterMarkType) {
        this.waterMarkType = waterMarkType;
    }

    public String getWaterMarkFile() {
        return waterMarkFile;
    }

    public void setWaterMarkFile(String waterMarkFile) {
        this.waterMarkFile = waterMarkFile;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public Double getLocateX() {
        return LocateX;
    }

    public void setLocateX(Double locateX) {
        LocateX = locateX;
    }

    public Double getLocateY() {
        return LocateY;
    }

    public void setLocateY(Double locateY) {
        LocateY = locateY;
    }

    public String getWaterMarkWidth() {
        return waterMarkWidth;
    }

    public void setWaterMarkWidth(String waterMarkWidth) {
        this.waterMarkWidth = waterMarkWidth;
    }

    public String getWaterMarkHeight() {
        return waterMarkHeight;
    }

    public void setWaterMarkHeight(String waterMarkHeight) {
        this.waterMarkHeight = waterMarkHeight;
    }
}
