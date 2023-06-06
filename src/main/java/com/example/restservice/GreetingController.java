package com.example.restservice;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.concurrent.ConcurrentHashMap;


@RestController
public class Controller {
    ConcurrentHashMap<String, Record> db = new ConcurrentHashMap<>();

    @PostMapping(value="/guid/{id}")
    public ResponseEntity greeting(@PathVariable("id") String id) {
	try {
	    UUID.fromString(id);
	} catch(IllegalArgumentException e) {
	    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	long expire=0;
	String user="user";
	Record rec = new Record(id, expire, user);
	db.put(id, rec);
	return ResponseEntity.ok().body(rec);
}
}
