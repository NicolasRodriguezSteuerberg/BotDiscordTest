package com.example.bot.configuration;

import com.example.bot.guild.listener.GuildListener;
import com.example.bot.music.MusicListener;
import com.example.bot.score.ScoreListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {
    @Value("${discord.token}")
    private String botToken;

    @Autowired
    private ScoreListener scoreListener;
    @Autowired
    private MusicListener musicListener;
    @Autowired
    private GuildListener guildListener;

    @Bean
    public JDA jdaBuilder() throws InterruptedException {
        return JDABuilder
                .createDefault(botToken)
                .setActivity(Activity.of(Activity.ActivityType.CUSTOM_STATUS, "hola"))
                .addEventListeners(scoreListener, musicListener, guildListener)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                .build()
                .awaitReady();
    }
}
