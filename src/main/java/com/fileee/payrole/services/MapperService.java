package com.fileee.payrole.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.data.rest.webmvc.json.DomainObjectReader;
import org.springframework.data.rest.webmvc.mapping.Associations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
@Primary
public class MapperService {
	
	private final ObjectMapper objectMapper;
	private final DomainObjectReader domainObjectReader;
	
	@Autowired
	public MapperService(ObjectMapper mapper, PersistentEntities persistentEntities, Associations associationLinks)
	{
		this.objectMapper = mapper;
		this.domainObjectReader = new DomainObjectReader(persistentEntities, associationLinks);
	}
	
	public <T> boolean map(final String properties, T targetObject) throws IOException{
		final ObjectNode objectNode = (ObjectNode) objectMapper.readTree(properties);
		return this.map(objectNode, targetObject);
	}

	@SuppressWarnings("deprecation")
	private <T> boolean map(final ObjectNode objectNode, T targetObject) {
		
		if(objectNode.size()==0) {
			return false;
		}
		
		domainObjectReader.merge(objectNode, targetObject, objectMapper);
		return true;
	}

}
