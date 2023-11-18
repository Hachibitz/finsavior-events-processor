package br.com.finsavior.events.processor.service.impl;

import br.com.finsavior.events.processor.service.UserService;
import br.com.finsavior.grpc.user.DeleteAccountRequest;
import br.com.finsavior.grpc.user.DeleteAccountResponse;
import br.com.finsavior.grpc.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public UserServiceImpl() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6566)
                .usePlaintext()
                .build();

        userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public boolean deleteAccount(DeleteAccountRequest deleteAccountRequest) {
        try {
            DeleteAccountResponse deleteAccountResponse = userServiceBlockingStub.deleteAccount(deleteAccountRequest);
            log.info("Account deleted successfully.");
            return true;
        } catch (StatusRuntimeException e) {
            log.error("Error deleting account: "+deleteAccountRequest.getUsername()+" - "+e.getStatus().getDescription());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            log.error("Error deleting account: "+deleteAccountRequest.getUsername()+" - "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
