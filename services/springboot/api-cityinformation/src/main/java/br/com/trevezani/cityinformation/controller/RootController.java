package br.com.trevezani.cityinformation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trevezani.cityinformation.service.RootService;

@RestController
public class RootController {
	@Autowired
	private RootService infoService;
	
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> info() {
    	return new ResponseEntity<>(infoService.properties(), HttpStatus.OK);
    }
}