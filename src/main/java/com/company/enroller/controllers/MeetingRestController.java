package com.company.enroller.controllers;
import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;
    @Autowired
    private ParameterNamesModule parameterNamesModule;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings(@RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortOrder, @RequestParam(required = false) String key)  {
        if (sortBy != null) {
            if (sortBy.equals("title")) {
                ;
            } else {
                sortBy = "title";
            }
        } else {
            sortBy = "title";
        }
        if (sortOrder != null) {
            if (sortOrder.equals("ASC") || sortOrder.equals("DESC")) {
                ;
            } else {
                sortOrder = "DESC";
            }
        } else {
            sortOrder = "DESC";
        }
        if (key != null) {
            ;
        } else {
            key = "";
        }
        Collection<Meeting> meetings = meetingService.getAll(sortBy, sortOrder, key);
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }
}
