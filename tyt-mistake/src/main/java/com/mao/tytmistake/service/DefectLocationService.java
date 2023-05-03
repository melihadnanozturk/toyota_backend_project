package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.DefectLocationRequest;
import com.mao.tytmistake.controller.request.LocationRemoveRequest;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.PageDefectLocationResponse;

import java.util.List;

public interface DefectLocationService {

    PageDefectLocationResponse findAll();

    DefectLocationResponse addNewLocation(DefectLocationRequest defectLocationRequest);

    DefectLocationResponse updateLocation();

    List<Long> removeLocation(LocationRemoveRequest locationRemoveRequest);
}
