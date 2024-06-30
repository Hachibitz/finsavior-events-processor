package br.com.finsavior.events.processor.mapper;

import br.com.finsavior.events.processor.model.dto.ExternalUserDTO;
import br.com.finsavior.events.processor.model.entity.ExternalUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExternalUserMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.firstName", target = "name")
    @Mapping(source = "user.userPlan.planId", target = "planId")
    ExternalUserDTO toExternalUserDTO(ExternalUser externalUser);
}
