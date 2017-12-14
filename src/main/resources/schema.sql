--	CREATE DATABASE if not exists `xlsConfig`;
  
   CREATE  TABLE if not exists `fileTemplateConfig` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `fileName` VARCHAR(255)  not NULL,
  `sheetName` VARCHAR(255) NOT NULL  ,
  `templateName` VARCHAR(255) not NULL ,
  `filePath` VARCHAR(512) not NULL ,
  `summary` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) 
  );
  
   CREATE  TABLE if not exists `templateConfig` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `templateName` VARCHAR(255) not NULL ,
  `templatePath` VARCHAR(512) not NULL ,
  `summary` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) 
  );