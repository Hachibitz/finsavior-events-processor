package br.com.finsavior.events.processor.service.impl;

import br.com.finsavior.events.processor.exception.BusinessException;
import br.com.finsavior.events.processor.exception.EventNotFound;
import br.com.finsavior.events.processor.mapper.ExternalUserMapper;
import br.com.finsavior.events.processor.model.constant.PlanType;
import br.com.finsavior.events.processor.model.dto.ExternalUserDTO;
import br.com.finsavior.events.processor.model.dto.WebhookRequestDTO;
import br.com.finsavior.events.processor.repository.ExternalUserRepository;
import br.com.finsavior.events.processor.service.UserService;
import br.com.finsavior.events.processor.service.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static br.com.finsavior.events.processor.model.constant.EventTypeEnum.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {

    private final ExternalUserRepository externalUserRepository;
    private final UserService userService;
    private final ExternalUserMapper externalUserMapper;

    @Override
    public void processWebhook(WebhookRequestDTO webhookRequestDTO) {
        ExternalUserDTO externalUserdto;
        try {
            externalUserdto = externalUserMapper.toExternalUserDTO(
                    externalUserRepository.findBySubscriptionId(webhookRequestDTO.getResource().getId())
            );

            switch(webhookRequestDTO.getEvent_type()){
                case BILLING_SUBSCRIPTION_ACTIVATED -> activatedEvent(externalUserdto, webhookRequestDTO);
                case BILLING_SUBSCRIPTION_EXPIRED -> expiredEvent(externalUserdto);
                case BILLING_SUBSCRIPTION_CANCELLED -> cancelledEvent(externalUserdto);
                case BILLING_SUBSCRIPTION_PAYMENT_FAILED -> paymentFailedEvent(externalUserdto);
                case BILLING_SUBSCRIPTION_SUSPENDED -> suspendedEvent(externalUserdto);
                default -> throw new EventNotFound("Evento não mapeado!");

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void suspendedEvent(ExternalUserDTO externalUser) {
        try {
            downgradeUserPlan(externalUser);
        } catch (Exception e) {
            throw new BusinessException("Error ao atualizar plano do usuário: " + externalUser.getUserId());
        }
    }

    private void paymentFailedEvent(ExternalUserDTO externalUser) {
        try {
            downgradeUserPlan(externalUser);
        } catch (Exception e) {
            throw new BusinessException("Error ao atualizar plano do usuário: " + externalUser.getUserId());
        }
    }

    private void cancelledEvent(ExternalUserDTO externalUser) {
        try {
            downgradeUserPlan(externalUser);
        } catch (Exception e) {
            throw new BusinessException("Error ao atualizar plano do usuário: " + externalUser.getUserId());
        }
    }

    private void expiredEvent(ExternalUserDTO externalUser) {
        try {
            downgradeUserPlan(externalUser);
        } catch (Exception e) {
            throw new BusinessException("Error ao atualizar plano do usuário: " + externalUser.getUserId());
        }
    }

    private void activatedEvent(ExternalUserDTO externalUser, WebhookRequestDTO webhookRequestDTO) {
        try {
            upgradeUserPlan(externalUser, webhookRequestDTO);
        } catch (Exception e) {
            throw new BusinessException("Error ao atualizar plano do usuário: " + externalUser.getUserId());
        }
    }

    private void createdEvent(ExternalUserDTO externalUser, WebhookRequestDTO webhookRequestDTO) {
    }

    private void downgradeUserPlan(ExternalUserDTO externalUser) {
        if((Objects.equals(externalUser.getPlanId(), PlanType.PLUS.getPlanTypeId()) ||
                Objects.equals(externalUser.getPlanId(), PlanType.PREMIUM.getPlanTypeId()))){
            externalUser.setPlanId(PlanType.FREE.getPlanTypeId());
            userService.updateUserPlan(externalUser);
        }
    }

    private void upgradeUserPlan(ExternalUserDTO externalUser, WebhookRequestDTO webhookRequestDTO) {
        if(Objects.equals(externalUser.getPlanId(), PlanType.FREE.getPlanTypeId())){
            externalUser.setPlanId(
                    PlanType.fromValue(webhookRequestDTO.getResource().getPlanId()).getPlanTypeId());
            userService.updateUserPlan(externalUser);
        }
    }
}
