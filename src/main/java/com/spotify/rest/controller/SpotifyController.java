package com.spotify.rest.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spotify.rest.authtoken.AuthorizationCode;
import com.spotify.rest.authtoken.AuthorizationToken;

@RestController
public class SpotifyController {

	@RequestMapping("/userData")
	public Object tokenAccepted(Map<String, Object> model, @RequestParam("code") String code)
	{
		AuthorizationToken.code = code;
		AuthorizationToken.authorizationCode_Async();
		return "YEaa";
	}
	
	@RequestMapping("/login")
	public Object tokenRequest(Map<String, Object> model) throws MalformedURLException
	{
		URI uri = AuthorizationCode.authorizationCodeUri_Sync();
		return uri;
	}
	
}
