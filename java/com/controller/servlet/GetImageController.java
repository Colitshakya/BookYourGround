package com.controller.servlet;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing File class to work with image files and folders
import java.io.File;

// Importing IOException to handle input/output errors
import java.io.IOException;

// Importing Files class to copy image file data to the response output stream
import java.nio.file.Files;

/**
 * GetImageController is responsible for loading uploaded images from the server.
 *
 * URL Mapping:
 * /getImage
 *
 * This controller:
 * - Receives an image name from the request parameter
 * - Searches for the matching image inside the upload folder
 * - Sets the correct image content type
 * - Sends the image file back to the browser
 */
@WebServlet("/getImage")
public class GetImageController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Folder path where uploaded images are stored inside the user's home directory
    private static final String UPLOAD_DIR =
            System.getProperty("user.home") + File.separator + "bookyourground_uploads";

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the browser requests an uploaded image.
     * It checks the requested image name, searches the upload directory,
     * and returns the image file as a response.
     *
     * @param request  stores request data sent by the browser
     * @param response sends image data or error response back to the browser
     * @throws ServletException if servlet processing fails
     * @throws IOException if file reading or output writing fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Gets image name from the request parameter
        String name = request.getParameter("name");

        // If image name is missing, send bad request error
        if (name == null || name.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing image name.");
            return;
        }

        // Creates a File object for the upload folder
        File folder = new File(UPLOAD_DIR);

        // Checks whether the upload folder exists and is a valid directory
        if (!folder.exists() || !folder.isDirectory()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image folder not found.");
            return;
        }

        // Searches for files that start with the requested image name followed by a dot
        // Example: if name is "court1", it can match court1.jpg or court1.png
        File[] matches = folder.listFiles((dir, fileName) -> fileName.startsWith(name + "."));

        // If matching image files are found, use the first matched file
        if (matches != null && matches.length > 0) {
            File imageFile = matches[0];

            // Gets the MIME type of the image based on file name
            String contentType = getServletContext().getMimeType(imageFile.getName());

            // If MIME type cannot be detected, set default content type as image/jpeg
            if (contentType == null) {
                contentType = "image/jpeg";
            }

            // Sets response content type so browser understands it is an image
            response.setContentType(contentType);

            // Sets image file size in the response
            response.setContentLength((int) imageFile.length());

            // Copies image file data to the response output stream
            Files.copy(imageFile.toPath(), response.getOutputStream());

        } else {
            // Sends not found error if no matching image is available
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found.");
        }
    }
}
