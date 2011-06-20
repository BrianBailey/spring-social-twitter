/*
 * Copyright 2011 the original author or authors.
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
package org.springframework.social.twitter.api.impl;

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.social.test.client.RequestMatchers.*;
import static org.springframework.social.test.client.ResponseCreators.*;

import java.util.List;

import org.junit.Test;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.twitter.api.TwitterProfile;


/**
 * @author Craig Walls
 */
public class FriendTemplateTest extends AbstractTwitterApiTest {

	@Test
	public void getFriends_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/friends.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friends-or-followers"), responseHeaders));

		List<TwitterProfile> friends = twitter.friendOperations().getFriends();
		assertEquals(2, friends.size());
		assertEquals("royclarkson", friends.get(0).getScreenName());
		assertEquals("kdonald", friends.get(1).getScreenName());
	}

	@Test(expected = NotAuthorizedException.class)
	public void getFriends_currentUser_unauthorized() {
		unauthorizedTwitter.friendOperations().getFriends();
	}
	
	@Test
	public void getFriends_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/friends.json?cursor=-1&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friends-or-followers"), responseHeaders));

		List<TwitterProfile> friends = twitter.friendOperations().getFriends(98765L);
		assertEquals(2, friends.size());
		assertEquals("royclarkson", friends.get(0).getScreenName());
		assertEquals("kdonald", friends.get(1).getScreenName());
	}

	@Test
	public void getFriends_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/friends.json?cursor=-1&screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friends-or-followers"), responseHeaders));

		List<TwitterProfile> friends = twitter.friendOperations().getFriends("habuma");
		assertEquals(2, friends.size());
		assertEquals("royclarkson", friends.get(0).getScreenName());
		assertEquals("kdonald", friends.get(1).getScreenName());
	}

	@Test
	public void getFriendIds_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1/friends/ids.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friend-or-follower-ids"), responseHeaders));
		
		List<Long> followerIds = twitter.friendOperations().getFriendIds();
		assertEquals(3, followerIds.size());
		assertEquals(12345L, (long) followerIds.get(0));
		assertEquals(9223372036854775807L, (long) followerIds.get(1));		
		assertEquals(34567L, (long) followerIds.get(2));
	}

	@Test(expected = NotAuthorizedException.class)
	public void getFriendIds_currentUser_unauthorized() {
		unauthorizedTwitter.friendOperations().getFriendIds();
	}
	
	@Test
	public void getFriendIds_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/friends/ids.json?cursor=-1&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friend-or-follower-ids"), responseHeaders));
		
		List<Long> followerIds = twitter.friendOperations().getFriendIds(98765L);
		assertEquals(3, followerIds.size());
		assertEquals(12345L, (long) followerIds.get(0));
		assertEquals(9223372036854775807L, (long) followerIds.get(1));		
		assertEquals(34567L, (long) followerIds.get(2));
	}

	@Test
	public void getFriendIds_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1/friends/ids.json?cursor=-1&screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friend-or-follower-ids"), responseHeaders));
		
		List<Long> followerIds = twitter.friendOperations().getFriendIds("habuma");
		assertEquals(3, followerIds.size());
		assertEquals(12345L, (long) followerIds.get(0));
		assertEquals(9223372036854775807L, (long) followerIds.get(1));		
		assertEquals(34567L, (long) followerIds.get(2));
	}
	
	@Test 
	public void getFollowers_currentUser() {
	    mockServer.expect(requestTo("https://api.twitter.com/1/statuses/followers.json?cursor=-1"))
	        .andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friends-or-followers"), responseHeaders));
	    
		List<TwitterProfile> followers = twitter.friendOperations().getFollowers();
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void getFollowers_currentUser_unauthorized() {
		unauthorizedTwitter.friendOperations().getFollowers();
	}

	@Test 
	public void getFollowers_byUserId() {
	    mockServer.expect(requestTo("https://api.twitter.com/1/statuses/followers.json?cursor=-1&user_id=98765"))
	        .andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friends-or-followers"), responseHeaders));
	    
		List<TwitterProfile> followers = twitter.friendOperations().getFollowers(98765L);
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}
	
	@Test 
	public void getFollowers_byScreenName() {
	    mockServer.expect(requestTo("https://api.twitter.com/1/statuses/followers.json?cursor=-1&screen_name=oizik"))
	        .andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friends-or-followers"), responseHeaders));
	    
		List<TwitterProfile> followers = twitter.friendOperations().getFollowers("oizik");
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}

	@Test
	public void getFollowerIds_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1/followers/ids.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friend-or-follower-ids"), responseHeaders));
		
		List<Long> followerIds = twitter.friendOperations().getFollowerIds();
		assertEquals(3, followerIds.size());
		assertEquals(12345L, (long) followerIds.get(0));
		assertEquals(9223372036854775807L, (long) followerIds.get(1));		
		assertEquals(34567L, (long) followerIds.get(2));
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void getFollowerIds_currentUser_unauthorized() {
		unauthorizedTwitter.friendOperations().getFollowerIds();
	}

	@Test
	public void getFollowerIds_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/followers/ids.json?cursor=-1&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friend-or-follower-ids"), responseHeaders));
		
		List<Long> followerIds = twitter.friendOperations().getFollowerIds(98765L);
		assertEquals(3, followerIds.size());
		assertEquals(12345L, (long) followerIds.get(0));
		assertEquals(9223372036854775807L, (long) followerIds.get(1));		
		assertEquals(34567L, (long) followerIds.get(2));
	}

	@Test
	public void getFollowerIds_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1/followers/ids.json?cursor=-1&screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("friend-or-follower-ids"), responseHeaders));
		
		List<Long> followerIds = twitter.friendOperations().getFollowerIds("habuma");
		assertEquals(3, followerIds.size());
		assertEquals(12345L, (long) followerIds.get(0));
		assertEquals(9223372036854775807L, (long) followerIds.get(1));		
		assertEquals(34567L, (long) followerIds.get(2));
	}
	
	@Test
	public void follow_byUserId() {
	    mockServer.expect(requestTo("https://api.twitter.com/1/friendships/create.json?user_id=98765"))
	        .andExpect(method(POST))
	        .andRespond(withResponse(jsonResource("follow"), responseHeaders));
	    
		String followedScreenName = twitter.friendOperations().follow(98765);
	    assertEquals("oizik2", followedScreenName);
	    
	    mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void follow_byUserId_unauthorized() {
		unauthorizedTwitter.friendOperations().follow(98765);
	}
	
	@Test
	public void follow_byScreenName() {
	    mockServer.expect(requestTo("https://api.twitter.com/1/friendships/create.json?screen_name=oizik2"))
	        .andExpect(method(POST))
	        .andRespond(withResponse(jsonResource("follow"), responseHeaders));
	    
		String followedScreenName = twitter.friendOperations().follow("oizik2");
	    assertEquals("oizik2", followedScreenName);
	    
	    mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void follow_byScreenName_unauthorized() {
		unauthorizedTwitter.friendOperations().follow("aizik2");
	}
	
	@Test
	public void unfollow_byUserId() {
        mockServer.expect(requestTo("https://api.twitter.com/1/friendships/destroy.json?user_id=98765"))
            .andExpect(method(POST))
            .andRespond(withResponse(jsonResource("unfollow"), responseHeaders));
        
		String unFollowedScreenName = twitter.friendOperations().unfollow(98765);
        assertEquals("oizik2", unFollowedScreenName);
        
        mockServer.verify();
    }

	@Test(expected = NotAuthorizedException.class)
	public void unfollow_byUserId_unauthorized() {
		unauthorizedTwitter.friendOperations().unfollow(98765);
	}

	@Test
	public void unfollow_byScreenName() {
        mockServer.expect(requestTo("https://api.twitter.com/1/friendships/destroy.json?screen_name=oizik2"))
            .andExpect(method(POST))
            .andRespond(withResponse(jsonResource("unfollow"), responseHeaders));
        
		String unFollowedScreenName = twitter.friendOperations().unfollow("oizik2");
        assertEquals("oizik2", unFollowedScreenName);
        
        mockServer.verify();
    }
	
	@Test(expected = NotAuthorizedException.class)
	public void unfollow_byScreenName_unauthorized() {
		unauthorizedTwitter.friendOperations().follow("aizik2");
	}

	@Test
	public void enableNotifications_byUserId() {
        mockServer.expect(requestTo("https://api.twitter.com/1/notifications/follow.json?user_id=98765"))
            .andExpect(method(POST))
            .andRespond(withResponse(jsonResource("follow"), responseHeaders));
		TwitterProfile unFollowedUser = twitter.friendOperations().enableNotifications(98765);
        assertEquals("oizik2", unFollowedUser.getScreenName());
        mockServer.verify();
    }

	@Test(expected = NotAuthorizedException.class)
	public void enableNotifications_byUserId_unauthorized() {
		unauthorizedTwitter.friendOperations().enableNotifications(98765);
	}
	
	@Test
	public void enableNotifications_byScreenName() {
        mockServer.expect(requestTo("https://api.twitter.com/1/notifications/follow.json?screen_name=oizik2"))
            .andExpect(method(POST))
            .andRespond(withResponse(jsonResource("follow"), responseHeaders));
		TwitterProfile unFollowedUser = twitter.friendOperations().enableNotifications("oizik2");
        assertEquals("oizik2", unFollowedUser.getScreenName());
        mockServer.verify();
    }

	@Test(expected = NotAuthorizedException.class)
	public void enableNotifications_byScreenName_unauthorized() {
		unauthorizedTwitter.friendOperations().enableNotifications("oizik2");
	}

	@Test
	public void disableNotifications_byUserId() {
        mockServer.expect(requestTo("https://api.twitter.com/1/notifications/leave.json?user_id=98765"))
            .andExpect(method(POST))
            .andRespond(withResponse(jsonResource("unfollow"), responseHeaders));
		TwitterProfile unFollowedUser = twitter.friendOperations().disableNotifications(98765);
        assertEquals("oizik2", unFollowedUser.getScreenName());
        mockServer.verify();
    }

	@Test(expected = NotAuthorizedException.class)
	public void disableNotifications_byUserId_unauthorized() {
		unauthorizedTwitter.friendOperations().disableNotifications(98765);
	}
	
	@Test
	public void disableNotifications_byScreenName() {
        mockServer.expect(requestTo("https://api.twitter.com/1/notifications/leave.json?screen_name=oizik2"))
            .andExpect(method(POST))
            .andRespond(withResponse(jsonResource("unfollow"), responseHeaders));
		TwitterProfile unFollowedUser = twitter.friendOperations().disableNotifications("oizik2");
        assertEquals("oizik2", unFollowedUser.getScreenName());
        mockServer.verify();
    }
	
	@Test(expected = NotAuthorizedException.class)
	public void disableNotifications_byScreenName_unauthorized() {
		unauthorizedTwitter.friendOperations().disableNotifications("oizik2");
	}
	
	@Test
	public void exists() {
		mockServer.expect(requestTo("https://api.twitter.com/1/friendships/exists.json?user_a=kdonald&user_b=tinyrod"))
			.andExpect(method(GET))
			.andRespond(withResponse("true", responseHeaders));
		mockServer.expect(requestTo("https://api.twitter.com/1/friendships/exists.json?user_a=royclarkson&user_b=charliesheen"))
			.andExpect(method(GET))
			.andRespond(withResponse("false", responseHeaders));
		
		assertTrue(twitter.friendOperations().friendshipExists("kdonald", "tinyrod"));
		assertFalse(twitter.friendOperations().friendshipExists("royclarkson", "charliesheen"));
	}
	
	@Test
	public void getIncomingFriendships() {
		mockServer.expect(requestTo("https://api.twitter.com/1/friendships/incoming.json"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("incoming-or-outgoing-friendships"), responseHeaders));

		List<Long> friendships = twitter.friendOperations().getIncomingFriendships();
		assertEquals(3, friendships.size());
		assertEquals(12345, (long) friendships.get(0));
		assertEquals(23456, (long) friendships.get(1));
		assertEquals(34567, (long) friendships.get(2));
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void getIncomingFriendships_unauthorized() {
		unauthorizedTwitter.friendOperations().getIncomingFriendships();
	}
	
	@Test
	public void getOutgoingFriendships() {
		mockServer.expect(requestTo("https://api.twitter.com/1/friendships/outgoing.json"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("incoming-or-outgoing-friendships"), responseHeaders));

		List<Long> friendships = twitter.friendOperations().getOutgoingFriendships();
		assertEquals(3, friendships.size());
		assertEquals(12345, (long) friendships.get(0));
		assertEquals(23456, (long) friendships.get(1));
		assertEquals(34567, (long) friendships.get(2));
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void getOutgoingFriendships_unauthorized() {
		unauthorizedTwitter.friendOperations().getOutgoingFriendships();
	}
}
