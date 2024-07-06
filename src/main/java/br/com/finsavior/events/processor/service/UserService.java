package br.com.finsavior.events.processor.service;

import br.com.finsavior.events.processor.model.dto.ExternalUserDTO;
import br.com.finsavior.grpc.user.DeleteAccountRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void deleteAccount(DeleteAccountRequest deleteAccountRequest);
    public void updateUserPlan(ExternalUserDTO externalUserdto);
}
