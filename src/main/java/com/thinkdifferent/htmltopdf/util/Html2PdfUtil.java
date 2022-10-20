//package com.thinkdifferent.htmltopdf.util;
//
//
//import com.itextpdf.io.source.ByteArrayOutputStream;
//import com.itextpdf.text.pdf.BaseFont;
//import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
//
//import com.openhtmltopdf.util.Configuration;
//import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.ResourceUtils;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.util.Locale;
//import java.util.Map;
//
//
///**
// * 使用Thymeleaf模板引擎，动态生成html，再使用openhtmltopdf将html转化为pdf。
// */
//
//public class Html2PdfUtil {
//    private static Logger logger = LoggerFactory.getLogger(Html2PdfUtil.class);
//    private static String DEFAULT_ENCODING="utf-8";
//    private static String PDF_TYPE="application/pdf";
//    private static boolean DEFAULT_NOCACHE=true;
//    private static String HEADER_ENCODING="utf-8";
//    private static String HEADER_NOCACHE="no-cache";
//
//
//    /**
//     * 生成PDF文件流
//     * @param ftlName 文件名称
//     * @param root	数据
//     * @return ByteArrayOutputStream
//     * @throws Exception
//     */
//    public static ByteArrayOutputStream createPDF(String ftlName, Object root) throws Exception {
//        //相对路径
//        File file = new File(Html2PdfUtil.class.getResource("/").getPath());
//        Configuration cfg = new Configuration();
//        try {
//            cfg.setLocale(Locale.CHINA);
//            cfg.setEncoding(Locale.CHINA, "UTF-8");
//            //设置编码
//            cfg.setDefaultEncoding("UTF-8");
//            //设置模板路径
//            cfg.setDirectoryForTemplateLoading(file);
//            //获取模板
//            Template template = cfg.getTemplate(ftlName);
//            template.setEncoding("UTF-8");
//            ITextRenderer iTextRenderer = new ITextRenderer();
//            //设置字体
//            ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
//            logger.info("获取的路径:"+file.getPath());
//            fontResolver.addFont(file.getPath() + "/public/font/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//
//            Writer writer = new StringWriter();
//            //数据填充模板
//            template.process(root, writer);
//            //设置输出文件内容及路径
//            String str = writer.toString();
//            iTextRenderer.setDocumentFromString(str);
//            /*iTextRenderer.getSharedContext().setBaseURL("");//共享路径*/
//            iTextRenderer.layout();
//            //生成PDF
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            iTextRenderer.createPDF(baos);
//            baos.close();
//            return baos;
//        } catch(Exception e) {
//            throw new Exception(e);
//        }
//    }
//    public static void renderPdf(HttpServletResponse response, final byte[] bytes, final String filename) {
//        initResponseHeader(response, PDF_TYPE);
//        setFileDownloadHeader(response, filename, ".pdf");
//        if (null != bytes) {
//            try {
//                response.getOutputStream().write(bytes);
//                response.getOutputStream().flush();
//            } catch (IOException e) {
//                throw new IllegalArgumentException(e);
//            }
//        }
//    }
//
//    /**
//     * 分析并设置contentType与headers.
//     */
//    private static HttpServletResponse initResponseHeader(HttpServletResponse response, final String contentType, final String... headers) {
//        // 分析headers参数
//        String encoding = DEFAULT_ENCODING;
//        boolean noCache = DEFAULT_NOCACHE;
//        for (String header : headers) {
//            String headerName = StringUtils.substringBefore(header, ":");
//            String headerValue = StringUtils.substringAfter(header, ":");
//            if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
//                encoding = headerValue;
//            } else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
//                noCache = Boolean.parseBoolean(headerValue);
//            } else {
//                throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
//            }
//        }
//        // 设置headers参数
//        String fullContentType = contentType + ";charset=" + encoding;
//        response.setContentType(fullContentType);
//        if (noCache) {
//            // Http 1.0 header
//            response.setDateHeader("Expires", 0);
//            response.addHeader("Pragma", "no-cache");
//            // Http 1.1 header
//            response.setHeader("Cache-Control", "no-cache");
//        }
//        return response;
//    }
//
//    /**
//     * 设置让浏览器弹出下载对话框的Header.
//     * @param
//     */
//    public static void setFileDownloadHeader(HttpServletResponse response, String fileName, String fileType) {
//        try {
//            // 中文文件名支持
//            String encodedfileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + fileType + "\"");
//        } catch (UnsupportedEncodingException e) {
//        }
//    }
//
//
//
//    /**
//     * @param response     http请求后的相应
//     * @param pdfFileName  pdf文件名称(不包含pdf后缀)
//     * @param templateName 模板名称
//     * @param variables    模板变量
//     */
//    public static void buildPdf(HttpServletResponse response, String pdfFileName, String templateName, Map<String, Object> variables) throws Exception {
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "filename=" + new String((pdfFileName + ".pdf").getBytes(), "iso8859-1"));
//        OutputStream os = response.getOutputStream();
//        //构造模板引擎
//        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//        resolver.setPrefix("templates/");//模板所在目录，相对于当前classloader的classpath。
//        resolver.setSuffix(".html");//模板文件后缀
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(resolver);
//        //构造上下文(Model)
//        Context context = new Context();
//        context.setVariable("templateName", templateName);
//        context.setVariable("pdfFileName", pdfFileName);
//        context.setVariables(variables);
//        //渲染模板
//        String example = templateEngine.process("parent", context);
//        PdfRendererBuilder builder = new PdfRendererBuilder();
//        builder.useFont(ResourceUtils.getFile("classpath:fonts/simsun.ttf"), "simsun");
//        builder.useFastMode();
//        builder.withHtmlContent(example, ResourceUtils.getURL("classpath:pdf/img/").toString());
//        builder.toStream(os);
//        builder.run();
//    }
//}
