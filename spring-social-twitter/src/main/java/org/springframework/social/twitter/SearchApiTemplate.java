package org.springframework.social.twitter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

class SearchApiTemplate implements SearchApi {

	private final RestTemplate restTemplate;

	public SearchApiTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public SearchResults search(String query) {
		return search(query, 1, DEFAULT_RESULTS_PER_PAGE, 0, 0);
	}

	public SearchResults search(String query, int page, int resultsPerPage) {
		return search(query, page, resultsPerPage, 0, 0);
	}

	public SearchResults search(String query, int page, int resultsPerPage, int sinceId, int maxId) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("query", query);
		parameters.put("rpp", String.valueOf(resultsPerPage));
		parameters.put("page", String.valueOf(page));
		String searchUrl = SEARCH_URL;
		if (sinceId > 0) {
			searchUrl += "&since_id={since}";
			parameters.put("since", String.valueOf(sinceId));
		}
		if (maxId > 0) {
			searchUrl += "&max_id={max}";
			parameters.put("max", String.valueOf(maxId));
		}
		ResponseEntity<Map> response = restTemplate.getForEntity(searchUrl, Map.class, parameters);
		// handleResponseErrors(response);
		Map<String, Object> resultsMap = response.getBody();
		List<Map<String, Object>> items = (List<Map<String, Object>>) resultsMap.get("results");
		List<Tweet> tweets = new ArrayList<Tweet>(resultsMap.size());
		for (Map<String, Object> item : items) {
			tweets.add(populateTweetFromSearchResults(item));
		}
		return buildSearchResults(resultsMap, tweets);
	}

	private SearchResults buildSearchResults(Map<String, Object> response, List<Tweet> tweets) {
		Number maxId = response.containsKey("max_id") ? (Number) response.get("max_id") : 0;
		Number sinceId = response.containsKey("since_id") ? (Number) response.get("since_id") : 0;
		return new SearchResults(tweets, maxId.longValue(), sinceId.longValue(), response.get("next_page") == null);
	}

	private Tweet populateTweetFromSearchResults(Map<String, Object> item) {
		Tweet tweet = new Tweet();
		tweet.setId(NumberUtils.parseNumber(ObjectUtils.nullSafeToString(item.get("id")), Long.class));
		tweet.setFromUser(ObjectUtils.nullSafeToString(item.get("from_user")));
		tweet.setText(ObjectUtils.nullSafeToString(item.get("text")));
		tweet.setCreatedAt(toDate(ObjectUtils.nullSafeToString(item.get("created_at")), searchDateFormat));
		tweet.setFromUserId(NumberUtils.parseNumber(ObjectUtils.nullSafeToString(item.get("from_user_id")), Long.class));
		Object toUserId = item.get("to_user_id");
		if (toUserId != null) {
			tweet.setToUserId(NumberUtils.parseNumber(ObjectUtils.nullSafeToString(toUserId), Long.class));
		}
		tweet.setLanguageCode(ObjectUtils.nullSafeToString(item.get("iso_language_code")));
		tweet.setProfileImageUrl(ObjectUtils.nullSafeToString(item.get("profile_image_url")));
		tweet.setSource(ObjectUtils.nullSafeToString(item.get("source")));
		return tweet;
	}

	private DateFormat searchDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

	private Date toDate(String dateString, DateFormat dateFormat) {
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	static final int DEFAULT_RESULTS_PER_PAGE = 50;

	static final String SEARCH_API_URL_BASE = "https://search.twitter.com";
	static final String SEARCH_URL = SEARCH_API_URL_BASE + "/search.json?q={query}&rpp={rpp}&page={page}";

}
