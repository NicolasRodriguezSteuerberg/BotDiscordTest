package com.example.bot.music.commands;

import com.example.bot.commands.interfaces.IMusicCommand;
import com.example.bot.configuration.CustomName;
import com.example.bot.utils.constants.CommandConstants;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
@CustomName(CommandConstants.PLAY)
public class PlayCommand implements IMusicCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String a = event.getOption("cancion").getAsString();
        event.reply("Play command").queue();
    }
}
