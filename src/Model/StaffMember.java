package Model;

import Utils.Helper;

public abstract class StaffMember {
    private static int idTracker= 1;
    protected int id;
    protected String name;
    protected String address;

    public StaffMember() {
    }

    public StaffMember(String name, String address) {
        this.id = idTracker;
        this.name = name;
        this.address = address;
        idTracker++;
    }
    public abstract  double pay();
    public  StaffMember addStaff(){
        id=idTracker;
        System.out.println("ID: "+idTracker);
        this.name=Helper.getUserInput("Enter user name: ","Please input a valid username","([a-zA-Z]+\\s*)+");
        this.address=Helper.getUserInput("Enter address: ","Please input a valid address",".*");
        idTracker++;
        return this;
    };
    public void showUpdateMenu(){
        System.out.print("\n1. Name\t 2. Address\t");
    }
    @Override
    public String toString() {
        return String.format("ID: %n\nName: %s\nAddress: %s",id,name,address);
    }
    public void updateStaff(){
       do{
           String input = Helper.getUserInput("➡️Select column number:", "Invalid input", subClassValidRegex());
           switch (Integer.parseInt(input)){
               case 0:
                   return;
               case 1:
                   name=Helper.getUserInput("➡️Change Name To:", "Invalid input", "([a-zA-Z]+\\s*)+");
                   break;
               case 2:
                   address=Helper.getUserInput("➡️Change Address To:", "Invalid input", ".*");
                   break;
               default:
                   handleSubclassUpdate(Integer.parseInt(input));
           }
           System.out.println("-".repeat(22));
           Helper.printSuccessMsg("Updated Successfully");
           System.out.println("-".repeat(22));
       }while (true);
    };
    protected abstract String subClassValidRegex();
    protected abstract void handleSubclassUpdate(int option);
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
