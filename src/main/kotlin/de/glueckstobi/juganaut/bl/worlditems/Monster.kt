package de.glueckstobi.juganaut.bl.worlditems

import de.glueckstobi.juganaut.bl.space.Direction


/**
 * Ein Monster
 * @param direction die Richtung, in die sich das Monster gerade bewegt
 */
class Monster() : WorldItem {
    var direction: Direction = Direction.random()
    var sleeping = true

}