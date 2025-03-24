package com.cr.articlesjava.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import androidx.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

@Singleton
public class InternetConnectivityObserver {

    private final ConnectivityManager connectivityManager;

    @Inject
    public InternetConnectivityObserver(Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * Observes network connectivity status and emits updates accordingly
     */
    public Observable<Boolean> isConnected() {
        return Observable.create(emitter -> {
            ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities);
                    boolean connected = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                    emitter.onNext(connected);
                }

                @Override
                public void onUnavailable() {
                    super.onUnavailable();
                    emitter.onNext(false);
                }

                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    emitter.onNext(false);
                }

                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    emitter.onNext(true);
                }
            };

            connectivityManager.registerDefaultNetworkCallback(callback);

            emitter.setCancellable(() -> connectivityManager.unregisterNetworkCallback(callback));
        });
    }

}
