package cn.hj.xlsServer.reponsitory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.hj.xlsServer.bean.model.TemplateConfig;
import cn.hj.xlsServer.util.StringUtil;
import cn.hj.xlsServer.xls.config.XlsReadConfig;

@Repository
public class ConfigReponsitory {
	private JdbcTemplate jdbc;
	@Autowired
	public ConfigReponsitory(JdbcTemplate jdbc){
		this.jdbc = jdbc;
	}
	
	public List<XlsReadConfig> getXlsReadConfig(){
		List<XlsReadConfig> results = new ArrayList<XlsReadConfig>();
		results = jdbc.query("select id,templateName,sheetName,beginRow,endRowFlag,"
				+ "beginColum,endColum,summary from xlsReadConfig ", new RowMapper<XlsReadConfig>() {
			public XlsReadConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
				XlsReadConfig row = new XlsReadConfig();
				row.setId(rs.getLong("id"));
				row.setTemplateName(rs.getString("templateName"));
				row.setsheetName(rs.getString("sheetName"));
				row.setBeginRow(rs.getInt("beginRow"));
				row.setEndRowFlag(rs.getString("endRowFlag"));
				row.setBeginColum(rs.getInt("beginColum"));
				row.setEndColum(rs.getInt("endColum"));
				row.setSummary(rs.getString("summary"));
				return row;
			}
		});
		return results;
	}
	
	public XlsReadConfig getXlsReadConfig(String templateName){
		XlsReadConfig result = jdbc.queryForObject("select id,templateName,sheetNo,beginRow,endRowFlag,"
				+ "beginColum,endColum,summary "
				+ " from xlsReadConfig where templateName = ? ",
				new Object[]{templateName}, new RowMapper<XlsReadConfig>(){
					@Override
					public XlsReadConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
						XlsReadConfig config = new XlsReadConfig();
						config.setId(rs.getLong("id"));
						config.setTemplateName(rs.getString("templateName"));
						config.setsheetName(rs.getString("sheetName"));
						config.setBeginRow(rs.getInt("beginRow"));
						config.setEndRowFlag(rs.getString("endRowFlag"));
						config.setBeginColum(rs.getInt("beginColum"));
						config.setEndColum(rs.getInt("endColum"));
						config.setSummary(rs.getString("summary"));
						return config;
					}
				});
		return result;
	}
	
	public void updateXlsReadConfig(XlsReadConfig config){
		if(StringUtil.isNotEmpty(config.getTemplateName())){
			jdbc.update("update xlsReadConfig set sheetNo = ?,beginRow=?,endRowFlag=?,"
					+ "beginColum = ?,endColum =?,summary =? where templateName = ? ",
					new Object[]{config.getsheetName(),config.getBeginRow(),config.getEndRowFlag(),
						config.getBeginColum(),config.getEndColum(),config.getSummary(),config.getTemplateName()});
		}
	}
	public void saveXlsReadConfig(XlsReadConfig config){
		if(StringUtil.isNotEmpty(config.getTemplateName())){
			jdbc.update("insert xlsReadConfig(templateName,sheetNo,beginRow,endRowFlag,"
					+ "beginColum,endColum,summary)"
					+ "values(?,?,?,?  ,?,?,?)",
					new Object[]{config.getTemplateName(),config.getsheetName(),config.getBeginRow(),config.getEndRowFlag(),
						config.getBeginColum(),config.getEndColum(),config.getSummary()});
		}
	}
	
	
	
	public List<TemplateConfig> getTemplateConfig(){
		List<TemplateConfig> results = new ArrayList<TemplateConfig>();
		results = jdbc.query("select id,templateName,templatePath,summary from templateConfig ", new RowMapper<TemplateConfig>() {
			public TemplateConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
				TemplateConfig row = new TemplateConfig();
				row.setId(rs.getLong("id"));
				row.setTemplateName(rs.getString("templateName"));
				row.setTemplatePath(rs.getString("templatePath"));
				row.setSummary(rs.getString("summary"));
				return row;
			}
		});
		return results;
	}
	
}
