package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.NoteDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.*;
import com.Dotorro.DelegationSystemServer.model.Note;
import com.Dotorro.DelegationSystemServer.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final DelegationService delegationService;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository, DelegationService delegationService, UserService userService) {
        this.noteRepository = noteRepository;
        this.delegationService = delegationService;
        this.userService = userService;
    }

    public void noteValidation(NoteDTO noteDTO){
        User user = userService.getUserById(noteDTO.getUserId());
        if (user == null){
            throw new RuntimeException("User not found with id: "+noteDTO.getUserId());
        }
        Delegation delegation = delegationService.getDelegationById(noteDTO.getDelegationId());
        if (delegation == null){
            throw new RuntimeException("Delegation not found with id: "+noteDTO.getDelegationId());
        }

        if(noteDTO.getCreatedAt().isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("The date can not be from the future");
        }
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(Long noteId)
    {
        return noteRepository.findById(noteId).orElse(null);
    }

    public Note createNote(NoteDTO noteDTO) {
        noteValidation(noteDTO);
        return noteRepository.save(convertToEntity(noteDTO));
    }

    public Note updateNote(Long id, NoteDTO noteDTO)
    {
        Optional<Note> optionalNote = noteRepository.findById(id);
        noteValidation(noteDTO);
        Note updatedNote = convertToEntity(noteDTO);

        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            note.setDelegation(updatedNote.getDelegation());
            note.setUser(updatedNote.getUser());
            note.setContent(updatedNote.getContent());
            note.setCreatedAt(updatedNote.getCreatedAt());

            return noteRepository.save(note);
        } else {
            throw new RuntimeException("Note not found with id: " + id);
        }
    }

    public void deleteNote(Long id)
    {
        noteRepository.deleteById(id);
    }

    private Note convertToEntity(NoteDTO noteDTO) {
        Delegation delegation = delegationService.getDelegationById(noteDTO.getDelegationId());

        User user = userService.getUserById(noteDTO.getUserId());

        return new Note(
                delegation,
                user,
                noteDTO.getContent(),
                noteDTO.getCreatedAt()
        );
    }

    private NoteDTO convertToDTO(Note note)
    {
        return new NoteDTO(
                note.getDelegation().getId(),
                note.getUser().getId(),
                note.getContent(),
                note.getCreatedAt()
        );
    }
}
