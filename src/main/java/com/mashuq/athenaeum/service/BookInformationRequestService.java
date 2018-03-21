package com.mashuq.athenaeum.service;

import java.util.Map;

public interface BookInformationRequestService {

	Map requestBookInformation(Integer id);

	Map requestBookInformation(Integer id, String email);
}
