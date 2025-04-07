package com.example.bot.commands;

import com.example.bot.utils.constants.CommandConstants;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AddCommandsBot {

    private final JDA jda;

    public AddCommandsBot(final JDA jda){
        this.jda = jda;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        // recogemos el objeto que modifica los comandos
        CommandListUpdateAction updateCommands = jda.updateCommands();
        // recorremos cada comando
        for(CommandConstants command: CommandConstants.values()){
            SlashCommandData commandData = Commands.slash(command.getName(), command.getDescription());
            // solo permitimos que se puedan usar en un server
            commandData = commandData.setContexts(InteractionContextType.GUILD);
            commandData = commandData.setDefaultPermissions(command.getDefaultMemberPermissions());
            // agregamos las opciones de ser el caso de tener
            if (command.getOptions()!= null) {
                for (CommandConstants.Option option : command.getOptions()) {
                    commandData.addOption(
                            option.getType(), option.getName(), option.getDescription(),
                            option.isRequired(), option.isAutocomplete()
                    );
                }
            }
            updateCommands.addCommands(commandData);
        }

        updateCommands.queue();
    }
}
