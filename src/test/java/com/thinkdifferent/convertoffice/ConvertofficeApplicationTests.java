package com.thinkdifferent.convertoffice;

import cn.hutool.json.JSONObject;
import com.thinkdifferent.convertoffice.service.ConvertOfficeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConvertofficeApplicationTests {
    @Autowired
    private ConvertOfficeService convertOfficeService;

   /* @param jsonInput 输入的JSON对象
     *                  {
     *                  "inputType": "path",
     *                  "inputFile": "D:/1.docx",
     *                  "inputHeaders":
     *                  {
     *                  "Authorization":"Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0"
     *                  },
     *                  "outPutFileName": "1-online",
     *                  "outPutFileType": "ofd",
     *                  "writeBackType": "path",
     *                  "writeBack":
     *                  {
     *                  "path":"D:/"
     *                  },
     *                  "writeBackHeaders":
     *                  {
     *                  "Authorization":"Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0"
     *                  },
     *                  "callBackURL": "http://10.11.12.13/callback"
     *                  }
    */
    @Test
    void contextLoads() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("inputType","path");
        jsonObject.putOpt("inputFile","");
        jsonObject.putOpt("outPutFileName","");
        jsonObject.putOpt("","");
        jsonObject.putOpt("","");
        jsonObject.putOpt("","");
        jsonObject.putOpt("","");
        jsonObject.putOpt("","");
        convertOfficeService.ConvertOffice(jsonObject);

    }

}
