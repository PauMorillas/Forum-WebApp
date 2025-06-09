package com.example.demo.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioSesionUtils {

	// Para acceder al auth en tiempo real
	private static Authentication getAuth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static boolean hayUsuarioEnSesion() {
		boolean haySesion = false;
		Authentication auth = getAuth();
		if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
			haySesion = true;
		}
		return haySesion;
	}

	public static String obtenerNombreUsuario() {
		String nombreUsuario = null;
		Authentication auth = getAuth();
		if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
			nombreUsuario = auth.getName();
		}
		return nombreUsuario;
	}

	public static Object obtenerUsuario() {
		Object usuario = null;
		Authentication auth = getAuth();
		if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
			usuario = auth.getPrincipal();
		}
		return usuario;
	}

}
