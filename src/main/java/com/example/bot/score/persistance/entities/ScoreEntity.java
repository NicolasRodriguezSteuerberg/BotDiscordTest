package com.example.bot.score.persistance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class ScoreEntity {
    @Id
    private String id;
    @Column(name = "text_exp")
    private Long textExp;
    @Column(name = "voice_exp")
    private Long voiceExp;
}
