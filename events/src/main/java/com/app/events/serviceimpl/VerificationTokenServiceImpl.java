package com.app.events.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.User;
import com.app.events.model.VerificationToken;
import com.app.events.repository.VerificationTokenRepository;
import com.app.events.service.VerificationTokenService;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

	@Autowired
	private VerificationTokenRepository verificationTokenRepostory;
	
	@Override
    public VerificationToken getVerificationToken(String verificationToken) throws ResourceNotFoundException{
        return verificationTokenRepostory.findByToken(verificationToken).orElseThrow(() -> new ResourceNotFoundException("Token"));
    }
     
    @Override
    public void create(User user, String token) {
        VerificationToken myToken = new VerificationToken(user, token);
        verificationTokenRepostory.save(myToken);
    }
}
