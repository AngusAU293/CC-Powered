package net.exec64.cc_powered.energy;

import net.exec64.cc_powered.Config;

public enum EnergyProfile {
    BASIC_COMPUTER (
            Config.basic_computer_consumption.get(),
            Config.basic_computer_capacity.get(),
            Config.basic_computer_max_receive.get()
    ),
    ADVANCED_COMPUTER (
            Config.advanced_computer_consumption.get(),
            Config.advanced_computer_capacity.get(),
            Config.advanced_computer_max_receive.get()
    ),
    COMMAND_COMPUTER (
            0,
            0,
            0
    ),


    TURTLE (
            0,
            0,
            0
    );

    public final int consumption;
    public final int capacity;
    public final int maxReceive;

    EnergyProfile(int consumption, int capacity, int maxReceive) {
        this.consumption = consumption;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
    }
}
