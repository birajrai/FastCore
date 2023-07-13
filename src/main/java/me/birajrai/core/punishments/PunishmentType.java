package me.birajrai.core.punishments;

import me.birajrai.core.utils.Utils;

public enum PunishmentType {

    WARN,
    MUTE,
    KICK,
    BAN,
    BLACKLIST;

    public static String convertToString(PunishmentType type) {
        return Utils.capitalizeFirst(type.toString().toLowerCase());
    }
}
