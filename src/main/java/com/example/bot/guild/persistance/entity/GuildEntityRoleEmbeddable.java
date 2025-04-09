package com.example.bot.guild.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GuildEntityRoleEmbeddable {
    @Column(name = "emoji")
    private String emoji;
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "message_id")
    private String messageId;
    @Column(name = "chat_id")
    private String chatId;
}
