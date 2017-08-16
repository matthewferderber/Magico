/**
 * Created by Matt_ on 8/12/2017.
 */

package net.cobaltium.magico;

import net.cobaltium.magico.data.*;
import net.cobaltium.magico.listeners.MagicoListener;
import net.cobaltium.magico.spells.SpellList;
import org.spongepowered.api.Game;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.slf4j.Logger;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;

import javax.inject.Inject;

@Plugin(id = "magico", name = "Magico", version = "0.01")
public class Magico {

    @Inject
    private Logger logger;

    @Inject
    Game game;

    @Inject
    private PluginManager pluginManager;

    @Listener
    public void preInit(GamePreInitializationEvent e) {
        PluginContainer plugin = pluginManager.getPlugin("magico").get();
        DataRegistration.builder().dataClass(MagicoProjectileData.class)
                .immutableClass(ImmutableMagicoProjectileData.class)
                .builder(new MagicoProjectileBuilder())
                .manipulatorId("player-fireball")
                .dataName("Player Fireball")
                .buildAndRegister(plugin);
        DataRegistration.builder().dataClass(MagicoUserData.class)
                .immutableClass(ImmutableMagicoUserData.class)
                .builder(new MagicoUserBuilder())
                .manipulatorId("magico-user")
                .dataName("Magico User")
                .buildAndRegister(plugin);

        //Listeners
        this.game.getEventManager().registerListeners(this, new MagicoListener());
        SpellList.ALL.forEach((spellType) -> this.game.getEventManager().registerListeners(this, spellType.getListener()));
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("started");
    }

    @Listener
    public void onReload(GameReloadEvent event) {
        logger.info("reloaded");
    }

}
