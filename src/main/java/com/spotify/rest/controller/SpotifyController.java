package com.spotify.rest.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spotify.rest.model.TokenData;
import com.spotify.rest.service.SpotifyApiService;

@RestController
public class SpotifyController {

	@Autowired
	private SpotifyApiService spotifyService;
	
	@RequestMapping(value = "/userPlayLists", method = RequestMethod.GET ,headers="Accept=application/json")
	public Object userLists(@RequestParam("userId") String userId)
	{
		return spotifyService.getUserLists(userId);
	}
	
	@RequestMapping(value = "/removeDuplicateTracks", method = RequestMethod.GET ,headers="Accept=application/json")
	public Object removeDuplicateTracks(@RequestParam("userId") String userId)
	{
		return spotifyService.getUserLists(userId);
	}
	
	@RequestMapping(value = "/userData", method = RequestMethod.POST ,headers="Accept=application/json")
	public Object userData(@RequestBody TokenData data)
	{
		spotifyService.createSpotifyApi(data.getCode());
		return spotifyService.getUserProfile();
	}
	
	@RequestMapping("/login")
	public Object tokenRequest(Map<String, Object> model) throws MalformedURLException
	{
		URI uri = spotifyService.getSpotifyUri();
		return uri;
	}
	
}
