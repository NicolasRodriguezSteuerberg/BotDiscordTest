package com.example.bot.score;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import com.example.bot.commands.interfaces.IScoreCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
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
        User user = event.getAuthor();
        if (user.isBot() || user.isSystem()) return;
        Message msg =  event.getMessage();
        System.out.println(msg);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        IScoreCommand command = scoreCommands.get(event.getName());

        if (command == null) return;
        else command.execute(event);
    }

}
