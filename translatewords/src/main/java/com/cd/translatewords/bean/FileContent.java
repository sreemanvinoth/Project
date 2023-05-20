package com.cd.translatewords.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileContent {

	private String englishName;
	
	private String frenchName;
	
	private Integer Frequency;
	
}
