package com.mashuq.athenaeum.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

import com.mashuq.athenaeum.service.InitializationService;

@Component
public class ApplicationEventListener {
	
	@Autowired
	private InitializationService initializationService;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		initializationService.initializeAllBooks();
	}

}
