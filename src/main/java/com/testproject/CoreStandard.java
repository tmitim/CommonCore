// Object for CoreStandard Descriptions

package com.testproject;

import java.util.ArrayList;
import java.util.List;

public class CoreStandard {
	String commonCoreCode;
	String commonCoreDescription;
	String commonCoreReferenceUrl;

	List<String> url;

	public CoreStandard(String commonCoreCode, String commonCoreDescription,
			String commonCoreReferenceUrl) {
		super();
		this.commonCoreCode = commonCoreCode;
		this.commonCoreDescription = commonCoreDescription;
		this.commonCoreReferenceUrl = commonCoreReferenceUrl;
		this.url = new ArrayList<String>();
	}

	public String getCommonCoreDescription() {
		return commonCoreDescription;
	}

	public List<String> getUrl() {
		return url;
	}

	public void setUrl(List<String> url) {
		this.url = url;
	}

	public String getCommonCoreCode() {
		return commonCoreCode;
	}

	public String getCommonCoreReferenceUrl() {
		return commonCoreReferenceUrl;
	}

}
