package com.Dotorro.DelegationSystemServer.repository;

import com.Dotorro.DelegationSystemServer.model.Note;
import com.Dotorro.DelegationSystemServer.model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
}
