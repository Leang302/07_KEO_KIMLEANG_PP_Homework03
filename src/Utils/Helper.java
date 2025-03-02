package Utils;

import Model.HourSalaryEmployee;
import Model.SalariedEmployee;
import Model.StaffMember;
import Model.Volunteer;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public interface Helper {
    String RESET = "\u001b[0m";
    String GREEN = "\u001b[32m";
    String BLUE = "\u001b[34m";
    String RED = "\u001b[31m";
    DecimalFormat df = new DecimalFormat("$#,##0.00");
    CellStyle align = new CellStyle(CellStyle.HorizontalAlign.center);

    //print main menu
    static void printMenu(int col, List<String> menuItem) {
        Table t = new Table(col, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        CellStyle align = new CellStyle(CellStyle.HorizontalAlign.center);
        CellStyle defaultAlign = new CellStyle(CellStyle.HorizontalAlign.left);
        if (col == 1) {
            t.addCell("STAFF MANAGEMENT SYSTEM", align);
            t.setColumnWidth(0, 50, 50);
        } else {
            for (int i = 0; i < col; i++) {
                t.setColumnWidth(i, 25, 25);
            }
        }
        AtomicInteger x = new AtomicInteger(1);
        menuItem.forEach(s -> {
            String cellValue = (col != 1 ? "" : " ".repeat(3)) + x.getAndIncrement() + " " + s;
            t.addCell(cellValue, col != 1 ? align : defaultAlign);
        });
        System.out.println(t.render());
    }

    //print employee table
    static void printTable(List<StaffMember> list, int pageNum, int pageSize) {
        Table t = new Table(9, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.ALL);
        List<String> headers = Arrays.asList("Type", "ID", "Name", "Address", "Salary", "Bonus", "Hour", "Rate", "Pay");
        //setting column width
        Map<Integer, Integer> columnIndexAndWith = Map.of(0, 28, 1, 10, 2, 20, 3, 20, 4, 20, 5, 20, 6, 14, 7, 14, 8, 14);
        columnIndexAndWith.forEach((index, size) -> t.setColumnWidth(index, size, size));
        //add header
        headers.forEach(s -> {
            t.addCell(BLUE + s + RESET, align);
        });
        //filter pagination
        List<StaffMember> paginatedList = list.stream().skip((pageNum - 1) * pageSize)  // Skip previous pages
                .limit(pageSize)             // Limit to page size
                .collect(Collectors.toList());
        //render all data
        paginatedList.forEach(staffMember -> {
            addRow(t, staffMember, 9);
        });
        System.out.println(t.render());
    }

    // print single employee
    static void printSingleEmployee(StaffMember staffMember) {
        String empType = staffMember.getClass().getSimpleName();
        int columnCount = getColumnCount(empType);
        Table t = new Table(columnCount, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.ALL);
        List<String> headers = getHeaders(empType);
        //setting column width
        Map<Integer, Integer> columnSize = getColumnSizeForTable(empType);
        columnSize.forEach((index, size) -> t.setColumnWidth(index, size, size));
        //add header
        headers.forEach(s -> {
            t.addCell(BLUE + s + RESET, align);
        });
        //render all data
        addRow(t, staffMember, columnCount);
        System.out.println(t.render());
    }

    //get size
    static List<String> getHeaders(String empType) {
        return switch (empType) {
            case "Volunteer" -> Arrays.asList("Type", "ID", "Name", "Address", "Salary", "Pay");
            case "SalariedEmployee" -> Arrays.asList("Type", "ID", "Name", "Address", "Salary", "Bonus", "Pay");
            case "HourSalaryEmployee" -> Arrays.asList("Type", "ID", "Name", "Address", "Hour", "Rate", "Pay");
            default -> Arrays.asList("Type", "ID", "Name", "Address", "Salary", "Bonus", "Hour", "Rate", "Pay");
        };
    }

    static int getColumnCount(String empType) {
        return switch (empType) {
            case "Volunteer" -> 6;
            case "SalariedEmployee", "HourSalaryEmployee" -> 7;
            default -> 9;
        };
    }

    static Map<Integer, Integer> getColumnSizeForTable(String empType) {
        return switch (empType) {
            case "Volunteer" -> Map.of(0, 28, 1, 10, 2, 20, 3, 20, 4, 20, 5, 20);
            case "SalariedEmployee", "HourSalaryEmployee" -> Map.of(0, 28, 1, 10, 2, 20, 3, 20, 4, 20, 5, 20, 6, 20);
            default -> throw new IllegalStateException("Unexpected value: " + empType);
        };
    }

    static String getType(String empType) {
        return switch (empType) {
            case "Volunteer" -> "Volunteer";
            case "SalariedEmployee" -> "Salaries Employee";
            case "HourSalaryEmployee" -> "Hour Salary Employee";
            default -> throw new IllegalStateException("Unexpected value: " + empType);
        };
    }

    //add row for table data
    static void addRow(Table t, StaffMember staffMember, int colCount) {
        String type = staffMember.getClass().getSimpleName();
        String name = staffMember.getName();
        String address = staffMember.getAddress();
        double bonus = 0, hour = 0, salary = 0, rate = 0, pay = 0;
        //get value base on type
        switch (type) {
            case "HourSalaryEmployee":
                HourSalaryEmployee hourEmp = (HourSalaryEmployee) staffMember;
                hour = hourEmp.getWorkHour();
                rate = hourEmp.getRate();
                break;
            case "SalariedEmployee":
                SalariedEmployee salariedEmp = (SalariedEmployee) staffMember;
                salary = salariedEmp.getSalary();
                bonus = salariedEmp.getBonus();
                break;
            case "Volunteer":
                Volunteer volunteer = (Volunteer) staffMember;
                salary = volunteer.getSalary();
                break;
        }
        t.addCell(getType(type), align);
        t.addCell(String.valueOf(staffMember.getId()), align);
        t.addCell(name, align);
        t.addCell(address, align);
        if (colCount == 9) {
            t.addCell(salary == 0 ? "---" : String.valueOf(df.format(salary)), align);
            t.addCell(bonus == 0 ? "---" : String.valueOf(df.format(bonus)), align);
            t.addCell(hour == 0 ? "---" : String.valueOf(hour + " h"), align);
            t.addCell(rate == 0 ? "---" : String.valueOf(df.format(rate)), align);
        } else if (colCount == 7 && type.equals("SalariedEmployee")) {
            t.addCell(String.valueOf(df.format(salary)), align);
            t.addCell(String.valueOf(df.format(bonus)), align);
        } else if (colCount == 7 && type.equals("HourSalaryEmployee")) {
            t.addCell(String.valueOf(hour + " h"), align);
            t.addCell(String.valueOf(df.format(rate)), align);
        } else if (colCount == 6) {
            t.addCell(String.valueOf(df.format(salary)), align);
        }
        t.addCell(String.valueOf(df.format(staffMember.pay())), align);
    }

    //get use input
    static String getUserInput(String prompt, String validateMsg, String regex) {
        String input;
        do {
            System.out.print(prompt);
            Scanner sc = new Scanner(System.in);
            input = sc.nextLine();
        } while (!validateInput(input, regex, validateMsg));
        return input;
    }

    //print error message
    static void printErrorMsg(String msg) {
        System.out.println(RED + msg + RESET);
    }

    //print success msg
    static void printSuccessMsg(String msg) {
        System.out.println(GREEN + msg + RESET);
    }

    //for validating empty and by regex
    static boolean validateInput(String value, String regex, String validateMsg) {
        if (value.trim().isEmpty()) {
            printErrorMsg("Input cannot be empty");
            return false;
        } else if (!value.matches(regex)) {
            printErrorMsg(validateMsg);
            return false;
        }
        return true;

    }
}
