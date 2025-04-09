package com.example.bot.guild.service.implementation;

import com.example.bot.guild.persistance.entity.GuildEntity;
import com.example.bot.guild.persistance.entity.GuildEntityRoleEmbeddable;
import com.example.bot.guild.persistance.repository.IGuildRepository;
import com.example.bot.guild.service.interfaces.IGuildService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.ContextException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuildServiceImpl implements IGuildService {

    Logger log = LoggerFactory.getLogger(GuildServiceImpl.class);

    @Autowired
    private IGuildRepository guildRepository;

    @Override
    public void onJoinGuild(Guild guild) {
        String id = guild.getId();
        GuildEntity newGuild = GuildEntity.builder().id(id).build();
        guildRepository.save(newGuild);
    }

    @Override
    public void onExitGuild(Guild guild) {
        String guildId = guild.getId();
        guildRepository.deleteById(guildId);
    }

    @Override
    public void onMessageReacted(MessageReactionAddEvent event) {
        Guild guild = event.getGuild();
        GuildEntityRoleEmbeddable roleInfo = guildRepository.findById(guild.getId()).orElseThrow().getRoleInfo();

        if (roleInfo == null) {
            log.warn("El server no tiene implementado esta función");
            return;
        }

        if (!event.getMessageId().equals(roleInfo.getMessageId())){
            log.warn("No es el mensaje deseado");
            return;
        }

        if(!event.getEmoji().getFormatted().equals(roleInfo.getEmoji())){
            log.warn("No es el mismo emoji");
            return;
        }
        log.info("hola");
        guild.addRoleToMember(event.getMember(), guild.getRoleById(roleInfo.getRoleId())).complete();

    }


    @Override
    public void onMemberJoin(GuildMemberJoinEvent event) {
        log.info("MEMBER_JOIN:: Se ha unido alguien");
        Guild guild = event.getGuild();
        User user = event.getUser();
        if (user.isBot()){
            log.warn("MEMBER_JOIN:: Es un bot");
            return;
        }
        GuildEntity entity = guildRepository.findById(guild.getId()).orElseThrow();
        if (entity.getGoodByeChat() == null){
            log.warn("MEMBER_JOIN:: La guild no tiene configurado el bot para esta funcion");
            return;
        }
        TextChannel channel = guild.getTextChannelById(entity.getWelcomeChat());
        if (channel == null) {
            log.error("MEMBER_JOIN:: No se pudo encontrar el chat de voz (posible eliminacion)");
            return;
        }
        Member member = event.getMember();

        MessageEmbed embed = new EmbedBuilder()
                .setThumbnail(member.getEffectiveAvatarUrl())
                .setTitle(member.getEffectiveName() + " se ha unido al servidor")
                .build();
        try{
            channel.sendMessage(member.getAsMention() + " se ha unido al canal")
                    .setEmbeds(embed)
                    .queue();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    @Override
    public void onMemberExit(GuildMemberRemoveEvent event) {
        // ToDo crear mensajes personalizables
        Guild guild = event.getGuild();
        User user = event.getUser();
        if (user.isBot()){
            log.warn("MEMBER_EXIT:: El usuario es un bot");
            return;
        }
        GuildEntity entity = guildRepository.findById(guild.getId()).orElseThrow();
        if (entity.getGoodByeChat() == null) {
            log.warn("MEMBER_EXIT:: La guild no esta configurada para mensajes de despedida");
            return;
        }
        TextChannel channel = guild.getTextChannelById(entity.getGoodByeChat());
        if (channel == null) {
            log.error("MEMBER_EXIT:: Es probable que la guild haya borrado el canal de texto");
            return;
        }
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(user.getEffectiveName(), null, user.getEffectiveAvatarUrl())
                .setTitle("Ya no está entre nosotros")
                .build();
        channel
                .sendMessage("El usuario "+ user.getAsMention() + " ha abandonado el servidor")
                .setEmbeds(embed)
                .queue();

        sendPrivateMessageOnMemberExit(user, embed);
    }

    // ToDo es posible que esto no se pueda
    public void sendPrivateMessageOnMemberExit(User user, MessageEmbed embed) {
        // ToDo crear opcion para mandar mensajes privado
        PrivateChannel privateChannel;
        try {
            privateChannel = user.openPrivateChannel().complete();
            System.out.println("hola");
            if (privateChannel != null && privateChannel.canTalk()) {
                log.info("ENTRA EN IF");
                privateChannel
                        .sendMessage("adios")
                        .setEmbeds(embed)
                        .complete();
            }
        } catch (RuntimeException e) {
            log.error("MEMBER_EXIT:: Error mandando mensaje privado{}: {}", e.getClass().getName(), e.getMessage());
        }
    }

    @Override
    public TextChannel getOrCreateTextChannel(Guild guild, String channelName) {
        List<TextChannel> guildTextChannels = guild.getTextChannelsByName(channelName, false);
        if (guildTextChannels.isEmpty()) {
            return guild.createTextChannel(channelName).complete();
        } else {
            return guildTextChannels.get(0);
        }
    }
}
