package com.spotify.rest.service;

import java.io.IOException;
import java.net.URI;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spotify.rest.authtoken.AuthorizationCode;
import com.spotify.rest.authtoken.AuthorizationToken;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.User;

@Service
public class SpotifyApiService {
	private SpotifyApi spotifyApi;

	public SpotifyApiService() {
		
	}
	
	public void createSpotifyApi(String code) {
		AuthorizationToken.buildAuthorization(code);		
		spotifyApi = AuthorizationToken.authorizationCode_Sync();
	}
	
	public URI getSpotifyUri() {
		return AuthorizationCode.authorizationCodeUri_Sync();
	}
	
	public User getUserProfile() {
		User user = null;
		try {
		      user = spotifyApi.getCurrentUsersProfile()
		              .build().execute();
		      System.out.println("Display name: " + user.getDisplayName());
		    } catch (IOException | SpotifyWebApiException e) {
		      System.out.println("Error: " + e.getMessage());
		    }
		return user;
	}

	public Map<String, Playlist> getUserLists(String userId) {
		Map<String, Playlist> list = new Hashtable<String, Playlist>();
		try {
			Paging<PlaylistSimplified> playlists = spotifyApi.getListOfCurrentUsersPlaylists().build().execute();
			System.out.println("List : " + playlists.toString());
			
			PlaylistSimplified[] items = playlists.getItems();
			for( PlaylistSimplified item : items ) {
				String playlist_id = item.getId();
				String user_id = userId;
				Playlist playlist = spotifyApi.getPlaylist(user_id, playlist_id).build().execute();
				list.put(playlist_id, playlist);
			}
			
			return list;
		} catch (SpotifyWebApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
