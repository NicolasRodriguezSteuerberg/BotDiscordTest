package com.example.bot.commands.interfaces;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface ICommand {
    void execute(SlashCommandInteractionEvent event);
}
