import Model.HourSalaryEmployee;
import Model.SalariedEmployee;
import Model.StaffMember;
import Model.Volunteer;
import Utils.Helper;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        List<StaffMember> allStaff = new ArrayList<>(Arrays.asList(new Volunteer("Jack", "Kandal", 800), new SalariedEmployee("Oggy", "PP", 12, 10), new HourSalaryEmployee("Bob", "PP", 12, 10), new HourSalaryEmployee("Olivia", "PP", 12, 10), new Volunteer("Johny", "TK", 1000), new SalariedEmployee("Jennie", "Siem Reap", 20, 10), new Volunteer("Norman", "LA", 800)));
        int size = 3;
        int pageNum = 1;
        Scanner sc = new Scanner(System.in);
        do {
            String userChoice;
            Helper.printMenu(1, Arrays.asList("Insert Employee", "Update Employee", "Display Employee", "Remove Employee", "Exit"));
            System.out.println("-".repeat(40));
            userChoice = Helper.getUserInput("➡️ Choose an option : ", "Input must be [1-5]", "[1-5]");
            switch (Integer.parseInt(userChoice)) {
                //insert employee
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
                //update employee
                case 2:
                    if (!allStaff.isEmpty()) {
                        AtomicBoolean found = new AtomicBoolean(false);
                        System.out.println("=".repeat(15) + "* Update Employee *" + "=".repeat(15));
                        do {
                            int inputId;
                            inputId = Integer.parseInt(Helper.getUserInput("➡️ Enter ID to update : ", "Invalid id format", "\\d+"));
                            Optional<StaffMember> staff = allStaff.stream().filter(staffMember -> staffMember.getId() == inputId).findFirst();
                            staff.ifPresentOrElse(staffMember -> {
                                found.set(true);
                                staffMember.showUpdateMenu();
                                staffMember.updateStaff();
                            }, () -> {
                                Helper.printErrorMsg("User not found");
                            });
                        } while (!found.get());
                    } else {
                        Helper.printErrorMsg("There's no staff");
                        System.out.println("Press any key to go back...");
                        sc.nextLine();
                    }
                    break;
                //display employee
                case 3:
                    int totalPage = (allStaff.size() + size - 1) / size;
                    display:
                    do {
                        Helper.printTable(allStaff, allStaff.isEmpty() ?0:pageNum, allStaff.isEmpty() ?0:size);
                        System.out.printf("\n\nPage: %s/%s\n", allStaff.isEmpty() ?0:pageNum, totalPage);
                        System.out.println("1. First page\t2. Next page\t3. Previous page\t4. Last Page\t5. Exit");
                        String userInput = Helper.getUserInput("➡️Choose: ", "Invalid choice!", "[1-5]");
                        switch (Integer.parseInt(userInput)) {
                            case 1:
                                pageNum = 1;
                                break;
                            case 2:
                                pageNum = pageNum == totalPage ? totalPage : pageNum + 1;
                                break;
                            case 3:
                                pageNum = pageNum == 1 ? 1 : pageNum - 1;
                                break;
                            case 4:
                                pageNum = totalPage;
                                break;
                            case 5:
                                break display;
                        }
                    } while (true);
                    break;
                //delete employee
                case 4:
                    if (!allStaff.isEmpty()) {
                        AtomicBoolean deletedUserFound = new AtomicBoolean(false);
                        System.out.println("=".repeat(15) + "* Remove Employee *" + "=".repeat(15));
                        do {
                            int inputId;
                            inputId = Integer.parseInt(Helper.getUserInput("➡️ Enter ID to Remove : ", "Invalid id format", "\\d+"));
                            Optional<StaffMember> staff = allStaff.stream().filter(staffMember -> staffMember.getId() == inputId).findFirst();
                            staff.ifPresentOrElse(staffMember -> {
                                deletedUserFound.set(true);
                                allStaff.remove(staff.get());
                                Helper.printSuccessMsg("User with id " + inputId + " have been removed!");
                            }, () -> {
                                Helper.printErrorMsg("User not found");
                            });
                        } while (!deletedUserFound.get());
                    } else {
                        Helper.printErrorMsg("There's no staff");
                        System.out.println("Press any key to go back...");
                        sc.nextLine();
                    }

                    break;
                //exit
                case 5:
                    System.out.println("Thank you 🙏 Good bye 😁");
                    return;
            }
        } while (true);
    }
}