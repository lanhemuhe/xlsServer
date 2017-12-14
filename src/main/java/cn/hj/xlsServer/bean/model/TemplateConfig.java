package cn.hj.xlsServer.bean.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="templateconfig")  //数据库中对应表名
public class TemplateConfig {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="templateName")
	private String templateName;
	@Column(name="templatePath")
	private String templatePath;
	@Column(name="summary") 
	private String summary;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
