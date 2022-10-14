package com.thinkdifferent.convertpic.controller;

import com.thinkdifferent.convertpic.config.ConvertPicConfig;
import com.thinkdifferent.convertpic.service.ConvertPicService;
import com.thinkdifferent.convertpic.service.RabbitPicMQService;
import com.thinkdifferent.mq.config.RabbitMQConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;

/**
 * 将常见的图片文件转换为Jpg或Pdf文件Controller
 *
 * @author json
 * @date 2022-10-14
 */
@Api(tags = "根据传入的JSON参数生成Jpg/Pdf文件")
@RestController
@RequestMapping(value = "/pic/api")
public class ConvertPic {

    @Autowired
    private ConvertPicService convertPicService;

    @Autowired
    private RabbitPicMQService rabbitMQService;

    /**
     * 接收传入的JSON数据，将源图片文件转换为Jpg、Pdf文件；按照传入的设置，将文件回写到指定位置
     *
     * @param jsonInput 输入的JSON对象
     *                  {
     *                  "inputType": "path",
     *                  "inputFile": "D:/cvtest/001.tif",
     *                  "inputHeaders":
     *                  {
     *                  "Authorization":"Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0"
     *                  },
     *                  "outPutFileName": "001-online",
     *                  "outPutFileType": "jpg",
     *                  "writeBackType": "path",
     *                  "writeBack":
     *                  {
     *                  "path":"D:/cvtest/"
     *                  },
     *                  "writeBackHeaders":
     *                  {
     *                  "Authorization":"Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0"
     *                  },
     *                  "callBackURL": "http://10.11.12.13/callback"
     *                  }
     * @return JSON结果
     */
    @ApiOperation("接收传入的JSON数据，将源图片文件转换为Jpg文件")
    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public Map<String, String> convert2Jpg(@RequestBody JSONObject jsonInput) {
        JSONObject jsonReturn = new JSONObject();

        if (!RabbitMQConfig.producer) {
            jsonReturn = convertPicService.ConvertPic(jsonInput);
        } else {
            jsonReturn.put("flag", "success");
            jsonReturn.put("message", "Set Data to MQ Success");

            rabbitMQService.setData2MQ(jsonInput);
        }

        return jsonReturn;
    }

    /**
     * 接收传入的JSON数据，将源图片文件转换为Jpg、Pdf文件，并以Base64字符串输出。
     * 本接口只能返回一种格式的转换结果文件
     *
     * @param jsonInput 输入的JSON对象
     *                  {
     *                  "inputType": "path",
     *                  "inputFile": "D:/cvtest/001.tif",
     *                  "inputHeaders":
     *                  {
     *                  "Authorization":"Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0"
     *                  },
     *                  "outPutFileName": "001-online",
     *                  "outPutFileType": "jpg"
     *                  }
     * @return JSON结果
     */
    @ApiOperation("接收传入的JSON数据，将源图片文件转换为Jpg文件，并以Base64字符串输出")
    @RequestMapping(value = "/convert2base64", method = RequestMethod.POST)
    public String convert2Base64(@RequestBody JSONObject jsonInput) {

        JSONObject jsonReturn = convertPicService.ConvertPic(jsonInput);

        if ("success".equalsIgnoreCase(jsonReturn.getString("flag"))) {
            String strPath = ConvertPicConfig.outPutPath;
            strPath = strPath.replaceAll("\\\\", "/");
            if (!strPath.endsWith("/")) {
                strPath = strPath + "/";
            }

            String strOutPutFileName = jsonInput.getString("outPutFileName");
            String strOutPutFileType = jsonInput.getString("outPutFileType");

            String strJpgFilePathName = strPath + strOutPutFileName + "." + strOutPutFileType;
            File fileJpg = new File(strJpgFilePathName);
            if (fileJpg.exists()) {
                try {
                    byte[] b = Files.readAllBytes(Paths.get(strJpgFilePathName));
                    // 文件转换为字节后，转换后的文件即可删除（jpg没用了）。
                    fileJpg.delete();
                    return Base64.getEncoder().encodeToString(b);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    /**
     * 接收传入的JSON数据，将源图片文件转换为Jpg、Pdf文件，并以Base64字符串输出。
     * 本接口可以返回Jpg和Pdf格式的转换结果文件
     *
     * @param jsonInput 输入的JSON对象
     *                  {
     *                  "inputType": "path",
     *                  "inputFile": "D:/cvtest/001.tif",
     *                  "inputHeaders":
     *                  {
     *                  "Authorization":"Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0"
     *                  },
     *                  "outPutFileName": "001-online",
     *                  "outPutFileType": "jpg,pdf"
     *                  }
     * @return JSON结果
     */
    @ApiOperation("接收传入的JSON数据，将源图片文件转换为Jpg文件，并以Base64字符串输出")
    @RequestMapping(value = "/convert2base64s", method = RequestMethod.POST)
    public JSONObject convert2Base64s(@RequestBody JSONObject jsonInput) {

        JSONObject jsonReturn = convertPicService.ConvertPic(jsonInput, true);

        if ("success".equalsIgnoreCase(jsonReturn.getString("flag"))) {
            JSONArray jsonArrayFile = new JSONArray();

            String strOutPutFileName = jsonInput.getString("outPutFileName");
            String strOutPutFileType = jsonInput.getString("outPutFileType");
            String strTypes = "";

            if (strOutPutFileType.indexOf("jpg") > -1) {
                strTypes = strTypes + "jpg;";
            }
            if (strOutPutFileType.indexOf("pdf") > -1) {
                strTypes = strTypes + "pdf;";
            }

            String strPath = ConvertPicConfig.outPutPath;
            strPath = strPath.replaceAll("\\\\", "/");
            if (!strPath.endsWith("/")) {
                strPath = strPath + "/";
            }

            if (!"".equals(strTypes)) {
                String[] strType = strTypes.split(";");
                for (int i = 0; i < strType.length; i++) {
                    String strFilePathName = strPath + strOutPutFileName + "." + strType[i];
                    File fileOutPut = new File(strFilePathName);
                    if (fileOutPut.exists()) {
                        try {
                            byte[] b = Files.readAllBytes(Paths.get(strFilePathName));
                            JSONObject jsonObjectPDF = new JSONObject();
                            jsonObjectPDF.put("filename", fileOutPut.getName());
                            jsonObjectPDF.put("base64", Base64.getEncoder().encodeToString(b));
                            jsonArrayFile.add(jsonObjectPDF);
                            // 转换为byte后，PDF文件即可删除
                            fileOutPut.delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                jsonReturn.put("base64", jsonArrayFile);
            }


        }

        return jsonReturn;
    }

}
