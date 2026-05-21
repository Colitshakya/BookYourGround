package com.util;

// Importing Part to handle uploaded files from multipart form data
import jakarta.servlet.http.Part;

// Importing IOException to handle file input/output errors
import java.io.IOException;

// Importing InputStream to read uploaded file content
import java.io.InputStream;

// Importing file path and file handling classes
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * FileUploadUtil is a utility class used for handling file uploads.
 *
 * This class provides reusable methods to:
 * - Get file extension
 * - Check whether uploaded file is an image
 * - Save uploaded file to a selected directory
 */
public class FileUploadUtil {

    /**
     * Extracts file extension from a file name.
     *
     * Example:
     * image.jpg returns .jpg
     *
     * @param fileName name of the uploaded file
     * @return file extension if available, otherwise empty string
     */
    public static String getFileExtension(String fileName) {
        // Checks whether file name is null or does not contain a dot
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }

        // Returns substring from the last dot to get extension
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * Checks whether the uploaded file is an image.
     *
     * @param part uploaded file part
     * @return true if uploaded file is an image, otherwise false
     */
    public static boolean isImage(Part part) {
        // Gets content type of uploaded file
        String contentType = part.getContentType();

        // Checks whether content type starts with image/
        return contentType != null && contentType.startsWith("image/");
    }

    /**
     * Saves uploaded file into the selected upload directory.
     *
     * @param part uploaded file part
     * @param uploadDir folder path where file should be saved
     * @param fileName final file name to save as
     * @throws IOException if file saving fails
     */
    public static void saveFile(Part part, String uploadDir, String fileName) throws IOException {
        // Converts upload directory string into Path object
        Path uploadPath = Paths.get(uploadDir);

        // Creates upload directory if it does not already exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Creates full file path using upload directory and file name
        Path filePath = uploadPath.resolve(fileName);

        // Gets input stream from uploaded file
        try (InputStream inputStream = part.getInputStream()) {
            // Copies uploaded file content to destination path
            // REPLACE_EXISTING overwrites file if same name already exists
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
