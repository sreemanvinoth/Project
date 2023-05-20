package com.cd.translatewords.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cd.translatewords.service.TranslateService;

@Controller
@RequestMapping("/file")
public class TranslateController {

	@Autowired
	private TranslateService service;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView downloadFileFormDisplay() {

		return new ModelAndView("success");

	}

	@RequestMapping("/translate")
	public String getSignUp() {

		return "fileUpload";
	}

	@PostMapping("/upload")
	public String translateFile(@RequestParam CommonsMultipartFile file, ModelMap map, HttpSession session) {

		// Translating text file
		String convertedFiles = service.translateFiles(file);

		if (convertedFiles.isBlank()) {
			System.out.println("File Exists");
			map.addAttribute("error", "File exists");
			map.addAttribute("uploaded", "File Translation InComplete");
			return "success";
		} else {
			map.addAttribute("uploaded", "Translate to French Successful");
			return "success";
		}

	}

}
