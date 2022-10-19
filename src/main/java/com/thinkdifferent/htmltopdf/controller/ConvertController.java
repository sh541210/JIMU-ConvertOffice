package com.thinkdifferent.htmltopdf.controller;

import com.thinkdifferent.htmltopdf.util.Html2PdfUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "html转Pdf文件")
@RestController
@RequestMapping(value = "/htmltopdf/api")
public class ConvertController {

    @GetMapping("pdf")
    public void test(HttpServletResponse response) throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "表格标题");
        variables.put("array", new String[]{"土豆", "番茄", "白菜", "芹菜", "土豆", "番茄", "白菜", "芹菜", "芹菜", "土豆",
                "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆",
                "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆",
                "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆",
                "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆", "番茄", "白菜", "芹菜", "土豆",
                "番茄", "白菜", "芹菜"});
        Html2PdfUtil.buildPdf(response, "用于测试的pdf", "example", variables);
    }
}
