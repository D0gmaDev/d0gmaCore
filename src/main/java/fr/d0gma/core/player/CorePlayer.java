package fr.d0gma.core.player;

import fr.d0gma.core.team.ScoreboardTeam;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface CorePlayer<T extends CorePlayer<T>> {

    Player getPlayer();

    UUID getUniqueId();

    String getPlayerName();

    Optional<ScoreboardTeam<T>> getScoreboardTeam();

    void setScoreboardTeam(ScoreboardTeam<T> scoreboardTeam);
}
