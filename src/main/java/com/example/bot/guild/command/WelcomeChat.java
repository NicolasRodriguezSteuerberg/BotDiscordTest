package com.example.bot.guild.command;

import com.example.bot.commands.interfaces.IGuildCommand;
import com.example.bot.configuration.CustomName;
import com.example.bot.guild.persistance.repository.IGuildRepository;
import com.example.bot.guild.service.implementation.GuildServiceImpl;
import com.example.bot.utils.constants.CommandConstants;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CustomName(CommandConstants.SET_WELCOME_CHAT)
public class WelcomeChat implements IGuildCommand {

    @Autowired
    private GuildServiceImpl guildService;
    @Autowired
    private IGuildRepository guildRepository;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        OptionMapping option = event.getOption(CommandConstants.SET_WELCOME_CHAT.getOptions()[0].getName());
        if (option == null){
            event.reply("No se ha recibido la opcion necesaria").queue();
            return;
        }
        try{
            TextChannel selectedChannel = option.getAsChannel().asTextChannel();
            int updated = guildRepository.updateWelcomeChat(selectedChannel.getId(), guild.getId());
            if (updated > 0){
                event.reply("Se ha cambiado exitosamente el chat de bienvenidas").queue();
            } else {
                event.reply("Hubo un error al cambiar el chat de bienvenidas").queue();
            }
        } catch (IllegalStateException e) {
            event.reply("Error, el canal seleccionado no es un canal de texto").queue();
        }
    }
}
