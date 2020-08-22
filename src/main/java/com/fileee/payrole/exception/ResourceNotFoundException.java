package com.fileee.payrole.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.fileee.payrole.controllers.StaffMemberController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	Logger logger = LoggerFactory.getLogger(StaffMemberController.class);

	public ResourceNotFoundException(String message) {
		super(message);
		logger.error(message);
	}

}
