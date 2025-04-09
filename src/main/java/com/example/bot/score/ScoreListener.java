package com.example.bot.score;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import com.example.bot.commands.interfaces.IScoreCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScoreListener extends ListenerAdapter {

    private final Map<String, IScoreCommand> scoreCommands;

    @Autowired
    public ScoreListener(final Map<String, IScoreCommand> scoreCommands) {
        this.scoreCommands = scoreCommands;
        System.out.println(scoreCommands);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        // ToDo sumar puntos
        User user = event.getAuthor();
        if (user.isBot() || user.isSystem()) return;
        Message msg =  event.getMessage();
        System.out.println(msg.getContentRaw());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        IScoreCommand command = scoreCommands.get(event.getName());

        if (command == null) return;
        else command.execute(event);
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        // ToDo Crear la entidad del usuario en la guild
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        // ToDo Eliminar la entidad del usuario en la guild (penitencia por haber salido o haber sido eliminado/banneado
    }
}
