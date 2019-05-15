package com.ubn.service;

import com.ubn.model.PeaceMassResponse;

public interface VerificationService {

	public PeaceMassResponse verified(String code);
}
