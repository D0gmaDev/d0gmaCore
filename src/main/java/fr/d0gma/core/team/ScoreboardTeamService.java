package fr.d0gma.core.team;

import fr.d0gma.core.player.CorePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ScoreboardTeamService {

    private static Scoreboard scoreboard;

    private static final Map<String, ScoreboardTeamImpl<?>> scoreboardTeams = new HashMap<>();

    public static void initialize(JavaPlugin plugin) {
        scoreboard = Objects.requireNonNull(plugin.getServer().getScoreboardManager()).getNewScoreboard();
    }

    @SuppressWarnings("unchecked")
    public static <T extends CorePlayer<T>> Optional<ScoreboardTeam<T>> getScoreboardTeam(Class<T> playerClass, String name) {
        return Optional.ofNullable(scoreboardTeams.get(name))
                .filter(scoreboardTeam -> scoreboardTeam.getPlayerClass().isAssignableFrom(playerClass))
                .map(scoreboardTeam -> (ScoreboardTeam<T>) scoreboardTeam);
    }

    public static Optional<ScoreboardTeam<?>> getScoreboardTeam(String name) {
        return Optional.ofNullable(scoreboardTeams.get(name));
    }

    public static <T extends CorePlayer<T>> ScoreboardTeam<T> registerScoreboardTeam(Class<T> playerClass, String name, Component prefix) {
        ScoreboardTeamImpl<T> scoreboardTeam = new ScoreboardTeamImpl<T>(playerClass, scoreboard.registerNewTeam(name));
        scoreboardTeam.setPrefix(prefix);
        scoreboardTeams.put(name, scoreboardTeam);
        return scoreboardTeam;
    }


    public static <T extends CorePlayer<T>> void deleteScoreboardTeam(ScoreboardTeam<T> scoreboardTeam) {
        List.copyOf(scoreboardTeam.getPlayers()).forEach(scoreboardTeam::removePlayer);

        scoreboardTeams.remove(scoreboardTeam.getName());
        scoreboardTeam.unregister();
    }

    public static void onPlayerJoin(Player player) {
        player.setScoreboard(scoreboard);
    }
}
