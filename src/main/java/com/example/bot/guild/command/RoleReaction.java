package com.example.bot.guild.command;

import com.example.bot.commands.interfaces.IGuildCommand;
import com.example.bot.configuration.CustomName;
import com.example.bot.guild.persistance.entity.GuildEntityRoleEmbeddable;
import com.example.bot.guild.persistance.repository.IGuildRepository;
import com.example.bot.utils.constants.CommandConstants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@CustomName(CommandConstants.SET_ROLE_REACTION)
public class RoleReaction implements IGuildCommand {

    Logger log = LoggerFactory.getLogger(RoleReaction.class);

    @Autowired
    private IGuildRepository guildRepository;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        CommandConstants.Option[] options = CommandConstants.SET_ROLE_REACTION.getOptions();
        String roleSelected = options[0].getName();
        String emojiSelected = options[1].getName();
        String chatSelected = options[2].getName();

        OptionMapping roleOption = event.getOption(roleSelected);
        OptionMapping emojiOption = event.getOption(emojiSelected);
        try{
            TextChannel textChannel;
            OptionMapping chatOption = event.getOption(chatSelected);
            if (chatOption==null){
                textChannel = event.getChannel().asTextChannel();
            } else{
                textChannel = chatOption.getAsChannel().asTextChannel();
            }

            String role = roleOption.getAsRole().getId();
            String emoji = emojiOption.getAsString();
            Message msg = textChannel.sendMessage("Reaccione con " + emojiOption.getAsString() + " para poder tener emoji").complete();
            guildRepository.updateRoleInfo(
                    GuildEntityRoleEmbeddable.builder()
                            .emoji(emoji)
                            .roleId(role)
                            .messageId(msg.getId())
                            .chatId(textChannel.getId())
                            .build()
                    ,event.getGuild().getId()
            );

            event.reply("Emoji message creado correctamente").queue();
        } catch (NullPointerException e){
            log.error("{}: {}", e.getClass().getName(), e.getMessage());
            event.reply("Por favor seleccione el rol y el emoji").queue();
        } catch (IllegalStateException e) {
            event.reply("Error, el canal seleccionado/en el que estás, tiene que ser un canal de texto").queue();
        }

    }
}
