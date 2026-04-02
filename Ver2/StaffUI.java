import java.util.Scanner;
import java.util.ArrayList;

public class StaffUI {
    public void StaffMenu(UserRole LoggedUser, ArrayList<Tenant> tenantList, ArrayList<Landlord> landlordList,
            ArrayList<Agent> agentList, ArrayList<Property> propertyList) {
        PropertyFunction pf = PropertyFunction.getInstance();
        boolean logout = false;
        Scanner scanner = new Scanner(System.in);
        Uimaterial.clearscreen();

        do {
            try {
                Uimaterial.displayoperationmenu(LoggedUser);
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                Uimaterial.clearscreen();
                switch (choice) {
                    case 1:
                        Uimaterial.clearscreen();
                        break;
                    case 2:

                        boolean leave = false;
                        do {
                            try {
                                System.out.println("User Management:");
                                System.out.println("1. Register User");
                                System.out.println("2. Update User Information");
                                System.out.println("3. Delete User");
                                System.out.print("Enter your choice(4 to go back): ");
                                int userChoice = scanner.nextInt();
                                scanner.nextLine();

                                Uimaterial.clearscreen();

                                if (userChoice == 1) {
                                    RegistrationProcess(LoggedUser, scanner, pf);
                                    Uimaterial.clearscreen();

                                } else if (userChoice == 2) {
                                    EditProfileProcess(LoggedUser, scanner, pf);
                                    Uimaterial.clearscreen();
                                }

                                else if (userChoice == 3) {
                                    leave = true;
                                } else if (userChoice == 4) {
                                    leave = true;
                                } else {
                                    Uimaterial.clearscreen();
                                    System.out.println("Invalid choice. Returning to main menu.");

                                }
                            } catch (Exception e) {
                                Uimaterial.clearscreen();
                                System.out.println("Invalid input. Please enter a number.");
                                System.out.print("\n");
                                scanner.nextLine(); // Clear the invalid input
                            }

                        } while (!leave);
                        break;

                    case 3:

                        boolean back = false;
                        do {
                            try {
                                for (Agent agent : agentList) {
                                    System.out.println(agent.toString());
                                }
                                System.out.print("Want to go back to main menu? (Y/N): ");
                                String backChoice = scanner.nextLine();
                                if (backChoice.equalsIgnoreCase("Y")) {
                                    back = true;
                                    Uimaterial.clearscreen();

                                } else if (backChoice.equalsIgnoreCase("N")) {
                                    Uimaterial.clearscreen();
                                } else {
                                    Uimaterial.clearscreen();
                                    System.out.println("Invalid choice. Please enter Y or N.");

                                }
                            } catch (Exception e) {
                                Uimaterial.clearscreen();
                                System.out.println("Invalid input. Please enter Y or N.");
                                System.out.print("\n");
                                scanner.nextLine(); // Clear the invalid input
                            }
                        } while (!back);
                        break;

                    case 4:
                        boolean back1 = false;
                        do {
                            try {
                                System.out.println(LoggedUser.toString());
                                System.out.print("Want to go back to main menu? (Y/N): ");
                                String backChoice = scanner.nextLine();
                                if (backChoice.equalsIgnoreCase("Y")) {
                                    back1 = true;
                                    Uimaterial.clearscreen();

                                } else if (backChoice.equalsIgnoreCase("N")) {
                                    Uimaterial.clearscreen();
                                } else {
                                    Uimaterial.clearscreen();
                                    System.out.println("Invalid choice. Please enter Y or N.");

                                }
                            } catch (Exception e) {
                                Uimaterial.clearscreen();
                                System.out.println("Invalid input. Please enter Y or N.");
                                System.out.print("\n");
                                scanner.nextLine(); // Clear the invalid input
                            }
                        } while (!back1);
                        break;

                    case 5:
                        System.out.println("Logging out...");
                        logout = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                Uimaterial.clearscreen();
                System.out.println("Invalid input. Please enter a number.");
                System.out.print("\n");
                scanner.nextLine(); // Clear the invalid input
            }

        } while (!logout);
    }

    public void RegistrationProcess(UserRole loggedUser, Scanner scanner, PropertyFunction pf) {
        boolean back = false;
        Uimaterial.clearscreen();
        do {
            try {

                Uimaterial.RegisterMenu(loggedUser);
                System.out.print("Enter your choice(4 to go back): ");
                int registerChoice = scanner.nextInt();
                scanner.nextLine();

                Uimaterial.clearscreen();

                if (registerChoice == 1) {
                    System.out.println("Registering for Agent...");
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();
                    pf.registerAgent(name, email, phone, username, password);
                    Uimaterial.clearscreen();
                } else if (registerChoice == 2) {
                    System.out.println("Registering for Landlord...");
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();
                    pf.registerLandlord(name, email, phone, username, password);
                    Uimaterial.clearscreen();
                } else if (registerChoice == 3) {
                    System.out.println("Registering for Tenant...");
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter IC number: ");
                    String icNumber = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();
                    pf.registerTenant(name, email, phone, icNumber, username, password);
                    Uimaterial.clearscreen();
                } else if (registerChoice == 4) {
                    back = true;
                } else {
                    Uimaterial.clearscreen();
                    System.out.println("Invalid choice. Returning to main menu.");

                }

            } catch (Exception e) {
                Uimaterial.clearscreen();
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input

            }
        } while (!back);
    }

    public void EditProfileProcess(UserRole loggedUser, Scanner scanner, PropertyFunction pf) {
        boolean back = false;
        Uimaterial.clearscreen();
        do {
            try {

                Uimaterial.editprofileFirstMenu(loggedUser);
                System.out.print("Enter your choice(4 to go back): ");
                int editChoice = scanner.nextInt();
                scanner.nextLine();
                boolean success = false;
                Uimaterial.clearscreen();

                if (editChoice == 1) {
                    try {
                        System.out.print("Enter Tenant ID to edit: ");
                        String tenantId = scanner.nextLine();
                        boolean Exists = checkIDexists(tenantId, UserRole.Role.TENANT, pf);
                        if (Exists) {
                            boolean innerBack = false;
                            Uimaterial.clearscreen();
                            do {
                                try {
                                    Uimaterial.editprofileSecondMenu(UserRole.Role.TENANT);
                                    int choice1 = scanner.nextInt();
                                    scanner.nextLine();
                                    Uimaterial.clearscreen();

                                    String newinfo;
                                    if (choice1 == 1) {
                                        System.out.print("New Name:");
                                        newinfo = scanner.nextLine();
                                        success = pf.updateAgentDetails(tenantId, choice1, newinfo);
                                        Uimaterial.clearscreen();
                                        if (success) {
                                            System.out.println("Tenant profile updated successfully.");
                                        } else {
                                            System.out.println(
                                                    "Failed to update tenant profile. Please check the Tenant ID and try again.");
                                        }
                                    } else if (choice1 == 2) {
                                        System.out.print("New IC Number:");
                                        newinfo = scanner.nextLine();
                                        success = pf.updateAgentDetails(tenantId, choice1, newinfo);
                                        Uimaterial.clearscreen();
                                        if (success) {
                                            System.out.println("Tenant profile updated successfully.");
                                        } else {
                                            System.out.println(
                                                    "Failed to update tenant profile. Please check the Tenant ID and try again.");
                                        }
                                    } else if (choice1 == 3) {
                                        System.out.print("New Email:");
                                        newinfo = scanner.nextLine();
                                        success = pf.updateAgentDetails(tenantId, choice1, newinfo);
                                        Uimaterial.clearscreen();
                                        if (success) {
                                            System.out.println("Tenant profile updated successfully.");
                                        } else {
                                            System.out.println(
                                                    "Failed to update tenant profile. Please check the Tenant ID and try again.");
                                        }
                                    } else if (choice1 == 4) {
                                        System.out.print("New Phone Number:");
                                        newinfo = scanner.nextLine();
                                        success = pf.updateAgentDetails(tenantId, choice1, newinfo);
                                        Uimaterial.clearscreen();
                                        if (success) {
                                            System.out.println("Tenant profile updated successfully.");
                                        } else {
                                            System.out.println(
                                                    "Failed to update tenant profile. Please check the Tenant ID and try again.");
                                        }
                                    } else if (choice1 == 5) {
                                        innerBack = true;
                                        Uimaterial.clearscreen();
                                    } else {
                                        Uimaterial.clearscreen();
                                        System.out.println("Invalid choice. Returning to previous menu.");
                                    }
                                } catch (Exception e) {
                                    Uimaterial.clearscreen();
                                    System.out.println("Invalid input. Please enter a number.");
                                    scanner.nextLine(); // Clear the invalid input
                                }
                            } while (!innerBack);

                        } else {
                            Uimaterial.clearscreen();
                            System.out.println("Tenant ID not found. Returning to main menu.");

                        }
                    } catch (Exception e) {
                        Uimaterial.clearscreen();
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine(); // Clear the invalid input

                    }
                }

                else if (editChoice == 2) {
                    try {
                        System.out.print("Enter Landlord ID to edit: ");
                        String landlordId = scanner.nextLine();
                        boolean back1 = false;

                        boolean Exists = checkIDexists(landlordId, UserRole.Role.LANDLORD, pf);
                        if (Exists) {
                            Uimaterial.clearscreen();
                            do {
                                try {

                                    Uimaterial.editprofileSecondMenu(UserRole.Role.LANDLORD);
                                    int choice1 = scanner.nextInt();
                                    scanner.nextLine();
                                    String newinfo;
                                    Uimaterial.clearscreen();

                                    if (choice1 == 1) {
                                        System.out.print("New Name:");
                                        newinfo = scanner.nextLine();
                                        success = pf.updateLandlordDetails(landlordId, choice1, newinfo);
                                        if (success) {
                                            System.out.println("Landlord profile updated successfully.");
                                        } else {
                                            System.out.println(
                                                    "Failed to update landlord profile. Please check the Landlord ID and try again.");
                                        }
                                    } else if (choice1 == 2) {
                                        System.out.print("New Email:");
                                        newinfo = scanner.nextLine();
                                        success = pf.updateLandlordDetails(landlordId, choice1, newinfo);
                                        if (success) {
                                            System.out.println("Landlord profile updated successfully.");
                                        } else {
                                            System.out.println(
                                                    "Failed to update landlord profile. Please check the Landlord ID and try again.");
                                        }
                                    } else if (choice1 == 3) {
                                        System.out.print("New Phone Number:");
                                        newinfo = scanner.nextLine();
                                        success = pf.updateLandlordDetails(landlordId, choice1, newinfo);
                                        if (success) {
                                            System.out.println("Landlord profile updated successfully.");
                                        } else {
                                            System.out.println(
                                                    "Failed to update landlord profile. Please check the Landlord ID and try again.");
                                        }

                                    } else if (choice1 == 4) {
                                        back1 = true;
                                        Uimaterial.clearscreen();
                                    } else {
                                        Uimaterial.clearscreen();
                                        System.out.println("Invalid choice. Returning to main menu.");

                                    }
                                } catch (Exception e) {
                                    Uimaterial.clearscreen();
                                    System.out.println("Invalid input. Please enter a number.");
                                    scanner.nextLine(); // Clear the invalid input
                                }

                            } while (!back1);

                        } else {
                            Uimaterial.clearscreen();
                            System.out.println("Landlord ID not found. Returning to main menu.");
                        }
                    } catch (Exception e) {
                        Uimaterial.clearscreen();
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine(); // Clear the invalid input
                    }
                }

                else if (editChoice == 3) {
                    try {
                        System.out.print("Enter Agent ID to edit: ");
                        String agentId = scanner.nextLine();
                        boolean back1 = false;

                        boolean Exists = checkIDexists(agentId, UserRole.Role.AGENT, pf);
                        if (Exists) {
                            Uimaterial.clearscreen();
                            do {
                                try {

                                    Uimaterial.editprofileSecondMenu(UserRole.Role.AGENT);
                                    int choice1 = scanner.nextInt();
                                    scanner.nextLine();
                                    String newinfo;
                                    Uimaterial.clearscreen();

                                    if (choice1 == 1) {
                                        System.out.print("New Name:");
                                        newinfo = scanner.nextLine();
                                        success = pf.updateAgentDetails(agentId, choice1, newinfo);
                                        if (success) {
                                            System.out.println("Agent profile updated successfully.");
                                        } else {
                                            System.out.println(
                                                    "Failed to update agent profile. Please check the Agent ID and try again.");
                                        }
                                    } else if (choice1 == 2) {
                                        System.out.print("New Email:");
                                        newinfo = scanner.nextLine();
                                        success = pf.updateAgentDetails(agentId, choice1, newinfo);
                                        if (success) {
                                            System.out.println("Agent profile updated successfully.");
                                        } else {
                                            System.out.println(
                                                    "Failed to update agent profile. Please check the Agent ID and try again.");
                                        }
                                    } else if (choice1 == 3) {
                                        System.out.print("New Phone Number:");
                                        newinfo = scanner.nextLine();
                                        success = pf.updateAgentDetails(agentId, choice1, newinfo);
                                        if (success) {
                                            System.out.println("Agent profile updated successfully.");
                                        } else {
                                            System.out.println(
                                                    "Failed to update agent profile. Please check the Agent ID and try again.");
                                        }

                                    } else if (choice1 == 4) {
                                        back1 = true;
                                        Uimaterial.clearscreen();
                                    } else {
                                        Uimaterial.clearscreen();
                                        System.out.println("Invalid choice. Returning to main menu.");

                                    }
                                } catch (Exception e) {
                                    Uimaterial.clearscreen();
                                    System.out.println("Invalid input. Please enter a number.");
                                    scanner.nextLine(); // Clear the invalid input
                                }

                            } while (!back1);

                        } else {
                            Uimaterial.clearscreen();
                            System.out.println("Agent ID not found. Returning to main menu.");
                        }
                    } catch (Exception e) {
                        Uimaterial.clearscreen();
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine(); // Clear the invalid input
                    }
                }

                else if (editChoice == 4) {
                    back = true;
                    Uimaterial.clearscreen();
                }

                else {
                    Uimaterial.clearscreen();
                    System.out.println("Invalid choice. Return to main menu.");
                }
            } catch (Exception e) {
                Uimaterial.clearscreen();
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input

            }
        } while (!back);
    }

    public boolean checkIDexists(String id, UserRole.Role role, PropertyFunction pf) {
        if (role == UserRole.Role.TENANT) {
            for (Tenant tenant : pf.getTenantList()) {
                if (tenant.getPersonId().equals(id)) {
                    return true;
                }
            }
        } else if (role == UserRole.Role.LANDLORD) {
            for (Landlord landlord : pf.getLandlordList()) {
                if (landlord.getPersonId().equals(id)) {
                    return true;
                }
            }
        } else if (role == UserRole.Role.AGENT) {
            for (Agent agent : pf.getAgentList()) {
                if (agent.getPersonId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

}
