package com.thinkdifferent.tools.controller;


import com.thinkdifferent.tools.domain.RespBody;
import com.thinkdifferent.tools.utils.FileUploaderUtils;
import com.thinkdifferent.tools.utils.PdfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件转 PDF 示例
 * @author hcwdc.com
 */
@RestController
@RequestMapping("/docpreview")
public class PdfToolsDemo {
    @Autowired
    private FileUploaderUtils fileUploaderUtils;
    @Autowired
    private PdfUtils pdfUtils;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("upload")
    public RespBody<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return RespBody.fail(-1,"请上传文件");
        }
        String fileName = file.getOriginalFilename();
        String urlFileName = fileUploaderUtils.getRandomFileName(FileUploaderUtils.getSuffix(fileName));
        String url = fileUploaderUtils.upload(file.getBytes(),urlFileName);
        Map<String, Object> data = new HashMap<>(1);
        data.put("src", url);
        return RespBody.data(data);
    }


    /**
     * 文件上传并转为PDF
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("topdf")
    public RespBody<Map<String, Object>> toPdf(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return RespBody.fail(-1,"请上传文件");
        }
        String fileName = file.getOriginalFilename();
        String urlFileName = fileUploaderUtils.getRandomFileName(FileUploaderUtils.getSuffix(fileName));
        String originalUrl = fileUploaderUtils.upload(file.getBytes(),urlFileName);
        pdfUtils.toPdf(pdfUtils.getServerPath(originalUrl),pdfUtils.getTargetFolder(originalUrl));
        Map<String, Object> data = new HashMap<>();
        data.put("src", originalUrl);
        data.put("pdfPath", pdfUtils.getPDFUrl(originalUrl));
        return RespBody.data(data);
    }

    /**
     * 文件上传并转为图片PNG格式
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("topng")
    public RespBody<Map<String, Object>> toPng(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return RespBody.fail(-1,"请上传文件");
        }
        String fileName = file.getOriginalFilename();
        String urlFileName = fileUploaderUtils.getRandomFileName(FileUploaderUtils.getSuffix(fileName));
        String originalUrl = fileUploaderUtils.upload(file.getBytes(),urlFileName);
        pdfUtils.toPdf(pdfUtils.getServerPath(originalUrl),pdfUtils.getTargetFolder(originalUrl));

        int page = pdfUtils.pdf2Image(pdfUtils.getServerPath(pdfUtils.getPDFUrl(originalUrl)),pdfUtils.getTargetFolder(originalUrl),96);
        Map<String, Object> data = new HashMap<>();
        data.put("src", originalUrl);
        data.put("pdfPath", pdfUtils.getPDFUrl(originalUrl));
        data.put("pngNum",page);
        data.put("pngList",pdfUtils.getPngUrl(originalUrl,page));
        return RespBody.data(data);
    }

}
