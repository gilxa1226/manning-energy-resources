package model;

public class BatteryEvent {
    private String device_id;
    private int processor1_temp;
    private int processor2_temp;
    private int processor3_temp;
    private int processor4_temp;
    private String charging_source;
    private int charging;
    private int current_capacity;
    private int inverter_state;
    private int moduleL_temp;
    private int moduleR_temp;
    private float SoC_regulator;

    /**
     *  Class for the BatteryEvent AVRO Schema
     *     {"name": "device_id", "type": "string"},
     *     {"name": "processor1_temp", "type": "int"},
     *     {"name": "processor2_temp", "type": "int"},
     *     {"name": "processor3_temp", "type": "int"},
     *     {"name": "processor4_temp", "type": "int"},
     *     {"name": "charging_source", "type": "string"},
     *     {"name": "charging", "type": "int"},
     *     {"name": "current_capacity", "type": "int"},
     *     {"name": "inverter_state", "type": "int"},
     *     {"name": "moduleL_temp", "type": "int"},
     *     {"name": "moduleR_temp", "type": "int"},
     *     {"name": "SoC_regulator", "type": "double"}
     */
    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getProcessor1_temp() {
        return processor1_temp;
    }

    public void setProcessor1_temp(int processor1_temp) {
        this.processor1_temp = processor1_temp;
    }

    public int getProcessor2_temp() {
        return processor2_temp;
    }

    public void setProcessor2_temp(int processor2_temp) {
        this.processor2_temp = processor2_temp;
    }

    public int getProcessor3_temp() {
        return processor3_temp;
    }

    public void setProcessor3_temp(int processor3_temp) {
        this.processor3_temp = processor3_temp;
    }

    public int getProcessor4_temp() {
        return processor4_temp;
    }

    public void setProcessor4_temp(int processor4_temp) {
        this.processor4_temp = processor4_temp;
    }

    public String getCharging_source() {
        return charging_source;
    }

    public void setCharging_source(String charging_source) {
        this.charging_source = charging_source;
    }

    public int getCharging() {
        return charging;
    }

    public void setCharging(int charging) {
        this.charging = charging;
    }

    public int getCurrent_capacity() {
        return current_capacity;
    }

    public void setCurrent_capacity(int current_capacity) {
        this.current_capacity = current_capacity;
    }

    public int getInverter_state() {
        return inverter_state;
    }

    public void setInverter_state(int inverter_state) {
        this.inverter_state = inverter_state;
    }

    public int getModuleL_temp() {
        return moduleL_temp;
    }

    public void setModuleL_temp(int moduleL_temp) {
        this.moduleL_temp = moduleL_temp;
    }

    public int getModuleR_temp() {
        return moduleR_temp;
    }

    public void setModuleR_temp(int moduleR_temp) {
        this.moduleR_temp = moduleR_temp;
    }

    public float getSoC_regulator() {
        return SoC_regulator;
    }

    public void setSoC_regulator(float SoC_regulator) {
        SoC_regulator = SoC_regulator;
    }
}
