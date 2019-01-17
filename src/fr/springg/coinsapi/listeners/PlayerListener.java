package fr.springg.coinsapi.listeners;

import fr.springg.coinsapi.Main;
import fr.springg.coinsapi.coins.Coins;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        new Coins().createAccount(e.getPlayer(), Main.getInstance().config.getLong("coins.start"));
        e.getPlayer().sendMessage("§aVous avez §e" + new Coins().getCoins(e.getPlayer()) + "€§r");
    }

}
