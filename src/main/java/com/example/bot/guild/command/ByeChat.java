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

@Component
@CustomName(CommandConstants.SET_GOOD_BYE_CHAT)
public class ByeChat implements IGuildCommand {

    @Autowired
    private GuildServiceImpl guildService;
    @Autowired
    private IGuildRepository guildRepository;


    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (guild == null){
            event.reply("Debes estar en una guild").queue();
            return;
        }
        OptionMapping option = event.getOption(CommandConstants.SET_GOOD_BYE_CHAT.getOptions()[0].getName());
        if (option == null){
            event.reply("No se ha recibido la opcion necesaria").queue();
            return;
        }
        String txtChannelOption = option.getAsString();
        TextChannel selectedTextChannel = guildService.getOrCreateTextChannel(guild, txtChannelOption);

        int updated = guildRepository.updateGoodByeChannel(selectedTextChannel.getId(), guild.getId());
        if (updated > 0) {
            event.reply("Canal de texto de despedidas cambiado exitosamente").queue();
        } else {
            event.reply("Error al cambiar el canal de texto de despedidas").queue();
        }

    }
}
