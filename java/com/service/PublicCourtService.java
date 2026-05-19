package com.service;

import com.dao.CourtDAO;
import com.model.CourtModel;

import java.util.List;

public class PublicCourtService {

    public List<CourtModel> getAllActiveCourts() throws Exception {
        CourtDAO dao = new CourtDAO();
        return dao.getAllActiveCourtsForPublic();
    }
    public CourtModel getCourtById(int courtId) throws Exception {
        CourtDAO dao = new CourtDAO();
        return dao.getCourtById(courtId);
    }
    public List<CourtModel> getCourtsBySport(String sportName) throws Exception {
        CourtDAO dao = new CourtDAO();
        return dao.getCourtsBySport(sportName);
    }
}