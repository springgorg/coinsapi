package fr.springg.coinsapi;

import fr.springg.coinsapi.commands.Commands;
import fr.springg.coinsapi.listeners.PlayerListener;
import fr.springg.coinsapi.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    private static Main instance;

    public MySQL sql = new MySQL();
    public FileConfiguration dbconfig = YamlConfiguration.loadConfiguration(getFile("database"));
    public FileConfiguration config = YamlConfiguration.loadConfiguration(getFile("config"));

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getConsoleSender().sendMessage("§c=====================");
        Bukkit.getConsoleSender().sendMessage("§eCoinsAPI");
        Bukkit.getConsoleSender().sendMessage("§aDev par §2Spring_g");
        Bukkit.getConsoleSender().sendMessage("§c=====================");

        createFile("database");
        createFile("config");

        // Configuration
        config.set("coins.start", 0);
        try{config.save(getFile("config"));}catch(IOException e){e.printStackTrace();}

        // Database
        dbconfig.set("database.host", "localhost");
        dbconfig.set("database.port", 3306);
        dbconfig.set("database.user", "root");
        dbconfig.set("database.pass", "root");
        dbconfig.set("database.dbname", "test");
        try {dbconfig.save(getFile("database"));}catch(IOException e){e.printStackTrace();}

        setupSQL();

        registerEvents();
        registerCommands();

        super.onEnable();
    }

    private void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListener(), this);
    }

    private void registerCommands(){
        getCommand("money").setExecutor(new Commands());
    }

    private void setupSQL(){
        sql.connect(dbconfig.getString("database.host"), dbconfig.getString("database.dbname"), dbconfig.getInt("database.port"), dbconfig.getString("database.user"), dbconfig.getString("database.pass"));
        sql.query("CREATE TABLE IF NOT EXISTS coins(" +
                "player_name VARCHAR(255)," +
                "coins BIGINT" +
                ")");
    }

    @Override
    public void onDisable() {sql.disconnect();}

    public void createFile(String fileName){
        if(!getDataFolder().exists()) getDataFolder().mkdir();

        File file = new File(getDataFolder(), fileName + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public File getFile(String fileName){
        return new File(getDataFolder(), fileName + ".yml");
    }

    public static Main getInstance(){
        return instance;
    }

}
