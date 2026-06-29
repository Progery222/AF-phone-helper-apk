package com.af.helper;

public final class ApiCatalog {
    private ApiCatalog() {
    }

    public static final class ApiRoute {
        public final String visibility;
        public final String transport;
        public final String method;
        public final String path;
        public final String name;
        public final String handler;
        public final String description;

        public ApiRoute(
                String visibility,
                String transport,
                String method,
                String path,
                String name,
                String handler,
                String description
        ) {
            this.visibility = visibility;
            this.transport = transport;
            this.method = method;
            this.path = path;
            this.name = name;
            this.handler = handler;
            this.description = description;
        }
    }

    public static final ApiRoute[] PUBLIC_ROUTES = new ApiRoute[]{
            new ApiRoute(
                    "public",
                    "android-broadcast",
                    "SEND",
                    "com.af.helper.SET_LOCALE",
                    "helper.set_locale",
                    "LocaleReceiver.onReceive",
                    "Apply system locale from the locale intent extra."
            )
    };

    public static final ApiRoute[] PRIVATE_ROUTES = new ApiRoute[]{
            new ApiRoute(
                    "private",
                    "android-system",
                    "CALL",
                    "ActivityManager.updatePersistentConfiguration",
                    "system.update_locale",
                    "LocaleReceiver.setSystemLocale",
                    "Hidden Android API used to persist locale configuration."
            )
    };
}
