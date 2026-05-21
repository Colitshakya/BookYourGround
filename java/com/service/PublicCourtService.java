package com.service;

// Importing CourtDAO to retrieve public court records
import com.dao.CourtDAO;

// Importing CourtModel to store court details
import com.model.CourtModel;

// Importing List to store multiple court records
import java.util.List;

/**
 * PublicCourtService contains business logic for public court pages.
 *
 * This service is used by public/user-side controllers to display courts.
 *
 * It is responsible for:
 * - Getting all active courts
 * - Getting court details by ID
 * - Getting courts by sport name
 */
public class PublicCourtService {

    /**
     * Gets all active courts for public users.
     *
     * @return list of active courts
     * @throws Exception if court records cannot be retrieved
     */
    public List<CourtModel> getAllActiveCourts() throws Exception {
        // Creating CourtDAO object to retrieve active court records
        CourtDAO dao = new CourtDAO();

        // Returning active courts for public display
        return dao.getAllActiveCourtsForPublic();
    }

    /**
     * Gets a court record by court ID.
     *
     * @param courtId ID of the selected court
     * @return CourtModel object containing court details
     * @throws Exception if court details cannot be retrieved
     */
    public CourtModel getCourtById(int courtId) throws Exception {
        // Creating CourtDAO object to retrieve court by ID
        CourtDAO dao = new CourtDAO();

        // Returning selected court details
        return dao.getCourtById(courtId);
    }

    /**
     * Gets courts by selected sport name.
     *
     * @param sportName selected sport name
     * @return list of courts for the selected sport
     * @throws Exception if court records cannot be retrieved
     */
    public List<CourtModel> getCourtsBySport(String sportName) throws Exception {
        // Creating CourtDAO object to retrieve sport-wise courts
        CourtDAO dao = new CourtDAO();

        // Returning courts filtered by sport name
        return dao.getCourtsBySport(sportName);
    }
}
