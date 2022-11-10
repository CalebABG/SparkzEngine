package com.cabg.utilities;

public class EnumUtil {
    public static <T extends Enum<T>> T transition(T enumType, boolean advance) {
        T[] values = enumType.getDeclaringClass().getEnumConstants();
        if (advance) return values[(enumType.ordinal() + 1) % values.length];
        else return values[(values.length + (enumType.ordinal() - 1)) % values.length];
    }
}
