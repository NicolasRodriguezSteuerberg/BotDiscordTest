package com.example.bot.guild.command;

import com.example.bot.commands.interfaces.IGuildCommand;
import com.example.bot.configuration.CustomName;
import com.example.bot.guild.persistance.repository.IGuildRepository;
import com.example.bot.utils.constants.CommandConstants;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CustomName(CommandConstants.SET_WELCOME_CHAT)
public class WelcomeChat implements IGuildCommand {

    @Autowired
    private IGuildRepository guildRepository;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String channelName = event.getOption(CommandConstants.SET_WELCOME_CHAT.getOptions()[0].getName()).getAsString();
        Guild guild = event.getGuild();

        TextChannel txtChannel;
        List<TextChannel> textChannelList = guild.getTextChannelsByName(channelName, false);
        System.out.println(textChannelList);
        if (textChannelList.isEmpty()){
             txtChannel = guild.createTextChannel(channelName).complete();
            System.out.println(txtChannel);
        } else {
            txtChannel = textChannelList.get(0);
        }
        int updated = guildRepository.updateWelcomeChat(txtChannel.getId(), guild.getId());
        String message;
        System.out.println(updated);
        if (updated > 0){
            message = "Se ha cambiado exitosamente el chat de bienvenidas";
        } else {
            message = "Hubo un error al cambiar el chat de bienvenidas";
        }
        event.reply(message).queue();
    }
}
