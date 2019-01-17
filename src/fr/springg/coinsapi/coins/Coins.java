package fr.springg.coinsapi.coins;

import fr.springg.coinsapi.Main;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class Coins {

    public void createAccount(Player player, long coins){
        try {
            PreparedStatement sts = Main.getInstance().sql.getConnection().prepareStatement("SELECT `coins` FROM `coins` WHERE `player_name`=?");
            sts.setString(1, player.getName());
            ResultSet rs = sts.executeQuery();

            if(!rs.next()){
                sts.close();
                sts = Main.getInstance().sql.getConnection().prepareStatement("INSERT INTO `coins` (player_name, coins) VALUES (?, ?)");
                sts.setString(1, player.getName());
                sts.setLong(2, coins);
                sts.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getCoins(Player player){
        try {
            PreparedStatement sts = Main.getInstance().sql.getConnection().prepareStatement("SELECT `coins` FROM `coins` WHERE `player_name`=?");
            sts.setString(1, player.getName());
            ResultSet rs = sts.executeQuery();

            if(rs.next()){
                return rs.getLong("coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addCoins(Player player, long coins){
        if(coins < 1)
            player.sendMessage("§cVous ne pouvez pas envoyer ce montant !");
        try {
            PreparedStatement sts = Main.getInstance().sql.getConnection().prepareStatement("SELECT `coins` FROM `coins` WHERE `player_name`=?");
            sts.setString(1, player.getName());
            ResultSet rs = sts.executeQuery();

            if(rs.next()){
                long money = rs.getLong("coins");
                sts.close();
                sts = Main.getInstance().sql.getConnection().prepareStatement("UPDATE `coins` SET `coins`=? WHERE `player_name`=?");
                sts.setLong(1, (coins + money));
                sts.setString(2, player.getName());
                sts.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCoins(Player player, long coins){
        if(coins < 1) return;
        try {
            PreparedStatement sts = Main.getInstance().sql.getConnection().prepareStatement("SELECT `coins` FROM `coins` WHERE `player_name`=?");
            sts.setString(1, player.getName());
            ResultSet rs = sts.executeQuery();

            if(rs.next()){
                long money = rs.getLong("coins");
                sts.close();

                if((money - coins) < 0){
                    coins = money;
                }

                sts = Main.getInstance().sql.getConnection().prepareStatement("UPDATE `coins` SET `coins`=? WHERE `player_name`=?");
                sts.setLong(1, (money - coins));
                sts.setString(2, player.getName());
                sts.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}