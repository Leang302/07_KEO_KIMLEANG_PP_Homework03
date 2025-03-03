package Model;

import Utils.Helper;

public class SalariedEmployee extends StaffMember {
    private double salary;
    private double bonus;

    public SalariedEmployee() {
    }

    public SalariedEmployee(String name, String address, double salary, double bonus) {
        super(name, address);
        this.salary = salary;
        this.bonus = bonus;
    }

    @Override
    public double pay() {
        return salary + bonus;
    }

    @Override
    public StaffMember addStaff() {
        super.addStaff();
        salary = Double.parseDouble(Helper.getUserInput("Enter salary: ", "Invalid salary input", "^(0|[1-9]\\d*)(\\.\\d+)?$"));
        bonus = Double.parseDouble(Helper.getUserInput("Enter bonus: ", "Invalid bonus input", "^(0|[1-9]\\d*)(\\.\\d+)?$"));
        return this;
    }

    @Override
    public void showUpdateMenu() {
        Helper.printSingleEmployee(this);
        super.showUpdateMenu();
        System.out.print("3. Salary\t4. Bonus\t0. Cancel\n");
    }

    @Override
    protected void handleSubclassUpdate(int option) {
        switch (option) {
            case 3:
                salary = Double.parseDouble(Helper.getUserInput("➡️Update Salary To:", "Invalid input", "^(0|[1-9]\\d*)(\\.\\d+)?$"));
                break;
            case 4:
                bonus = Double.parseDouble(Helper.getUserInput("➡️Update Bonus To:", "Invalid input", "^(0|[1-9]\\d*)(\\.\\d+)?$"));
                break;
        }
    }

    @Override
    protected String subClassValidRegex() {
        return "[0-4]";
    }

    @Override
    public String toString() {
        return String.format("Salary Employee\n%s\nSalary: %n\nBonus: %n \nPayment : %n", super.toString(), salary, bonus, bonus);
    }

    public double getSalary() {
        return salary;
    }

    public double getBonus() {
        return bonus;
    }
}
