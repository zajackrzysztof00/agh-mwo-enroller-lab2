package com.company.enroller.controllers;

import java.util.Collection;
import java.util.Optional;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;
    @Autowired
    private ParameterNamesModule parameterNamesModule;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants(@RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortOrder, @RequestParam(required = false) String key)  {
		if (sortBy != null) {
			if (sortBy.equals("login")) {
				;
			} else {
				sortBy = "login";
			}
		} else {
			sortBy = "login";
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
		Collection<Participant> participants = participantService.getAll(sortBy, sortOrder, key);
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> registerParticipant(@RequestBody Participant participant) {
		if (participantService.findByLogin(participant.getLogin()) == null) {
			Participant participant1 = participantService.add(participant);
			return new ResponseEntity<Participant>(participant1, HttpStatus.CREATED);
		}
		return new ResponseEntity("Unable to create. A participant with login " + participant.getLogin() + " already exist.", HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeParticipant(@PathVariable("id") String login) {
		participantService.remove(login);
		return new ResponseEntity<>("Participant Deleted", HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipant(@PathVariable("id") String login) {
		Participant participant = participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public  ResponseEntity<?> updateParticipant(@PathVariable("id") String login, @RequestBody Participant participant) {
		if (participantService.findByLogin(participant.getLogin()) == null) {
			Participant participant1 = participantService.add(participant);
			return new ResponseEntity("Unable to find" + participant.getLogin(), HttpStatus.CONFLICT);
		}
		participantService.update(login, participant, participantService.findByLogin(login));
		return new ResponseEntity<>("Participant Updated", HttpStatus.OK);
	}

}
