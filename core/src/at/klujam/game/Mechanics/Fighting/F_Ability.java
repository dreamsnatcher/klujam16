package at.klujam.game.Mechanics.Fighting;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.FightWorld;

/**
 * Created by Veit on 15.04.2016.
 */
public abstract class F_Ability {
    public String name;
    public FightWorld fworld;
    public F_Entity origin;

    public F_Ability(String name,FightWorld fworld, F_Entity origin) {
        this.name = name;
        this.fworld = fworld;
        this.origin = origin;
    }

    public abstract void useOn(F_Entity target);
}
