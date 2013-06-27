package com.netand.bookstore;

public class VO_NBS {
	
	public static String FILENAME = "filename";
	public static String CATEGORY = "category";
	public static String CONTENTS = "contents";
	
	
	private String filename;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	private String category;
	private String contents;
	
	
	private String highlight;
	public String getHighlight() {
		return highlight;
	}
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}
	
	public String toString(){
		return "[VO_NBS] filename:" + filename + ",category:" + category + ",contents:" + contents;
	}
	
}
