package com.spotify.rest.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.spotify.rest.authtoken.AuthorizationCode;
import com.spotify.rest.authtoken.AuthorizationToken;
import com.spotify.rest.model.Playlist;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.Paging;
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

	public boolean removeDuplicateTracks(String userId, Playlist playlist) {
		try {
			String user_id = userId;
			
			//Transfrom to JsonArray
			for( Map track : playlist.getTracks() ) {
				String playlist_id = (String) track.get("playlistId");		

				StringBuilder t = new StringBuilder().append("{ 'uri' :");
				t.append("'");
				t.append((String) track.get("track"));
				t.append("'}");
				
				Gson gson = new Gson();
				JsonElement element = gson.fromJson(t.toString(), JsonElement.class);
				
				JsonArray tracks = new JsonArray();
				tracks.add(element);
				SnapshotResult result = spotifyApi.removeTracksFromPlaylist(user_id, playlist_id, tracks).build().execute();
				System.out.println("Snapshot: " + result.toString());
			}		
			
		return true;
			
		}catch (SpotifyWebApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public List<com.wrapper.spotify.model_objects.specification.Playlist> getUserLists(String userId) {
		List<com.wrapper.spotify.model_objects.specification.Playlist> list = new ArrayList<>();
		try {
			Paging<PlaylistSimplified> playlists = spotifyApi.getListOfCurrentUsersPlaylists().build().execute();
			System.out.println("List : " + playlists.toString());
			
			PlaylistSimplified[] items = playlists.getItems();
			for( PlaylistSimplified item : items ) {
				String playlist_id = item.getId();
				String user_id = userId;
				com.wrapper.spotify.model_objects.specification.Playlist playlist = spotifyApi.getPlaylist(user_id, playlist_id).build().execute();
				list.add(playlist);
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
