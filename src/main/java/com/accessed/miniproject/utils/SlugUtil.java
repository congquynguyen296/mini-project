package com.accessed.miniproject.utils;

import java.text.Normalizer;
import java.util.Locale;

public class SlugUtil {

    public static String toSlug(String input) {

        if (input == null) return "";

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String slug = normalized.replaceAll("\\p{M}", "");

        slug = slug.replaceAll("đ", "d").replaceAll("Đ", "D");
        slug = slug.toLowerCase(Locale.ROOT);
        slug = slug.replaceAll("[^a-z0-9]+", "-");
        slug = slug.replaceAll("^-+|-+$", "");

        return slug;
    }
}
