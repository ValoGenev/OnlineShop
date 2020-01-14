package com.app.project.repositories;

import com.app.project.domain.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {




}
