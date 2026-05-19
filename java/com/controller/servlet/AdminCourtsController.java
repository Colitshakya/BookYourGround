package com.controller.servlet;

import com.model.CourtModel;
import com.service.AdminCourtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/adminCourts")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AdminCourtsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminCourtsController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            AdminCourtService service = new AdminCourtService();

            request.setAttribute("activePage", "courts");
            request.setAttribute("courtList", service.getAllCourts());

            request.getRequestDispatcher("/pages/adminCourts.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("activePage", "courts");
            request.setAttribute("errorMessage", "Unable to load courts.");
            request.getRequestDispatcher("/pages/adminCourts.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            AdminCourtService service = new AdminCourtService();

            if ("activate".equals(action)) {
                int courtId = Integer.parseInt(request.getParameter("courtId"));
                service.updateCourtStatus(courtId, "active");
            } else if ("deactivate".equals(action)) {
                int courtId = Integer.parseInt(request.getParameter("courtId"));
                service.updateCourtStatus(courtId, "inactive");
            } else if ("addCourt".equals(action)) {
                CourtModel court = new CourtModel();
                court.setCourtName(request.getParameter("courtName"));
                court.setCourtNumber(request.getParameter("courtNumber"));
                court.setCourtCapacity(Integer.parseInt(request.getParameter("courtCapacity")));
                court.setSurfaceType(request.getParameter("surfaceType"));
                court.setPricePerHour(new BigDecimal(request.getParameter("pricePerHour")));
                court.setCourtStatus(request.getParameter("courtStatus"));
                court.setVenueId(Integer.parseInt(request.getParameter("venueId")));
                court.setSportTypeId(Integer.parseInt(request.getParameter("sportTypeId")));
                court.setStaffId(Integer.parseInt(request.getParameter("staffId")));

                Part imagePart = request.getPart("courtImage");
                String imageFileName = null;

                if (imagePart != null && imagePart.getSize() > 0) {
                    String originalFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                    String extension = "";

                    int dotIndex = originalFileName.lastIndexOf(".");
                    if (dotIndex != -1) {
                        extension = originalFileName.substring(dotIndex);
                    }

                    imageFileName = UUID.randomUUID().toString() + extension;

                    String uploadPath = getServletContext().getRealPath("/photos");
                    File uploadDir = new File(uploadPath);

                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    imagePart.write(uploadPath + File.separator + imageFileName);
                }

                court.setImagePath(imageFileName);
                service.addCourt(court);
            }

            response.sendRedirect(request.getContextPath() + "/adminCourts");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("activePage", "courts");
            request.setAttribute("errorMessage", "Unable to process court action.");
            doGet(request, response);
        }
    }
}