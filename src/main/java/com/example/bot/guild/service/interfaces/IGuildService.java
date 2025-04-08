package com.example.bot.guild.service.interfaces;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

public interface IGuildService {

    void onJoinGuild(Guild guild);

    void onExitGuild(Guild guild);

    void onMemberJoin(GuildMemberJoinEvent event);

    void onMemberExit(GuildMemberRemoveEvent event);

    TextChannel getOrCreateTextChannel(Guild guild, String channelName);
}
