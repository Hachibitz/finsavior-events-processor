package br.com.finsavior.events.processor.service;

import br.com.finsavior.grpc.user.DeleteAccountRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public boolean deleteAccount(DeleteAccountRequest deleteAccountRequest);
}
