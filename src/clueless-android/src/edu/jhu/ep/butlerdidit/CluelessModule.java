package edu.jhu.ep.butlerdidit;

import com.google.inject.AbstractModule;

import edu.jhu.ep.butlerdidit.service.GSMatchHelperImpl;
import edu.jhu.ep.butlerdidit.service.api.GSMatchHelper;

public class CluelessModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GSMatchHelper.class).to(GSMatchHelperImpl.class);
	}

}
