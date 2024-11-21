package com.webhook.notifiation.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webhook.notifiation.api.logic.ProcessNotification;
import com.webhook.notifiation.api.model.NotificationRequestDto;
import com.webhook.notifiation.api.model.NotificationResponseDto;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

	@Autowired
	private ProcessNotification processNotification;

	@PostMapping("/receive")
	public ResponseEntity<NotificationResponseDto> receiveNotification(@RequestBody NotificationRequestDto request) {

		processNotification.processMercantilNotification(request);
		// Return a response
		return new ResponseEntity<NotificationResponseDto>(NotificationResponseDto.builder().codigo("0000")
				.mensajeCliente("Notificacion recibia con exito").mensajeSistema("Notificacion recibia con exitoF").idRegistro("00000").build(), HttpStatus.OK);
	}
}