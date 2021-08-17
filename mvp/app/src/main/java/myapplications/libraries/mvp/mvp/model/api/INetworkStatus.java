package myapplications.libraries.mvp.mvp.model.api;

public interface INetworkStatus {

    enum Status {
        WIFI,
        MOBILE,
        ETHERNET,
        OTHER,
        OFFLINE,
    }

    Status getStatus();

    boolean isOnline();

    boolean isWifi();

    boolean isEthernet();

    boolean isMobile();

    boolean isOffline();
}
