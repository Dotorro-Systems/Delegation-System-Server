package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.NoteDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Note;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "*")
public class NoteController {
    public final NoteService noteService;

    public  NoteController(NoteService noteService){this.noteService = noteService;}

    @GetMapping(value = "/")
    public List<Note> getNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping(value = "/{id}")
    public Note getNoteById(@PathVariable Long id)
    {
        return noteService.getNoteById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody NoteDTO noteDTO)
    {
        try {
            Note savedNote = noteService.updateNote(id, noteDTO);
            return ResponseEntity.ok(savedNote);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteNoteById(@PathVariable Long id)
    {
        noteService.deleteNote(id);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createNote(@RequestBody NoteDTO noteDTO) {
        try {
            Note savedNote = noteService.createNote(noteDTO);
            return ResponseEntity.ok(savedNote);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
