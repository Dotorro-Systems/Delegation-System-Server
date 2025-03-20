package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.NoteDTO;
import com.Dotorro.DelegationSystemServer.model.Note;
import com.Dotorro.DelegationSystemServer.service.NoteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "*")
public class NoteController {
    public final NoteService noteService;

    public  NoteController(NoteService noteService){this.noteService = noteService;}

    @GetMapping
    public List<Note> getNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping
    public Note createNote(@RequestBody NoteDTO noteDTO) {
        return noteService.createNote(noteDTO);
    }
}
