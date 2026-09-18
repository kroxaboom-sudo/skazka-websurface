package com.kroxaboom.skazka.websurface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * RU: Единая поверхность для пользовательских HTTPS-переходов через Android Custom Tabs.
 * EN: Shared surface for user-facing HTTPS navigation through Android Custom Tabs.
 */
public final class SkazkaWebSurface {
    private static final String CUSTOM_TABS_SERVICE = "android.support.customtabs.action.CustomTabsService";
    private static final String CUSTOM_TABS_SESSION = "android.support.customtabs.extra.SESSION";
    private static final String CUSTOM_TABS_TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";
    private static final String CUSTOM_TABS_NAVIGATION_BAR_COLOR =
            "android.support.customtabs.extra.NAVIGATION_BAR_COLOR";
    private static final String CUSTOM_TABS_SHARE_MENU_ITEM = "android.support.customtabs.extra.SHARE_MENU_ITEM";
    private static final String CUSTOM_TABS_ENABLE_INSTANT_APPS =
            "android.support.customtabs.extra.EXTRA_ENABLE_INSTANT_APPS";

    private SkazkaWebSurface() {}

    public static void openHttps(Context context, String url) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }

        openHttps(context, Uri.parse(url == null ? "" : url.trim()));
    }

    public static void openHttps(Context context, Uri target) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        if (target == null || !"https".equalsIgnoreCase(target.getScheme()) || target.getHost() == null) {
            throw new IllegalArgumentException("Web target must use HTTPS");
        }

        String packageName = findCustomTabsPackage(context);
        if (packageName.isEmpty()) {
            throw new IllegalStateException("No Custom Tabs provider available");
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, target);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setPackage(packageName);

        Bundle customTabs = new Bundle();
        customTabs.putBinder(CUSTOM_TABS_SESSION, null);
        intent.putExtras(customTabs);
        intent.putExtra(CUSTOM_TABS_TOOLBAR_COLOR, 0xff151b29);
        intent.putExtra(CUSTOM_TABS_NAVIGATION_BAR_COLOR, 0xff0f141e);
        intent.putExtra(CUSTOM_TABS_SHARE_MENU_ITEM, false);
        intent.putExtra(CUSTOM_TABS_ENABLE_INSTANT_APPS, false);

        /*
         * RU:
         * Библиотека принимает Context, чтобы не привязываться к Activity конкретного приложения.
         * Для обычной Activity флаг не нужен; для service/application Context Android требует NEW_TASK.
         *
         * EN:
         * The library accepts Context so it is not coupled to an app-specific Activity.
         * A normal Activity needs no extra flag; service/application contexts require NEW_TASK.
         */
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    public static boolean isAvailable(Context context) {
        return context != null && !findCustomTabsPackage(context).isEmpty();
    }

    static String findCustomTabsPackage(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> services = packageManager.queryIntentServices(
                new Intent(CUSTOM_TABS_SERVICE),
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL)
        );

        if (services == null || services.isEmpty()) {
            return "";
        }

        Set<String> supportedPackages = new LinkedHashSet<>();
        for (ResolveInfo info : services) {
            if (info == null || info.serviceInfo == null || info.serviceInfo.packageName == null) {
                continue;
            }
            supportedPackages.add(info.serviceInfo.packageName);
        }

        if (supportedPackages.isEmpty()) {
            return "";
        }

        /*
         * RU:
         * Сначала используем браузер по умолчанию, если он действительно поддерживает Custom Tabs.
         * Иначе берём первый найденный provider. В отдельный браузер без Custom Tabs не откатываемся.
         *
         * EN:
         * Prefer the default browser when it actually supports Custom Tabs.
         * Otherwise use the first available provider. Never silently fall back to a non-Custom-Tab browser.
         */
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com/"));
        ResolveInfo preferred = packageManager.resolveActivity(
                browserIntent,
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY)
        );

        if (preferred != null
                && preferred.activityInfo != null
                && supportedPackages.contains(preferred.activityInfo.packageName)) {
            return preferred.activityInfo.packageName;
        }

        return supportedPackages.iterator().next();
    }
}
