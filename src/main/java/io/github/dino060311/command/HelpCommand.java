package io.github.dino060311.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage("");
        sender.sendMessage("§b§l[ SERVER COMMAND HELP ]");
        sender.sendMessage("§7--------------------------------");
        sender.sendMessage("§e/메뉴 §f: 직업 선택 GUI 메뉴");
        sender.sendMessage("§e/타이머 §f: 파밍 시간 보스바 타이머");
        sender.sendMessage("§e/npc소환 §f: 스토리 NPC를 소환");
        sender.sendMessage("§e/시한폭탄 §f: 투척형 시한폭탄");
        sender.sendMessage("§e/마법지팡이 §f: 화염 마법 지팡이");
        sender.sendMessage("§e/컷신 §f: 극적인 화면 고정 연출");
        sender.sendMessage("§e/랜덤직업 §f: 전용 GUI를 통해 직업을 무작위로 선택");
        sender.sendMessage("§e/무기대장장이 §f: 대장장이 상인 NPC 소환");
        sender.sendMessage("§7--------------------------------");

        return true;
    }
}