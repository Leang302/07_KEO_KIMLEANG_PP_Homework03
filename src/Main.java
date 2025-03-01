import Model.HourSalaryEmployee;
import Model.SalariedEmployee;
import Model.StaffMember;
import Model.Volunteer;
import Utils.Helper;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        List<StaffMember> allStaff = new ArrayList<>(Arrays.asList(
                new HourSalaryEmployee("leang", "PP", 12, 10),
                new SalariedEmployee("Leang1", "PP", 12, 10),
                new HourSalaryEmployee("leang", "PP", 12, 10),
                new Volunteer("leang 3", "TK", 1000),
                new Volunteer("leang 3", "TK", 1000)
        ));
        Scanner sc = new Scanner(System.in);
        do {
            String userChoice;
            Helper.printMenu(1, Arrays.asList("Insert Employee", "Update Employee", "Display Employee", "Remove Employee", "Exit"));
            System.out.println("-".repeat(40));
            userChoice = Helper.getUserInput("➡️ Choose an option : ", "Input must be [1-5]", "[1-5]");
            switch (Integer.parseInt(userChoice)) {
                case 1:
                    insert:
                    do {
                        System.out.println("=".repeat(15) + "* Insert Employee *" + "=".repeat(15));
                        System.out.println("Choose type: ");
                        Helper.printMenu(4, Arrays.asList("Volunteer", "Salaries Employee", "Hourly Employee", "Back"));
                        userChoice = Helper.getUserInput("➡️ Enter Type Number : ", "Input must be [1-4]", "[1-4]");
                        String type = "";
                        StaffMember staffMember = null;
                        switch (Integer.parseInt(userChoice)) {
                            case 1:
                                staffMember = new Volunteer();
                                staffMember.addStaff();
                                allStaff.add(staffMember);
                                type = "Volunteer";
                                break;
                            case 2:
                                staffMember = new SalariedEmployee();
                                staffMember.addStaff();
                                allStaff.add(staffMember);
                                type = "Salaries Employee";
                                break;
                            case 3:
                                staffMember = new HourSalaryEmployee();
                                staffMember.addStaff();
                                allStaff.add(staffMember);
                                type = "Hour Salary Employee";
                                break;
                            case 4:
                                break insert;
                        }
                        Helper.printSuccessMsg("You added " + staffMember.getName() + " of type " + type + " successfully!");
                    } while (true);
                    break;
                case 2:
                    AtomicBoolean found = new AtomicBoolean(false);
                    System.out.println("=".repeat(15) + "* Update Employee *" + "=".repeat(15));
                    do {
                        int inputId;
                        inputId = Integer.parseInt(Helper.getUserInput("➡️ Enter ID to update : ", "Input must be [1-4]", "\\d"));
                        Optional<StaffMember> staff = allStaff.stream().filter(staffMember -> staffMember.getId() == inputId).findFirst();
                        staff.ifPresentOrElse(
                                staffMember -> {
                                    found.set(true);
                                    staffMember.showUpdateMenu();
                                    staffMember.updateStaff();
                                },
                                () -> {
                                    Helper.printErrorMsg("User not found");
                                }
                        );
                    } while (!found.get());
                    break;
                case 3:
                    Helper.printTable(allStaff);
                    sc.nextLine();
                    break;
                case 4:
                    AtomicBoolean deletedUserFound = new AtomicBoolean(false);
                    System.out.println("=".repeat(15) + "* Remove Employee *" + "=".repeat(15));
                    do {
                        int inputId;
                        inputId = Integer.parseInt(Helper.getUserInput("➡️ Enter ID to update : ", "Input must be [1-4]", "\\d"));
                        Optional<StaffMember> staff = allStaff.stream().filter(staffMember -> staffMember.getId() == inputId).findFirst();
                        staff.ifPresentOrElse(
                                staffMember -> {
                                    deletedUserFound.set(true);
                                    allStaff.remove(staff.get());
                                    Helper.printSuccessMsg("User with id " + inputId + " have been removed!");
                                },
                                () -> {
                                    Helper.printErrorMsg("User not found");
                                }
                        );
                    } while (!deletedUserFound.get());

                    break;
                case 5:
                    System.out.println("Thank you 🙏 Good bye 😁");
                    return;
            }
        } while (true);
    }
}