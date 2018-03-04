package com.spotify.rest.authtoken;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

public class AuthorizationToken {
	private static final String clientId = "b9d3a52f089a4a8b91e70ae0e290138e";
	private static final String clientSecret = "a715aa9814e7440684a2b628c0c61431";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/spotify");
	private static final String code = "";

	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
			.setClientSecret(clientSecret).setRedirectUri(redirectUri).build();
	private static final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

	public static void authorizationCode_Sync() {
		try {
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

			// Set access and refresh token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
			spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

			System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
		} catch (IOException | SpotifyWebApiException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void authorizationCode_Async() {
		try {
			final Future<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRequest
					.executeAsync();

			// ...

			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.get();

			// Set access and refresh token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
			spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

			System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Error: " + e.getCause().getMessage());
		}
	}
}
