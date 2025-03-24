package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationUserDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.DelegationUser;
import com.Dotorro.DelegationSystemServer.model.DelegationUserKey;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.DelegationUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public DelegationUser getDelegationUserByDelegationIdUserId(Long delegationId, Long userId)
    {
        DelegationUserKey id = new DelegationUserKey(delegationId,userId);
        return delegationUserRepository.findById(id).orElse(null);
    }

    public DelegationUser createDelegationUser(DelegationUserDTO delegationUserDTO) {
        return delegationUserRepository.save(convertToEntity(delegationUserDTO));
    }

    public DelegationUser updateDelegationUser(Long delegationId, Long userId, DelegationUserDTO delegationUserDTO)
    {
        DelegationUserKey id = new DelegationUserKey(delegationId,userId);

        Optional<DelegationUser> optionalDelegationUser = delegationUserRepository.findById(id);

        DelegationUser updatedDelegationUser = convertToEntity(delegationUserDTO);

        if (optionalDelegationUser.isPresent()) {
            DelegationUser delegationUser = optionalDelegationUser.get();
            delegationUser.setDelegation(updatedDelegationUser.getDelegation());
            delegationUser.setUser(updatedDelegationUser.getUser());

            return delegationUserRepository.save(delegationUser);
        } else {
            throw new RuntimeException("DelegationUser not found with DelegationId: " + delegationId + " and UserId: " + userId);
        }
    }

    public void deleteDelegationUser(Long delegationId, Long userId)
    {
        DelegationUserKey id = new DelegationUserKey(delegationId,userId);
        delegationUserRepository.deleteById(id);
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
