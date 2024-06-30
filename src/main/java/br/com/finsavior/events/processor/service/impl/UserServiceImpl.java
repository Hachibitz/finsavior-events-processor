package br.com.finsavior.events.processor.service.impl;

import br.com.finsavior.events.processor.exception.BusinessException;
import br.com.finsavior.events.processor.exception.DeleteAccountException;
import br.com.finsavior.events.processor.model.constant.Flag;
import br.com.finsavior.events.processor.model.constant.PlanType;
import br.com.finsavior.events.processor.model.dto.ExternalUserDTO;
import br.com.finsavior.events.processor.model.entity.PlanChangeHistory;
import br.com.finsavior.events.processor.model.entity.User;
import br.com.finsavior.events.processor.repository.PlanHistoryRepository;
import br.com.finsavior.events.processor.repository.UserRepository;
import br.com.finsavior.events.processor.service.UserService;
import br.com.finsavior.grpc.user.DeleteAccountRequest;
import br.com.finsavior.grpc.user.DeleteAccountResponse;
import br.com.finsavior.grpc.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PlanHistoryRepository planHistoryRepository;

    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;
    private final static String APP_ID = "finsavior-app-events";

    @PostConstruct
    public void initialize() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6566)
                .usePlaintext()
                .build();

        userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void deleteAccount(DeleteAccountRequest deleteAccountRequest) {
        try {
            DeleteAccountResponse deleteAccountResponse = userServiceBlockingStub.deleteAccount(deleteAccountRequest);
        } catch (StatusRuntimeException e) {
            log.error("method: {}, message: {} - {}, error: {}",
                    "deleteAccount", "Error deleting account",
                    deleteAccountRequest.getUsername(),
                    e.getStatus().getDescription());

            throw new DeleteAccountException(e.getStatus().getDescription());
        }
    }

    @Override
    public void updateUserPlan(ExternalUserDTO externalUserdto){
        User user = userRepository.getById(externalUserdto.getUserId());
        String planId = externalUserdto.getPlanId();

        try {
            PlanChangeHistory planChangeHistory = getPlanchangeHistory(externalUserdto, planId);
            setProfileAndPlan(user, planId);

            userRepository.save(user);

            planHistoryRepository.save(planChangeHistory);

            log.info("method = updateUserPlan, message = Plano do user: {}, atualizado com sucesso!", externalUserdto.getUserId());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    private PlanChangeHistory getPlanchangeHistory(ExternalUserDTO externalUserdto, String planId) {
        return PlanChangeHistory.builder()
                .planId(planId)
                .userId(externalUserdto.getUserId())
                .externalUserId(externalUserdto.getExternalUserId())
                .planType(PlanType.fromValue(externalUserdto.getPlanId()).getPlanTypeId())
                .updateTime(LocalDateTime.now())
                .delFg(Flag.N)
                .userInsDtm(LocalDateTime.now())
                .userInsId(APP_ID)
                .userUpdDtm(LocalDateTime.now())
                .userUpdId(APP_ID)
                .build();
    }

    private void setProfileAndPlan(User user, String planId) {
        user.getUserPlan().setPlanId(planId);
        user.getUserPlan().setUserUpdDtm(LocalDateTime.now());
        user.getUserPlan().setUserUpdId(APP_ID);

        user.getUserProfile().setPlanId(planId);
        user.getUserProfile().setUserUpdDtm(LocalDateTime.now());
        user.getUserProfile().setUserUpdId(APP_ID);
    }
}
