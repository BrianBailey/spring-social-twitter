/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter;

import java.util.List;

import org.springframework.social.core.SocialException;

/**
 * Interface specifying a basic set of operations for interacting with Twitter.
 * Implemented by TwitterTemplate. Not often used directly, but a useful option
 * to enhance testability, as it can easily be mocked or stubbed.
 * 
 * @author Craig Walls
 */
public interface TwitterOperations {

	/**
	 * Retrieves the user's Twitter screen name.
	 * 
	 * @return the user's screen name at Twitter
	 */
	String getProfileId();

	/**
	 * Retrieves a list of users that the given user follows.
	 * 
	 * @param screenName
	 *            The user's Twitter screen name
	 * @return a list of user screen names
	 */
	List<String> getFriends(String screenName);

	/**
	 * Updates the user's status.
	 * 
	 * @param status
	 *            The status message
	 * 
	 * @throws SocialException
	 *             if an error response is received from Twitter
	 */
	void updateStatus(String status);

	/**
	 * Updates the user's status, including additional metadata concerning the
	 * status.
	 * 
	 * @param status
	 *            The status message
	 * @param details
	 *            Metadata pertaining to the status
	 * 
	 * @throws SocialException
	 *             if an error response is received from Twitter
	 */
	void updateStatus(String status, StatusDetails details);

	/**
	 * Posts a retweet of an existing tweet.
	 * 
	 * @param tweetId
	 *            The ID of the tweet to be retweeted
	 * 
	 * @throws SocialException
	 *             if an error response is received from Twitter
	 */
	void retweet(long tweetId);

	/**
	 * Searches Twitter, returning the first 50 matching {@link Tweet}s
	 * 
	 * @param query
	 *            The search query string
	 * @return a {@link SearchResults} containing the search results metadata
	 *         and a list of matching {@link Tweet}s
	 * 
	 * @see SearchResults, {@link Tweet}
	 */
	SearchResults search(String query);

	/**
	 * Searches Twitter, returning a specific page out of the complete set of
	 * results.
	 * 
	 * @param query
	 *            The search query string
	 * @param page
	 *            The page to return
	 * @param pageSize
	 *            The number of {@link Tweet}s per page
	 * 
	 * @return a {@link SearchResults} containing the search results metadata
	 *         and a list of matching {@link Tweet}s
	 * 
	 * @see SearchResults, {@link Tweet}
	 */
	SearchResults search(String query, int page, int pageSize);

	/**
	 * Searches Twitter, returning a specific page out of the complete set of
	 * results. Results are filtered to those whose ID falls between sinceId and
	 * maxId
	 * 
	 * @param query
	 *            The search query string
	 * @param page
	 *            The page to return
	 * @param pageSize
	 *            The number of {@link Tweet}s per page
	 * @param sinceId
	 *            The minimum {@link Tweet} ID to return in the results
	 * @param maxId
	 *            The maximum {@link Tweet} ID to return in the results
	 * 
	 * @return a {@link SearchResults} containing the search results metadata
	 *         and a list of matching {@link Tweet}s
	 * 
	 * @see SearchResults, {@link Tweet}
	 */
	SearchResults search(String query, int page, int resultsPerPage, int sinceId, int maxId);
}