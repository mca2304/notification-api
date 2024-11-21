package com.webhook.notifiation.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationResponseDto {
	private String codigo;
	private String mensajeCliente;
	private String mensajeSistema;
	private String idRegistro;

}
