package cn.hj.xlsServer.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hj.xlsServer.bean.model.FileTemplateConfig;
import cn.hj.xlsServer.bean.model.TemplateConfig;
import cn.hj.xlsServer.service.ConfigService;
import cn.hj.xlsServer.util.DataUtil;
import cn.hj.xlsServer.xls.XlsTools;
import cn.hj.xlsServer.xls.constant.ConfigConstants;
import net.sf.json.JSONArray;

@RestController
@RequestMapping(value="/xls")
public class XlsController {
	@Autowired
	ConfigService configService;

	@RequestMapping(method=RequestMethod.GET)
	public String show(){
		return "fileConfig";
	}
	@RequestMapping(value="/read",method=RequestMethod.POST)
	public List<Map<String,String>> read(@RequestParam(name="fileName",defaultValue="") String fileName)
		throws Exception{
		FileTemplateConfig config = configService.getFileTemplateConfig(fileName);
		TemplateConfig templateConfig = configService.getTemplateConfig(config.getTemplateName());
		List<Map<String,String>> datas = null;
		datas = XlsTools.getXlsData(config.getFilePath(), templateConfig.getTemplatePath(), config.getSheetName());
		return datas;
	}
	@RequestMapping(value="/write",method=RequestMethod.POST)
	public ResponseEntity<String> write(@RequestParam(name="datas",defaultValue="") String datas,
			@RequestParam(name="fileTemplateName",defaultValue="") String templateName,
			@RequestParam(name="sheetName",defaultValue="") String sheetName)
		throws Exception{
		String fileName = DataUtil.fmtTime(new Date());
		//将JSON字符串转换为List<Map<>>对象
		JSONArray jsonArray = JSONArray.fromObject(datas); 
        List<Map<String,String>> mapListJson = (List)jsonArray;  
        //获取模板信息
		TemplateConfig templateConfig = configService.getTemplateConfig(templateName);
		//生成Excel
		File file = XlsTools.writeXlsData(fileName, templateConfig.getTemplatePath(), sheetName, mapListJson);
		ResponseEntity<String> response = new ResponseEntity<String>(file.getName(),HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public void download(HttpServletResponse response,
			@RequestParam(value="fileName",defaultValue="")String fileName)
		throws Exception{
		String filePath = ConfigConstants.DOWNLOADPATH+fileName;
		response.setHeader("conent-type", "application/octet-stream");  
        response.setContentType("application/octet-stream");  
        response.setHeader("Content-Disposition", "attachment; filename=" 
        		// 防止下载时名称乱码
        		+ java.net.URLEncoder.encode(fileName, "UTF-8"));
          
        OutputStream os = response.getOutputStream();  
        BufferedOutputStream bos = new BufferedOutputStream(os);  
          
        InputStream is = null;  
        is = new FileInputStream(filePath);  
        BufferedInputStream bis = new BufferedInputStream(is);  

        int length = 0;  
        byte[] temp = new byte[1 * 1024 * 10];  

        while ((length = bis.read(temp)) != -1) {  
            bos.write(temp, 0, length);  
        }  
        bos.flush();  
        bis.close();  
        bos.close();  
        is.close();         
	}
}
