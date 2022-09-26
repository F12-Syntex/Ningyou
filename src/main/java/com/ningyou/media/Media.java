package com.ningyou.media;

import com.ningyou.domains.Domain;
import com.ningyou.queries.QueryEntity;

public abstract class Media extends QueryEntity{

	public Domain domain;
	public boolean nsfw;
	
	public Media(QueryEntity entity) {
		super(entity);
		this.domain = Domain.getDomain(entity.source);
		this.nsfw = this.domain.nsfw;
	}
	
}
