package org.fga.tcc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fga.tcc.entities.OpenDataBaseResponse;
import org.fga.tcc.json.FetchJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static void saveFile(String filePath, String fileContent) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            File file = createFile(filePath);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, mapper.readValue(fileContent, OpenDataBaseResponse.class));
        } catch (IOException e) {
            System.out.println("Error saving json file");
        }
    }

    public static void saveTxtFile(String filePath, String fileContent) {
        ObjectMapper mapper = new ObjectMapper();

        if (fileContent != null && !fileContent.isEmpty()) {
            String normalizedRow = fileContent
                    .replaceAll("\"", "")
                    .replaceAll("\\n", "")
                    .replaceAll("\\t", "")
                    .replaceAll("\\r", "")
                    .trim();

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
                bufferedWriter.write(normalizedRow);
                LOGGER.info("Data saved in txt: " + filePath);
            } catch(IOException e) {
                System.out.println("Error saving txt file");
            }
        } else {
            System.out.println("There is no data to be saved in txt file.");
        }
    }

    public static File createFile(String filePath) {
        File file = new File(filePath);

        // Create subdirectories if needed
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        return file;
    }

    public static boolean isFileAlreadyCreated(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

}
