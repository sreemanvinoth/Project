package com.cd.translatewords.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface TranslateService {

	String translateFiles(CommonsMultipartFile file);

}
