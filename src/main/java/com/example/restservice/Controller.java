package com.example.restservice;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.concurrent.ConcurrentHashMap;


@RestController
public class Controller {
    ConcurrentHashMap<String, Record> db = new ConcurrentHashMap<>();

		// can move this to somewhere else later
		public boolean verify_uuid(String id) {
			try {
				UUID.fromString(id);
			} catch(IllegalArgumentException e) {
				return false;
			}
			return true;
		}

    @PostMapping(value="/guid/{id}")
    public ResponseEntity f(@PathVariable("id") String id) {
			if (!verify_uuid(id)) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			long expire=0;
			String user="user";
			Record rec = new Record(id, expire, user);
			db.put(id, rec);
			return ResponseEntity.ok().body(rec);
    }

    @GetMapping(value="/guid/{id}")
    public ResponseEntity g(@PathVariable("id") String id) {
			if (!verify_uuid(id)) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			Record rec = db.get(id);
			if (rec == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
					return ResponseEntity.ok().body(db.get(id));
			}
    }

		@PutMapping(value = "/guid/{id}")
		public ResponseEntity h(@PathVariable("id") String id) {
			if (!verify_uuid(id)) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			Record rec = db.get(id);
			if (rec == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				long expire=0;
				String user="new_user";
				Record rec_new = new Record(id, expire, user);
				db.put(id, rec_new);
				return ResponseEntity.ok().body(db.get(id));
			}
		}

		@DeleteMapping(value = "/guid/{id}")
		public ResponseEntity i(@PathVariable("id") String id) {
			if (!verify_uuid(id)) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			Record rec = db.get(id);
			if (rec == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				db.remove(id);
				return ResponseEntity.ok().body("");
			}
		}
}
