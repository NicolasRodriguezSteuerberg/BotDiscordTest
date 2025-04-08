package com.example.bot.guild.listener;

import com.example.bot.commands.interfaces.IGuildCommand;
import com.example.bot.guild.service.implementation.GuildServiceImpl;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.thread.member.ThreadMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Member;
import java.util.Map;

@Component
public class GuildListener extends ListenerAdapter {

    private final GuildServiceImpl guildService;

    private final Map<String, IGuildCommand> commandHashMap;

    @Autowired
    public GuildListener(final GuildServiceImpl guildService, final Map<String, IGuildCommand> commandHashMap){
        this.guildService = guildService;
        this.commandHashMap = commandHashMap;
        System.out.println(commandHashMap);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        IGuildCommand command = commandHashMap.get(event.getName());
        if (command==null) return;
        command.execute(event);
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        System.out.println("hola");
        guildService.onMemberJoin(event);
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        guildService.onMemberExit(event);
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        System.out.println("HOLA onGuildJoin");
        guildService.onJoinGuild(event.getGuild());
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        guildService.onExitGuild(event.getGuild());
    }


}
