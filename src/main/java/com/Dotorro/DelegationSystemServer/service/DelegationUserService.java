package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationUserDTO;
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
    private final EmailService emailService;

    public DelegationUserService(DelegationUserRepository delegationUserRepository,
                                 DelegationService delegationService,
                                 UserService userService,
                                 EmailService emailService) {
        this.delegationUserRepository = delegationUserRepository;
        this.delegationService = delegationService;
        this.userService = userService;
        this.emailService = emailService;
    }

    public void validateDelegationUser(DelegationUser delegationUser){
        if (delegationUser.getUser() == null) {
            throw new RuntimeException("User not found");
        }

        if (delegationUser.getDelegation() == null) {
            throw new RuntimeException("Delegation not found");
        }
    }

    public List<DelegationUser> getAllDelegationUsers() {
        return delegationUserRepository.findAll();
    }

    public DelegationUser getDelegationUserByDelegationIdUserId(Long delegationId, Long userId) {
        DelegationUserKey id = new DelegationUserKey(delegationId,userId);

        return delegationUserRepository.findById(id).orElse(null);
    }

    public DelegationUser createDelegationUser(DelegationUserDTO delegationUserDTO) {
        User user = userService.getUserById(delegationUserDTO.getUserId());
        Delegation delegation = delegationService.getDelegationById(delegationUserDTO.getDelegationId());
        emailService.sendAddToDelegationMail(user, delegation);
        return delegationUserRepository.save(convertToEntity(delegationUserDTO));
    }

    public DelegationUser updateDelegationUser(Long delegationId, Long userId, DelegationUserDTO delegationUserDTO)
    {
        DelegationUserKey id = new DelegationUserKey(delegationId,userId);

        Optional<DelegationUser> optionalDelegationUser = delegationUserRepository.findById(id);

        if (optionalDelegationUser.isPresent()) {
            DelegationUser updatedDelegationUser = convertToEntity(delegationUserDTO);

            DelegationUser delegationUser = optionalDelegationUser.get();
            delegationUser.setId(updatedDelegationUser.getId());
            delegationUser.setDelegation(updatedDelegationUser.getDelegation());
            delegationUser.setUser(updatedDelegationUser.getUser());

            return delegationUserRepository.save(delegationUser);
        } else {
            throw new RuntimeException("Delegation User not found with delegation id: " + delegationId + " and user id: " + userId);
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

        DelegationUser delegationUser = new DelegationUser(
                delegation,
                user
        );

        validateDelegationUser(delegationUser);

        return delegationUser;
    }

    private DelegationUserDTO convertToDTO(DelegationUser delegationUser) {
        return new DelegationUserDTO(
                delegationUser.getDelegation().getId(),
                delegationUser.getUser().getId()
        );
    }
}
