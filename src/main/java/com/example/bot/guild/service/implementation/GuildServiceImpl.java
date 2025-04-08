package com.example.bot.guild.service.implementation;

import com.example.bot.guild.persistance.entity.GuildEntity;
import com.example.bot.guild.persistance.repository.IGuildRepository;
import com.example.bot.guild.service.interfaces.IGuildService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildServiceImpl implements IGuildService {

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
    public void onMemberJoin(GuildMemberJoinEvent event) {
        System.out.println("Alguien se unio");
        Guild guild = event.getGuild();
        String guildId = guild.getId();
        Member member = event.getMember();
        String memberId = member.getId();
        // ToDo crear usuario para el score

        // ToDo recoger mensajes de la base de datos y cambiar el titulo

        MessageEmbed embed = new EmbedBuilder()
                .setThumbnail(member.getEffectiveAvatarUrl())
                .setTitle(member.getEffectiveName() + " se ha unido al servidor")
                .build();

        // ToDo recoger el canal de texto de las bienvenidas
        GuildEntity entity = guildRepository.findById(guildId).orElseThrow();
        TextChannel channel = guild.getTextChannelById(entity.getWelcomeChat());
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
        Guild guild = event.getGuild();
        User user = event.getUser();
        if (user.isBot()){
            return;
        }
        GuildEntity entity = guildRepository.findById(guild.getId()).orElseThrow();
        if (entity.getGoodByeChat() == null) return;
        TextChannel channel = guild.getTextChannelById(entity.getGoodByeChat());
        if (channel == null) return;
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(user.getEffectiveName(), null, user.getEffectiveAvatarUrl())
                .setTitle("Ya no está entre nosotros")
                .build();
        channel
                .sendMessage("El usuario "+ user.getAsMention() + " ha abandonado el servidor")
                .setEmbeds(embed)
                .queue();
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
