package fr.d0gma.core.team;

import fr.d0gma.core.player.CorePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.scoreboard.Team;

import java.util.Optional;
import java.util.Set;

public interface ScoreboardTeam<T extends CorePlayer<T>> {

    /**
     * Gets the name of this team.
     *
     * @return the name of the team
     */
    String getName();

    /**
     * Gets the prefix prepended to the display of entries on this team.
     *
     * @return the team's current prefix
     */
    Component getPrefix();

    /**
     * Sets the prefix prepended to the display of entries on this team.
     *
     * @param prefix the new prefix for this team
     *               characters
     */
    void setPrefix(Component prefix);

    /**
     * Gets the suffix appended to the display of entries on this team.
     *
     * @return the team's current suffix
     */
    Component getSuffix();

    /**
     * Sets the suffix appended to the display of entries on this team.
     *
     * @param suffix the new suffix for this team.
     */
    void setSuffix(Component suffix);

    /**
     * Gets the color of the team.
     *
     * @return team color, or empty if none
     */
    Optional<TextColor> getColor();

    /**
     * Sets the color of the team.
     *
     * @param color new color, or {@code null} for no color
     */
    void setColor(NamedTextColor color);

    /**
     * Gets the Set of players on the team
     *
     * @return players on the team
     */
    Set<T> getPlayers();

    /**
     * Gets the size of the team
     *
     * @return number of players on the team
     */
    int getSize();

    /**
     * Puts the specified player onto this team for the scoreboard.
     * This will remove the player from any other team on the scoreboard.
     *
     * @param player the player to add
     */
    void addPlayer(T player);

    /**
     * Removes the player from this team.
     *
     * @param player the player to remove
     * @return if the player was on this team
     */
    boolean removePlayer(T player);

    /**
     * Unregisters this team
     */
    void unregister();

    /**
     * Checks to see if the specified player is a member of this team.
     *
     * @param player the player to search for
     * @return true if the player is a member of this team
     */
    boolean hasPlayer(T player);

    /**
     * Gets an option for this team.
     *
     * @param option the option to get
     * @return the option status
     */
    Team.OptionStatus getOption(Team.Option option);

    /**
     * Sets an option for this team.
     *
     * @param option the option to set
     * @param status the new option status
     */
    void setOption(Team.Option option, Team.OptionStatus status);

}
