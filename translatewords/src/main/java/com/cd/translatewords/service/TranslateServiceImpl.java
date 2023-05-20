package com.cd.translatewords.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.opencsv.CSVWriter;

@Service
public class TranslateServiceImpl implements TranslateService {

	private static final String DICTIONARY_FILE_PATH = "E:\\D\\JAVA\\project\\TranslateWords Challenge\\french_dictionary.csv";
	private static final String SOURCE_FILE_PATH = "E:\\D\\JAVA\\project\\TranslateWords Challenge\\source/";
	private static final String TRANSLATED_FILE_PATH = "E:\\D\\JAVA\\project\\TranslateWords Challenge\\translate/";

	private static BufferedReader dictionaryReader;
	private static Map<String, Integer> wordFrequecy = new LinkedHashMap<>();
	private static Map<String, String> dictionaryFile = new LinkedHashMap<>();

	static {
		try {
			loadDictionary();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String translateFiles(CommonsMultipartFile file) {

		Long startTime = System.currentTimeMillis();
		Long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		File inputFile = new File(SOURCE_FILE_PATH + file.getOriginalFilename());
		File outputFile = new File(
				TRANSLATED_FILE_PATH + file.getOriginalFilename().replaceFirst(".txt", ".translated.txt"));
		try {
			// Check if the file exists or not
			if (!inputFile.exists()) {
				file.transferTo(inputFile);
			} else {
				return "";
			}

			try (// comparing the dictionary.csv file to .TXT
					BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

				String textLine;

				while ((textLine = reader.readLine()) != null) {
					String translatedLine = translateLine(textLine);

					writer.write(translatedLine);
					writer.newLine();

				}
				writer.flush();
				writer.close();
			}
			
			Long endTime = System.currentTimeMillis();
			Long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			Long elapsedTime = endTime - startTime;
			Double memoryUsed = Double.valueOf(((endMemory - startMemory) / 1024) / 1024);

			File performance = new File(TRANSLATED_FILE_PATH + "performance.txt");
			BufferedWriter performanceWiter = new BufferedWriter(new FileWriter(performance));
			if (elapsedTime > 100000) {
				performanceWiter.write("Time to process:" + (elapsedTime / 1000) / 60 + " minutes "
						+ (elapsedTime / 1000) % 60 + " seconds");
			} else {
				performanceWiter.write(
						"Time to process:" + (elapsedTime / 1000) / 60 + " minutes " + elapsedTime + " milliSeconds");
			}
			performanceWiter.newLine();
			performanceWiter.write("Memory used:" + memoryUsed + "mb");
			performanceWiter.flush();
			performanceWiter.close();

			printWordFrequency(wordFrequecy);

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return outputFile.getPath();

	}

	private String translateLine(String textLine) throws IOException {

		StringBuilder translatedLine = new StringBuilder();

		String translatedWord;
		String[] words = textLine.split("\\s+");
		for (String word : words) {
			translatedWord = dictionaryFile.getOrDefault(word.toLowerCase(), word);

			// Update frequency count if the word is replaced
			if (!word.equals(translatedWord)) {
				updateFrequency(wordFrequecy, translatedWord);
			}
			if (translatedWord != null) {
				translatedLine.append(translatedWord + " ");
			} else {
				translatedLine.append(word + " ");

			}

		}
		return translatedLine.toString();
	}

	private static void loadDictionary() throws IOException {
		dictionaryReader = new BufferedReader(new FileReader(DICTIONARY_FILE_PATH));
		String line;

		// updating the dictionary file
		while ((line = dictionaryReader.readLine()) != null) {
			String[] words = line.split(",");
			if (words.length == 2) {
				String englishWord = words[0].trim().toLowerCase();
				String frenchWord = words[1].trim();
				dictionaryFile.put(englishWord, frenchWord);
			}
		}

	}

	private void printWordFrequency(Map<String, Integer> wordFrequency) {
		// write the frequency in CSV file
		FileWriter excelFile;
		try {
			excelFile = new FileWriter(new File(TRANSLATED_FILE_PATH + "frequency.csv"));

			CSVWriter csvWriter = new CSVWriter(excelFile);

			// Header for CSV file
			String[] csvHeader = { "English Word", "French Word", "Frequency" };
			csvWriter.writeNext(csvHeader);

			// Array of String to write in CSV file
			for (Map.Entry<String, String> dictionaryEntry : dictionaryFile.entrySet()) {
				String[] set = { dictionaryEntry.getKey(), dictionaryEntry.getValue(), "0" };

				for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
					if (set[1].equals(entry.getKey())) {
						set[2] = String.valueOf(entry.getValue());
					}
				}

				csvWriter.writeNext(set);
			}
			csvWriter.flush();
			csvWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void updateFrequency(Map<String, Integer> wordFrequecy, String word) {
		wordFrequecy.put(word, wordFrequecy.getOrDefault(word, 0) + 1);

	}

}
