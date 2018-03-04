package com.spotify.rest.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spotify.rest.authtoken.AuthorizationCode;

@RestController
public class SpotifyController {

	@RequestMapping("/spotify")
	public Object tokenAccepted(Map<String, Object> model)
	{
		return "YEaa";
	}
	
	@RequestMapping("/login")
	public String tokenRequest(Map<String, Object> model)
	{
		URI uri = AuthorizationCode.authorizationCodeUri_Sync();
		return "LoginRequested";
	}
	
}
