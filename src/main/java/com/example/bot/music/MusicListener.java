package com.example.bot.music;

import com.example.bot.commands.interfaces.IMusicCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MusicListener extends ListenerAdapter {
    private final Map<String, IMusicCommand> musicSlashCommandsMap;

    public MusicListener(final Map<String, IMusicCommand> musicSlashCommandsMap) {
        this.musicSlashCommandsMap = musicSlashCommandsMap;
        System.out.println(musicSlashCommandsMap);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        IMusicCommand command = musicSlashCommandsMap.get(event.getName());
        if (command == null) return;
        command.execute(event);
    }
}
