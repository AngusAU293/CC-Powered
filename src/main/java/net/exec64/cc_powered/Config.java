package net.exec64.cc_powered;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // BASIC COMPUTER
    public static final ForgeConfigSpec.ConfigValue<Integer> basic_computer_consumption;
    public static final ForgeConfigSpec.ConfigValue<Integer> basic_computer_capacity;
    public static final ForgeConfigSpec.ConfigValue<Integer> basic_computer_max_receive;

    // ADVANCED COMPUTER
    public static final ForgeConfigSpec.ConfigValue<Integer> advanced_computer_consumption;
    public static final ForgeConfigSpec.ConfigValue<Integer> advanced_computer_capacity;
    public static final ForgeConfigSpec.ConfigValue<Integer> advanced_computer_max_receive;

    // BASIC POCKET COMPUTER
    public static final ForgeConfigSpec.ConfigValue<Integer> basic_pocket_computer_consumption;
    public static final ForgeConfigSpec.ConfigValue<Integer> basic_pocket_computer_capacity;
    public static final ForgeConfigSpec.ConfigValue<Integer> basic_pocket_computer_max_receive;

    // ADVANCED POCKET COMPUTER
    public static final ForgeConfigSpec.ConfigValue<Integer> advanced_pocket_computer_consumption;
    public static final ForgeConfigSpec.ConfigValue<Integer> advanced_pocket_computer_capacity;
    public static final ForgeConfigSpec.ConfigValue<Integer> advanced_pocket_computer_max_receive;

    static {
        basic_computer_consumption = BUILDER.comment("The amount of energy a basic computer consumes in FE per tick.")
                .define("basic_computer_consumption", 100);
        basic_computer_capacity = BUILDER.comment("The amount of energy a basic computer can hold in FE.")
                .define("basic_computer_capacity", 1000);
        basic_computer_max_receive = BUILDER.comment("The amount of energy a basic computer can receive in FE per tick.")
                .define("basic_computer_max_receive", 500);

        advanced_computer_consumption = BUILDER.comment("The amount of energy an advanced computer consumes in FE per tick.")
                .define("advanced_computer_consumption", 1000);
        advanced_computer_capacity = BUILDER.comment("The amount of energy an advanced computer can hold in FE.")
                .define("advanced_computer_capacity", 10000);
        advanced_computer_max_receive = BUILDER.comment("The amount of energy an advanced computer can receive in FE per tick.")
                .define("advanced_computer_max_receive", 5000);

        // Has 1 hour of battery life and takes 6 minutes to charge while off by default.
        basic_pocket_computer_consumption = BUILDER.comment("The amount of energy a basic pocket computer consumes in FE per tick.")
                .define("basic_pocket_computer_consumption", 1);
        basic_pocket_computer_capacity = BUILDER.comment("The amount of energy a basic pocket computer can hold in FE.")
                .define("basic_pocket_computer_capacity", 72000);
        basic_pocket_computer_max_receive = BUILDER.comment("The amount of energy a basic pocket computer can receive in FE per tick.")
                .define("basic_pocket_computer_max_receive", 10);

        // Has 2 hours of battery life and takes 1 minute and 12 seconds to charge while off by default.
        advanced_pocket_computer_consumption = BUILDER.comment("The amount of energy an advanced pocket computer consumes in FE per tick.")
                .define("advanced_pocket_computer_consumption", 10);
        advanced_pocket_computer_capacity = BUILDER.comment("The amount of energy an advanced pocket computer can hold in FE.")
                .define("advanced_pocket_computer_capacity", 1440000);
        advanced_pocket_computer_max_receive = BUILDER.comment("The amount of energy an advanced pocket computer can receive in FE per tick.")
                .define("advanced_pocket_computer_max_receive", 1000);

        SPEC = BUILDER.build();
    }
}
