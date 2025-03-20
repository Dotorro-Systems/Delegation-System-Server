package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationUserDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.DelegationUser;
import com.Dotorro.DelegationSystemServer.model.DelegationUserKey;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.DelegationUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelegationUserService {
    private final DelegationUserRepository delegationUserRepository;
    private final DelegationService delegationService;
    private final UserService userService;

    public DelegationUserService(DelegationUserRepository delegationUserRepository,
                                 DelegationService delegationService,
                                 UserService userService) {
        this.delegationUserRepository = delegationUserRepository;
        this.delegationService = delegationService;
        this.userService = userService;
    }

    public List<DelegationUser> getAllDelegationUsers() {
        return delegationUserRepository.findAll();
    }

    public DelegationUser createDelegationUser(DelegationUserDTO delegationUserDTO) {
        return delegationUserRepository.save(convertToEntity(delegationUserDTO));
    }

    private DelegationUser convertToEntity(DelegationUserDTO delegationUserDTO) {
        Delegation delegation = delegationService.getDelegationById(delegationUserDTO.getDelegationId());

        User user = userService.getUserById(delegationUserDTO.getUserId());

        return new DelegationUser(
                new DelegationUserKey(
                        delegationUserDTO.getDelegationId(),
                        delegationUserDTO.getUserId()
                ),
                delegation,
                user
        );
    }

    private DelegationUserDTO convertToDTO(DelegationUser delegationUser) {
        return new DelegationUserDTO(
                delegationUser.getDelegation().getId(),
                delegationUser.getUser().getId()
        );
    }
}
