package com.example.bot.guild.persistance.repository;

import com.example.bot.guild.persistance.entity.GuildEntity;
import com.example.bot.guild.persistance.entity.GuildEntityRoleEmbeddable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IGuildRepository extends JpaRepository<GuildEntity, String> {
    @Modifying
    @Transactional
    @Query("UPDATE GuildEntity guild SET guild.welcomeChat = :new_value where guild.id = :cond_value")
    int updateWelcomeChat(@Param("new_value") String welcomeTextId, @Param("cond_value") String guildId);

    @Modifying
    @Transactional
    @Query("UPDATE GuildEntity guild SET guild.goodByeChat = :new_value where guild.id = :cond_value")
    int updateGoodByeChannel(@Param("new_value") String goodByeChat, @Param("cond_value") String guildId);

    @Modifying
    @Transactional
    @Query("UPDATE GuildEntity guild SET guild.roleInfo = :new_value where guild.id = :cond_value")
    int updateRoleInfo(@Param("new_value")GuildEntityRoleEmbeddable roleInfo, @Param("cond_value") String guildId);
}
