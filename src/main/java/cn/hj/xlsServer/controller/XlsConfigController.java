package cn.hj.xlsServer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.hj.xlsServer.bean.ReturnValue;
import cn.hj.xlsServer.bean.model.FileTemplateConfig;
import cn.hj.xlsServer.bean.model.TemplateConfig;
import cn.hj.xlsServer.service.ConfigService;
import cn.hj.xlsServer.xls.XlsTools;
import cn.hj.xlsServer.xls.constant.ConfigConstants;


@Controller
@RequestMapping("/xlsConfig")
public class XlsConfigController {
	@Autowired
	private ConfigService configService;
	@Autowired
	public XlsConfigController(){
	}
	@RequestMapping(method=RequestMethod.GET)
	public String show(Model model){
		ReturnValue result  = new ReturnValue();
		result.setResult(0);
		result.setMsg("测试成功！");
		model.addAttribute("result", result);
		return "fileConfig";
	}
	/**
	 * 返回模板信息列表
	 * @return
	 */
	@RequestMapping(value="/templateList",method=RequestMethod.GET)
	public ResponseEntity<List<TemplateConfig>> templateList(){
		List<TemplateConfig> templateList = configService.getTemplateConfigList();
		HttpStatus status = templateList==null?HttpStatus.NOT_FOUND:HttpStatus.OK;
		ResponseEntity<List<TemplateConfig>> result = 
				new ResponseEntity<List<TemplateConfig>>(templateList, status);
		return result;
	}
	/**
	 * 返回数据文件列表
	 * @return
	 */
	@RequestMapping(value="/fileList",method=RequestMethod.GET)
	public ResponseEntity<List<FileTemplateConfig>> dataFileList(){
		List<FileTemplateConfig> fileList = configService.getFileConfigList();
		HttpStatus status = fileList==null?HttpStatus.NOT_FOUND:HttpStatus.OK;
		ResponseEntity<List<FileTemplateConfig>> result = 
				new ResponseEntity<List<FileTemplateConfig>>(fileList, status);
		return result;
	}
	@RequestMapping(value="/uploadXls",method=RequestMethod.POST)
	public String UploadXls(
			@RequestParam(value="sheetName",defaultValue="") String sheetName,
			Model model,
			@RequestParam("dataFile") MultipartFile dataFile,
			@RequestParam("templateFile") MultipartFile templateFile) throws Exception{
	    FileInputStream fs;
	    String templateFileName,dataFileName,templateFilePath,dataFilePath;
	    ReturnValue result = new ReturnValue();
	    result.setResult(-1);
	    
	    templateFileName = templateFile.getOriginalFilename();
	    dataFileName = dataFile.getOriginalFilename();
		templateFilePath = ConfigConstants.TEMPLATEPATH+templateFileName;	//模板文件保存路径
		dataFilePath = ConfigConstants.DATAPATH + dataFileName;				//数据文件保存路径
		// 保存文件
		fs = (FileInputStream) templateFile.getInputStream();
		XlsTools.saveFile(fs,templateFilePath);
		fs = (FileInputStream) dataFile.getInputStream();
		XlsTools.saveFile(fs,dataFilePath);
		// 保存模板路径配置
		TemplateConfig config = new TemplateConfig();
		config.setTemplateName(templateFileName.substring(0, templateFileName.indexOf(".")));
		config.setTemplatePath(templateFilePath);
		configService.saveTemplateConfig(config);
		// 保存数据文件配置
		FileTemplateConfig fileConfig = new FileTemplateConfig();
		fileConfig.setFileName(dataFileName.substring(0, dataFileName.indexOf(".")));
		fileConfig.setFilePath(dataFilePath);
		fileConfig.setSheetName(sheetName);
		fileConfig.setTemplateName(config.getTemplateName());
		configService.saveFileTemplateConfig(fileConfig);
		result.setResult(0);
		result.setMsg("文件上传成功！");
		model.addAttribute("result", result);
		return "fileConfig";
	}
}
