package com.example.bot.dto;

public class CommandDto {
    // Atributos y constructor
    private String name;
    private String description;
    private OptionCommandDto[] options;

    public CommandDto(String name, String description, OptionCommandDto[] options){
        this.name = name;
        this.description = description;
        this.options = options;
    }
}
