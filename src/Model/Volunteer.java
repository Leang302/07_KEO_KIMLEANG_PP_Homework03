package Model;

import Utils.Helper;

public class Volunteer extends StaffMember {
    private double salary;

    public Volunteer() {
    }

    public Volunteer(String name, String address, double salary) {
        super(name, address);
        this.salary = salary;
    }

    @Override
    public double pay() {
        return salary;
    }

    @Override
    public StaffMember addStaff() {
        super.addStaff();
        salary = Double.parseDouble(Helper.getUserInput("Enter salary: ", "Invalid salary input", "^(0|[1-9]\\d*)(\\.\\d+)?$"));
        return this;
    }

    @Override
    public void showUpdateMenu() {
        Helper.printSingleEmployee(this);
        super.showUpdateMenu();
        System.out.print("3. Salary\t0. Cancel\n");
    }

    @Override
    protected void handleSubclassUpdate(int option) {
        switch (option) {
            case 3:
                salary = Double.parseDouble(Helper.getUserInput("➡️Change Salary To:", "Invalid input", "^(0|[1-9]\\d*)(\\.\\d+)?$"));
                break;
        }

    }

    @Override
    protected String subClassValidRegex() {
        return "[0-3]";
    }

    @Override
    public String toString() {
        return String.format("Volunteer\n%s\nSalary: %n", super.toString(), salary);
    }

    public double getSalary() {
        return salary;
    }
}
