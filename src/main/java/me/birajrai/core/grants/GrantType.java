package me.birajrai.core.grants;

import me.birajrai.core.utils.Utils;

public enum GrantType {

    RANK,
    PERMISSION;

    public static String convertToString(GrantType type) {
        return Utils.capitalizeFirst(type.toString().toLowerCase());
    }
}
