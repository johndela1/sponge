package com.example.restservice;

import java.util.UUID;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.concurrent.ConcurrentHashMap;


@RestController
public class Controller {
    ConcurrentHashMap<String, Record> db = new ConcurrentHashMap<>();

	@ControllerAdvice
	class GlobalControllerExceptionHandler {
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		@ExceptionHandler(IllegalArgumentException.class)
		public void handleConflict() {
			// Nothing to do
		}
	}

    @PostMapping(value="/guid/{id}")

    public ResponseEntity f(@PathVariable("id") String id, @RequestBody Record req) {
			UUID.fromString(id);
			Record rec = new Record(req.id(), req.ts(), req.user());
			db.put(id, rec);
			return ResponseEntity.ok().body(rec);
    }

    @GetMapping(value="/guid/{id}")
    public ResponseEntity g(@PathVariable("id") String id) {
			UUID.fromString(id);
			Record rec = db.get(id);
			if (rec == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
					return ResponseEntity.ok().body(db.get(id));
			}
    }

		@PutMapping(value = "/guid/{id}")
		public ResponseEntity h(@PathVariable("id") String id, @RequestBody Record req) {
			UUID.fromString(id);
			Record rec = db.get(id);
			if (rec == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
      	Record rec_new = new Record(req.id(), req.ts(), req.user());
				db.put(id, rec_new);
				return ResponseEntity.ok().body(db.get(id));
			}
		}

		@DeleteMapping(value = "/guid/{id}")
		public ResponseEntity i(@PathVariable("id") String id) {
			UUID.fromString(id);
			Record rec = db.get(id);
			if (rec == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				db.remove(id);
				return ResponseEntity.ok().body("");
			}
		}
}
