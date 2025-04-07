package com.example.bot.guild.service.interfaces;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public interface IGuildService {

    void onJoinGuild(Guild guild);

    void onExitGuild(Guild guild);

    void onMemberJoin(GuildMemberJoinEvent event);
}
