package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.StageDTO;
import com.Dotorro.DelegationSystemServer.enums.StageType;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Stage;
import com.Dotorro.DelegationSystemServer.repository.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class StageService {

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private DelegationService delegationService;

    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    public Stage getStageById(Long id) {
        return stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage not found with id " + id));
    }

    public List<Stage> getStageByDelegationId(Long delegationId) {
        return stageRepository.findByDelegationId(delegationId);
    }

    public Stage createStage(StageDTO stageDTO) {
        return stageRepository.save(convertToEntity(stageDTO));
    }

    public Stage updateStage(Long id, StageDTO stageDTO) {
        Stage stage = getStageById(id);

        Stage updatedStage = convertToEntity(stageDTO);

        stage.setDelegation(updatedStage.getDelegation());
        stage.setType(updatedStage.getType());
        stage.setPlace(updatedStage.getPlace());
        stage.setDescription(updatedStage.getDescription());
        stage.setTime(updatedStage.getTime());

        return stageRepository.save(stage);
    }

    public void deleteStage(Long id) {
        stageRepository.deleteById(id);
    }

    public Stage convertToEntity(StageDTO stageDTO) {
        Delegation delegation = delegationService.getDelegationById(stageDTO.getDelegationId());

        StageType type;
        try {
            type = StageType.valueOf(stageDTO.getType());
        }
        catch (Exception e) {
            throw new InvalidParameterException("No stage type with provided " + stageDTO.getType());
        }

        Stage stage = new Stage(
                delegation,
                type,
                stageDTO.getPlace(),
                stageDTO.getDescription(),
                stageDTO.getWhen()
        );

        validateStage(stage);

        return stage;
    }

    public StageDTO convertToDTO(Stage stage) {
        return new StageDTO(
                stage.getDelegation().getId(),
                stage.getType().toString(),
                stage.getPlace(),
                stage.getDescription(),
                stage.getTime()
        );
    }

    public void validateStage(Stage stage) {
        // TODO: stage validation
    }
}
