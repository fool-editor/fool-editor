package com.ooqn.assist.entity;

import java.util.List;

/**
 * @author tqf
 * @Description
 * @Version 1.0
 * @since 2022-04-20 14:57
 */
 
public class FileRes {

    /**
     * 文件名称、目录名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private Long size;
 
    /**
     * true-文件，false-目录
     */
    private Boolean isFile;

	private List<FileRes> fileResList;

	


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Boolean getIsFile() {
		return isFile;
	}

	public void setIsFile(Boolean isFile) {
		this.isFile = isFile;
	}

	public List<FileRes> getFileResList() {
		return fileResList;
	}

	public void setFileResList(List<FileRes> fileResList) {
		this.fileResList = fileResList;
	}
}