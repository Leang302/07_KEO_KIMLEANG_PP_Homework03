package Model;

import Utils.Helper;

public class HourSalaryEmployee extends StaffMember {
    private double workHour;
    private double rate;

    public HourSalaryEmployee() {
    }

    public HourSalaryEmployee(String name, String address, double workHour, double rate) {
        super(name, address);
        this.workHour = workHour;
        this.rate = rate;
    }

    @Override
    public double pay() {
        return workHour * rate;
    }

    @Override
    public StaffMember addStaff() {
        super.addStaff();
        workHour = Double.parseDouble(Helper.getUserInput("Enter work hour: ", "Invalid hour input", "^(0|[1-9]\\d*)(\\.\\d+)?$"));
        rate = Double.parseDouble(Helper.getUserInput("Enter rate: ", "Invalid rate input", "^(0|[1-9]\\d*)(\\.\\d+)?$"));
        return this;
    }

    @Override
    public void showUpdateMenu() {
        Helper.printSingleEmployee(this);
        super.showUpdateMenu();
        System.out.print("3. Hour\t4. Rate\t0. Cancel\n");
    }

    @Override
    protected void handleSubclassUpdate(int option) {
        switch (option) {
            case 3:
                workHour = Double.parseDouble(Helper.getUserInput("➡️Update Work Hour To:", "Invalid input", "^(0|[1-9]\\d*)(\\.\\d+)?$"));
                break;
            case 4:
                rate = Double.parseDouble(Helper.getUserInput("➡️Update Rate To:", "Invalid input", "^(0|[1-9]\\d*)(\\.\\d+)?$"));
                break;
        }
    }

    @Override
    protected String subClassValidRegex() {
        return "[0-4]";
    }

    @Override
    public String toString() {
        return String.format("Hour Employee\n%s\nHoursWorked: %n\nRate: %n \nPayment : %n", super.toString(), workHour + "", rate + "", rate + "");
    }

    public double getWorkHour() {
        return workHour;
    }

    public double getRate() {
        return rate;
    }
}
