package com.sagarvarule.parallel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceConfiguration {
    private String deviceName;
    private String osVersion;
    private String platformName;
    private String browserstackDevice;
    
    public static DeviceConfiguration[] getParallelDevices() {
        return new DeviceConfiguration[] {
            new DeviceConfiguration("Samsung Galaxy S22", "12.0", "Android", "Samsung Galaxy S22"),
            new DeviceConfiguration("Google Pixel 6", "12.0", "Android", "Google Pixel 6"),
            new DeviceConfiguration("OnePlus 9", "11.0", "Android", "OnePlus 9")
        };
    }
    
    public static DeviceConfiguration getDeviceForThread() {
        DeviceConfiguration[] devices = getParallelDevices();
        String threadName = Thread.currentThread().getName();
        int threadHash = Math.abs(threadName.hashCode());
        int deviceIndex = threadHash % devices.length;
        return devices[deviceIndex];
    }
}
