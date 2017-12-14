package cn.hj.xlsServer.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.hj.xlsServer.bean.model.FileTemplateConfig;
import cn.hj.xlsServer.bean.model.TemplateConfig;

public interface FileTemplateConfigReponsitory extends JpaRepository<FileTemplateConfig,Long>{
	public FileTemplateConfig readByFileName(String name);
}
