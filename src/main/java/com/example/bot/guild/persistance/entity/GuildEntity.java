package com.example.bot.guild.persistance.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "guild")
public class GuildEntity {
    @Id
    private String id;
    @Column(name = "default_role")
    private String defaultRole;
    @Column(name = "info_chat")
    private String infoChat;
    @Column(name = "welcome_chat")
    private String welcomeChat;
    @Column(name = "bye_chat")
    private String byeChat;
}
