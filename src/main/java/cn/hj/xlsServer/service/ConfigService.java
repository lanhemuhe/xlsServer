package cn.hj.xlsServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hj.xlsServer.bean.model.FileTemplateConfig;
import cn.hj.xlsServer.bean.model.TemplateConfig;
import cn.hj.xlsServer.reponsitory.FileTemplateConfigReponsitory;
import cn.hj.xlsServer.reponsitory.TemplateConfigReponsitory;
@Service
public class ConfigService {
	@Autowired
	private TemplateConfigReponsitory templateRep;
	@Autowired
	private FileTemplateConfigReponsitory fileTemplateRep;
	

	/**
	 * 模板相关配置
	 */
	public List<TemplateConfig> getTemplateConfigList(){
		return templateRep.findAll();
	}
	public void saveTemplateConfig(TemplateConfig config) {
		// 若存在同名模板，先删除再插入
		TemplateConfig existConfig = templateRep.readByTemplateName(config.getTemplateName());
		if(existConfig!=null){
			templateRep.delete(existConfig.getId());
		}
		templateRep.save(config);
	}
	public TemplateConfig getTemplateConfig(String templateName){
		return templateRep.readByTemplateName(templateName);
	}
	/**
	 * 文件相关配置
	 */
	public List<FileTemplateConfig> getFileConfigList(){
		return fileTemplateRep.findAll();
	}
	public void saveFileTemplateConfig(FileTemplateConfig config) {
		// 若存在同名文件配置，先删除再插入
		FileTemplateConfig existConfig = fileTemplateRep.readByFileName(config.getFileName());
		if(existConfig!=null){
			fileTemplateRep.delete(existConfig.getId());
		}
		fileTemplateRep.save(config);
	}
	
	public FileTemplateConfig getFileTemplateConfig(String fileName){
		return fileTemplateRep.readByFileName(fileName);
	}
	
}
