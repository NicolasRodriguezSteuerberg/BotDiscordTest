package com.example.bot.score.commands;

import com.example.bot.commands.interfaces.IScoreCommand;
import com.example.bot.configuration.CustomName;
import com.example.bot.utils.constants.CommandConstants;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
@CustomName(CommandConstants.TIER_LIST)
public class PointsCommandImplementation implements IScoreCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Points command").queue();
    }
}
