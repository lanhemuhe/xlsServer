package cn.hj.xlsServer.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.hj.xlsServer.bean.model.TemplateConfig;

public interface TemplateConfigReponsitory extends JpaRepository<TemplateConfig,Long>{
	public TemplateConfig readByTemplateName(String name);
}
