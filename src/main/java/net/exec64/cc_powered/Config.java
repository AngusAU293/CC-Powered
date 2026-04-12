package net.exec64.cc_powered;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> basic_computer_consumption;
    public static final ForgeConfigSpec.ConfigValue<Integer> basic_computer_capacity;
    public static final ForgeConfigSpec.ConfigValue<Integer> basic_computer_max_receive;

    public static final ForgeConfigSpec.ConfigValue<Integer> advanced_computer_consumption;
    public static final ForgeConfigSpec.ConfigValue<Integer> advanced_computer_capacity;
    public static final ForgeConfigSpec.ConfigValue<Integer> advanced_computer_max_receive;

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

        SPEC = BUILDER.build();
    }
}
