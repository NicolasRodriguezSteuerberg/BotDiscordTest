package com.example.bot.utils.constants;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@Getter
public enum CommandConstants {
    // GUILD
    SET_WELCOME_CHAT(
            "set_welcome_chat",
            "Agrega un canal de texto como chat de bienvenidas",
            DefaultMemberPermissions.DISABLED,
            new Option[]{
                    new Option("chat", "Chat de texto", OptionType.CHANNEL, true, false)
            }
    ),
    SET_GOOD_BYE_CHAT(
            "set_goodbye_chat",
            "Agrega un canal de texto como chat de despedidas",
            DefaultMemberPermissions.DISABLED,
            new Option[]{
                    new Option("chat", "Chat de texto", OptionType.CHANNEL, true, false)
            }
    ),
    SET_ROLE_REACTION(
            "set_role_reaction",
            "Agrega un mensaje en el que se tiene que reaccionar con un rol para añadirselo",
            DefaultMemberPermissions.DISABLED,
            new Option[] {
                    new Option("role", "Nombre del rol que se agregara al usuario al poner el rol", OptionType.ROLE, true, false),
                    new Option("emoji", "Emoji con el que hay que reaccionar", OptionType.STRING, true, false),
                    new Option("chat", "Nombre del chat donde mandar el mensaje", OptionType.CHANNEL, false, false)
            }
    ),

    // SCORE
    TIER_LIST("tier_list", "Top de los 5 mejores (tanto de chat como de voz)", DefaultMemberPermissions.ENABLED, null),
    SPECIFIC_SCORE("score", "Puntuación personal (tanto de chat como de voz)", DefaultMemberPermissions.ENABLED, new Option[]{
            new Option("user", "Nombre de usuario del que obtener la puntuacion", OptionType.USER, false, false)
    }),

    // MUSIC
    PLAY("play", "Reproduce una cancion", DefaultMemberPermissions.DISABLED, new Option[]{
        new Option("song", "Nombre de la cancion a reproducir", OptionType.STRING, true, false)
    }),
    ;

    // Atributos y constructor
    private String name;
    private String description;
    private DefaultMemberPermissions defaultMemberPermissions;
    private Option[] options;

    CommandConstants(String name, String description, DefaultMemberPermissions defaultPermission, Option[] options){
        this.name = name;
        this.description = description;
        this.defaultMemberPermissions = defaultPermission;
        this.options = options;
    }

    @Getter
    public static class Option {
        private final String name;
        private final String description;
        private final OptionType type;
        private final boolean required;
        private final boolean autocomplete;


        public Option(String name, String description, OptionType type, boolean required, boolean autocomplete) {
            this.name = name;
            this.description = description;
            this.type = type;
            this.required = required;
            this.autocomplete = autocomplete;
        }
    }
}