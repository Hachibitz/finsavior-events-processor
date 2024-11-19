package br.com.finsavior.events.processor.service;

import br.com.finsavior.events.processor.model.dto.DeleteAccountRequestDTO;
import br.com.finsavior.events.processor.model.dto.ExternalUserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void deleteAccount(DeleteAccountRequestDTO deleteAccountRequestDTO);
    public void updateUserPlan(ExternalUserDTO externalUserdto);
}
