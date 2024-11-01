package de.glueckstobi.juganaut.bl.worlditems


/**
 * Ein Monster
 * @param direction die Richtung, in die sich das Monster gerade bewegt
 */
class Bomb() : WorldItem {
    var active = false
    var countdown = 3
    var falling = false

}