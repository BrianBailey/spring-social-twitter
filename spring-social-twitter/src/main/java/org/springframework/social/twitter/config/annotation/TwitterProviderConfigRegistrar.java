/*
 * Copyright 2013 the original author or authors.
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
package org.springframework.social.twitter.config.annotation;

import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.social.config.annotation.AbstractProviderConfigRegistrarSupport;
import org.springframework.social.security.provider.SocialAuthenticationService;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.config.support.TwitterApiHelper;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.social.twitter.security.TwitterAuthenticationService;

/**
 * {@link ImportBeanDefinitionRegistrar} for configuring a {@link TwitterConnectionFactory} bean and a request-scoped {@link Twitter} bean.
 * @author Craig Walls
 */
public class TwitterProviderConfigRegistrar extends AbstractProviderConfigRegistrarSupport {

	public TwitterProviderConfigRegistrar() {
		super(EnableTwitter.class, TwitterConnectionFactory.class, TwitterApiHelper.class);
	}

	@Override
	protected Class<? extends SocialAuthenticationService<?>> getAuthenticationServiceClass() {
		return TwitterAuthenticationService.class;
	}

}
