package com.company.enroller.controllers;
import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeeting(@PathVariable("id") Long id) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting) {
        Meeting meeting1 = meetingService.add(meeting);
        return new ResponseEntity<Meeting>(meeting1, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeMeeting(@PathVariable("id") Long id) {
        meetingService.remove(id);
        return new ResponseEntity<>("Meeting Deleted", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public  ResponseEntity<?> updateMeeting(@PathVariable("id") Long id, @RequestBody Meeting meeting) {
        if (meetingService.findById(meeting.getId()) == null) {
            Meeting meeting1 = meetingService.add(meeting);
            return new ResponseEntity("Unable to find" + meeting.getTitle(), HttpStatus.CONFLICT);
        }
        meetingService.update(id, meeting, meetingService.findById(id));
        return new ResponseEntity<>("Participant Updated", HttpStatus.OK);
    }
}
