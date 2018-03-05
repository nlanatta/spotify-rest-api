package com.spotify.rest.authtoken;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

public class AuthorizationCode {
	private static final String clientId = "b9d3a52f089a4a8b91e70ae0e290138e";
	private static final String clientSecret = "a715aa9814e7440684a2b628c0c61431";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3000/spotifyloginresponse");

	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
			.setClientSecret(clientSecret).setRedirectUri(redirectUri).build();
	private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
			.state("x4xkmn9pu3j6ukrs8n").scope("user-read-birthdate,user-read-email").show_dialog(true).build();

	public static URI authorizationCodeUri_Sync() {
		final URI uri = authorizationCodeUriRequest.execute();

		System.out.println("URI: " + uri.toString());
		return uri;
	}

	public static URI authorizationCodeUri_Async() {
		try {
			final Future<URI> uriFuture = authorizationCodeUriRequest.executeAsync();

			// ...

			final URI uri = uriFuture.get();
			System.out.println("URI: " + uri.toString());
			
			return uri;
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Error: " + e.getCause().getMessage());
			return null;
		}
	}
}
