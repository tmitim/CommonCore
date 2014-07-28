// Youtube Api
// Uses example from https://developers.google.com/youtube/v3/code_samples/java
// Youtube Api Key must be in root directory.
// name of file = publickey.properties
// format of file = youtube.apikey={apikey}

package com.testproject;

/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.common.collect.HashMultiset;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class YouTubeSearcher {

	/** Global instance properties filename. */
	private static String PROPERTIES_FILENAME = "publickey.properties";

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	/**
	 * Global instance of the max number of videos we want returned (50 = upper
	 * limit per page).
	 */
	private static final long NUMBER_OF_VIDEOS_RETURNED = 3;

	/** Global instance of Youtube object to make all API requests. */
	private static YouTube youtube;

	public HashMultiset <CoreStandard> runSearch(HashMultiset<CoreStandard> hm) {


		HashMultiset <CoreStandard>tempHm = HashMultiset.create();

		int count = 0;
		for (CoreStandard temp : hm) {
			String searchTerm = temp.getCommonCoreDescription();
			temp.setUrl(getLinks(searchTerm));
			tempHm.add(temp);
			count++;
			if (count % 50 == 0){
				System.out.println("Working on item #" + count);
			}
		}

		return tempHm;
	}

	public List<String> getLinks(String searchTerm) {
		List <String> tempString = new ArrayList<String>();
		// Read the developer key from youtube.properties
		Properties properties = new Properties();
		try {
			InputStream in = YouTubeSearcher.class.getResourceAsStream("/"
					+ PROPERTIES_FILENAME);
			properties.load(in);

		} catch (IOException e) {
			System.err.println("There was an error reading "
					+ PROPERTIES_FILENAME + ": " + e.getCause() + " : "
					+ e.getMessage());
			System.exit(1);
		}

		try {
			/*
			 * The YouTube object is used to make all API requests. The last
			 * argument is required, but because we don't need anything
			 * initialized when the HttpRequest is initialized, we override the
			 * interface and provide a no-op function.
			 */
			youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY,
					new HttpRequestInitializer() {
						public void initialize(HttpRequest request)
								throws IOException {
						}
					}).setApplicationName("youtube-cmdline-search-sample")
					.build();

			YouTube.Search.List search = youtube.search().list("id,snippet");

			
			// must be placed in root directory
			String apiKey = properties.getProperty("youtube.apikey");
			search.setKey(apiKey);
			search.setQ(searchTerm);

			search.setType("video");
			/*
			 * This method reduces the info returned to only the fields we need
			 * and makes calls more efficient.
			 */
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
			search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

			SearchListResponse searchResponse = search.execute();

			List<SearchResult> searchResultList = searchResponse.getItems();

			if (searchResultList != null) {
				tempString = findLinks(searchResultList.iterator());
			}
			
			
		} catch (GoogleJsonResponseException e) {
			System.err.println("There was a service error: "
					+ e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
		} catch (IOException e) {
			System.err.println("There was an IO error: " + e.getCause() + " : "
					+ e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return tempString;
	}

	
	private List<String> findLinks(Iterator<SearchResult> iteratorSearchResults) {

		List <String>tempList = new ArrayList<String>();
		if (!iteratorSearchResults.hasNext()) {
			tempList.add("No videos found");
		}

		while (iteratorSearchResults.hasNext()) {

			SearchResult singleVideo = iteratorSearchResults.next();
			ResourceId rId = singleVideo.getId();

			// Double checks the kind is video.
			// System.out.println(singleVideo.getId());
			if (rId.getKind().equals("youtube#video")) {
				tempList.add(rId.getVideoId());
			}
		}
		return tempList;
	}
}
