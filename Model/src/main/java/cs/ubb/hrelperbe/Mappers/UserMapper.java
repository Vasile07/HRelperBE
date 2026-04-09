package cs.ubb.hrelperbe.Mappers;

import cs.ubb.hrelperbe.BaseModels.User;
import cs.ubb.hrelperbe.DTOs.AccountRegistrationData;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.generated", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface UserMapper {
    User toEntity(AccountRegistrationData accountRegistrationData);
}
