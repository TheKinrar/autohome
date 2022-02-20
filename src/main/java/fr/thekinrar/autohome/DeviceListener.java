package fr.thekinrar.autohome;

import fr.thekinrar.autohome.devices.Button;

public interface DeviceListener<D extends Device, A extends Enum<A>> {
    Class<D> getDeviceClass();

    void onAction(D device, A action);
}
