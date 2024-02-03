package com.s151044.discord;

import com.google.gson.Gson;
import com.s151044.discord.commands.CommandList;
import com.s151044.discord.commands.interactions.SlashCommandList;
import com.s151044.discord.handlers.MessageHandler;
import com.s151044.discord.handlers.interactions.ButtonHandler;
import com.s151044.discord.handlers.interactions.SlashHandler;
import com.s151044.discord.utils.TimeRecord;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;
import java.util.List;

public class Main {
    private static JDA jda;
    private static Gson gson;
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> arguments = List.of(args);
        gson = new Gson();


        CommandList commandList = new CommandList();
        SlashCommandList slashList = new SlashCommandList();
        ButtonHandler buttonHandler = new ButtonHandler();

        jda = JDABuilder.createDefault(System.getenv("BOT_TOKEN"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(
                        new MessageHandler("&", commandList),
                        new SlashHandler(slashList),
                        buttonHandler)
                .build();
        jda.awaitReady();
    }

    public static JDA getJda() {
        return jda;
    }

    private static TimeRecord getTime(String timeString) {
        String[] arr = timeString.split(" ");
        if (arr.length > 4) { // Exceeds normal cases like TuTh 03:00PM - 04:20PM
            StringBuilder sb = new StringBuilder();
            for (int i = 3; i < arr.length; i++) {
                sb.append(arr[i]);
                sb.append(" ");
            }
            return new TimeRecord(arr[0], arr[2], sb.toString().trim());
        }
        return new TimeRecord(timeString);
    }
}
