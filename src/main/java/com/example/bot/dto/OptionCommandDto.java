package com.example.bot.dto;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@Getter
public class OptionCommandDto {
    private final String name;
    private final String description;
    private final OptionType type;
    private final boolean required;
    private final boolean autocomplete;


    public OptionCommandDto(String name, String description, OptionType type, boolean required, boolean autocomplete) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.required = required;
        this.autocomplete = autocomplete;
    }
}
