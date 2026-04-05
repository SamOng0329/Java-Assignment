package files1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Handles saving and loading all data to a readable text file.
 */
public class Database {

    public static void save(PropertyManager manager, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            // Save Properties
            writer.write("[Properties]\n");
            for (Property p : manager.getProperties()) {
                if (p instanceof ResidentialProperty rp) {
                    // ID|LOCATION|PRICE|SQFT|STATUS|R-TYPE|BEDS|BATHS|FURNISHED
                    writer.write(String.format("%s|%s|%.2f|%.2f|%s|RESIDENTIAL|%s|%d|%d|%b\n",
                            rp.getPropertyId(), rp.getLocation(), rp.getListingPrice(), rp.getSquareFootage(),
                            rp.getStatus(), rp.getResidentialType(), rp.getNumBedrooms(), rp.getNumBathrooms(),
                            rp.isFurnished()));
                } else if (p instanceof CommercialProperty cp) {
                    // ID|LOCATION|PRICE|SQFT|STATUS|C-TYPE|PARKING|DOCK
                    writer.write(String.format("%s|%s|%.2f|%.2f|%s|COMMERCIAL|%s|%d|%b\n",
                            cp.getPropertyId(), cp.getLocation(), cp.getListingPrice(), cp.getSquareFootage(),
                            cp.getStatus(), cp.getCommercialType(), cp.getNumParkingSpots(), cp.isHasLoadingDock()));
                }
            }

            // Save Tenants
            writer.write("[Tenants]\n");
            for (Tenant t : manager.getTenants()) {
                writer.write(String.format("%s|%s|%s|%s|%s|%b\n",
                        t.getPersonId(), t.getName(), t.getEmail(), t.getPhone(),
                        t.getIcNumber(), t.isHasOverduePayment()));
            }

            // Save Agents
            writer.write("[Agents]\n");
            for (Agent a : manager.getAgents()) {
                writer.write(String.format("%s|%s|%s|%s|%s|%d\n",
                        a.getPersonId(), a.getName(), a.getEmail(), a.getPhone(),
                        a.getLicenseNumber(), a.getTotalDealsCompleted()));
            }

            // Save Landlords
            writer.write("[Landlords]\n");
            for (Landlord ll : manager.getLandlords()) {
                // Build a comma-separated list of owned property IDs
                StringBuilder propIds = new StringBuilder();
                for (Property p : ll.getOwnedProperties()) {
                    if (propIds.length() > 0)
                        propIds.append(",");
                    propIds.append(p.getPropertyId());
                }
                writer.write(String.format("%s|%s|%s|%s|%s|%s\n",
                        ll.getPersonId(), ll.getName(), ll.getEmail(), ll.getPhone(),
                        ll.getBankAccount(), propIds.toString()));
            }

            // Save Users
            writer.write("[Users]\n");
            for (User u : manager.getUsers()) {
                String linkedId = (u.getLinkedPerson() != null) ? u.getLinkedPerson().getPersonId() : "NONE";
                writer.write(String.format("%s|%s|%s|%s\n",
                        u.getUsername(), u.getPassword(), u.getRole(), linkedId)); // Changed private password to a
                                                                                   // getter later, but wait password
                                                                                   // has no getter. We need to add
                                                                                   // getter for password in User.java
            }

            // Save Leases
            writer.write("[Leases]\n");
            for (LeaseRecord l : manager.getLeases()) {
                String agtId = (l.getAgent() != null) ? l.getAgent().getPersonId() : "NONE";
                writer.write(String.format("%s|%s|%s|%s|%s|%s|%.2f|%s\n",
                        l.getLeaseId(), l.getProperty().getPropertyId(), l.getTenant().getPersonId(), agtId,
                        l.getStartDate(), l.getEndDate(), l.getMonthlyRent(), l.isActive()));
            }

            // Save Payments
            writer.write("[Payments]\n");
            for (PaymentRecord p : manager.getPayments()) {
                String leaseId = (p.getLease() != null) ? p.getLease().getLeaseId() : "DELETED";
                writer.write(String.format("%s|%s|%.2f|%s|%s|%b\n",
                        p.getPaymentId(), leaseId, p.getAmountPaid(),
                        p.getPaymentDate(), p.getPaymentMethod(), p.isLatePayment()));
            }

            // Save Visit Requests
            writer.write("[VisitRequests]\n");
            for (VisitRequest vr : manager.getVisitRequests()) {
                writer.write(String.format("%s|%s|%s|%s|%s|%s\n",
                        vr.getRequestId(),
                        vr.getProperty().getPropertyId(),
                        vr.getTenant().getPersonId(),
                        vr.getPreferredDate(),
                        vr.getStatus(),
                        vr.getLandlordReply().replace("|", " ")));
            }

            // Save Rental Applications
            writer.write("[RentalApplications]\n");
            for (RentalApplication ra : manager.getRentalApplications()) {
                String agtId = (ra.getAgent() != null) ? ra.getAgent().getPersonId() : "NONE";
                writer.write(String.format("%s|%s|%s|%s|%s|%.2f|%s|%s|%s\n",
                        ra.getApplicationId(),
                        ra.getProperty().getPropertyId(),
                        ra.getTenant().getPersonId(),
                        agtId,
                        ra.getDesiredMoveIn(),
                        ra.getOfferedRent(),
                        ra.getMessage().replace("|", " "),
                        ra.getStatus(),
                        ra.getAgentReply().replace("|", " ")));
            }

            // Save Counters
            writer.write("[Counters]\n");
            // We need a way to get the counters from PropertyManager too.
            // Let's assume the sizes represent the counters, or we can just derive them
            // from sizes.
            // ID = "PROP001". Size = 1. Next counter = 2. So size + 1.
            writer.write(String.format("%d|%d|%d|%d|%d|%d|%d\n",
                    manager.getProperties().size() + 1,
                    manager.getTenants().size() + 1,
                    manager.getAgents().size() + 1,
                    manager.getLeases().size() + 1,
                    manager.getPayments().size() + 1,
                    manager.getVisitRequests().size() + 1,
                    manager.getRentalApplications().size() + 1));

        } catch (Exception e) {
            System.out.println("  [ERROR] Could not save database: " + e.getMessage());
        }
    }

    public static void load(PropertyManager manager, String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("  [INFO] No existing database found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String currentSection = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                if (line.startsWith("[")) {
                    currentSection = line;
                    continue;
                }

                String[] parts = line.split("\\|");

                try {
                    switch (currentSection) {
                        case "[Properties]":
                            String pId = parts[0];
                            String pLoc = parts[1];
                            double pPrice = Double.parseDouble(parts[2]);
                            double pSqft = Double.parseDouble(parts[3]);
                            Property.PropertyStatus pStatus = Property.PropertyStatus.valueOf(parts[4]);

                            if (parts[5].equals("RESIDENTIAL")) {
                                ResidentialProperty.ResidentialType rType = ResidentialProperty.ResidentialType
                                        .valueOf(parts[6]);
                                int beds = Integer.parseInt(parts[7]);
                                int baths = Integer.parseInt(parts[8]);
                                boolean furn = Boolean.parseBoolean(parts[9]);
                                ResidentialProperty rp = new ResidentialProperty(pId, pLoc, pPrice, pSqft, rType, beds,
                                        baths, furn);
                                rp.setStatus(pStatus);
                                manager.getProperties().add(rp);
                            } else {
                                CommercialProperty.CommercialType cType = CommercialProperty.CommercialType
                                        .valueOf(parts[6]);
                                int parking = Integer.parseInt(parts[7]);
                                boolean dock = Boolean.parseBoolean(parts[8]);
                                CommercialProperty cp = new CommercialProperty(pId, pLoc, pPrice, pSqft, cType, parking,
                                        dock);
                                cp.setStatus(pStatus);
                                manager.getProperties().add(cp);
                            }
                            break;

                        case "[Tenants]":
                            Tenant t = new Tenant(parts[0], parts[1], parts[2], parts[3], parts[4]);
                            t.setHasOverduePayment(Boolean.parseBoolean(parts[5]));
                            manager.getTenants().add(t);
                            break;

                        case "[Agents]":
                            Agent a = new Agent(parts[0], parts[1], parts[2], parts[3], parts[4]);
                            // Use a loop to increment deals if there is no setter.
                            int deals = Integer.parseInt(parts[5]);
                            for (int i = 0; i < deals; i++) {
                                a.incrementDeals();
                            }
                            manager.getAgents().add(a);
                            break;

                        case "[Landlords]":
                            Landlord ll = new Landlord(parts[0], parts[1], parts[2], parts[3], parts[4]);
                            // Re-link owned properties (stored as comma-separated IDs in parts[5])
                            if (parts.length > 5 && !parts[5].isBlank()) {
                                for (String propId : parts[5].split(",")) {
                                    Property owned = manager.findPropertyById(propId.trim());
                                    if (owned != null)
                                        ll.addOwnedProperty(owned);
                                }
                            }
                            manager.getLandlords().add(ll);
                            break;

                        case "[Users]":
                            User.Role role = User.Role.valueOf(parts[2]);
                            UserInfo linked = null;
                            if (role == User.Role.AGENT)
                                linked = manager.findAgentById(parts[3]);
                            else if (role == User.Role.TENANT)
                                linked = manager.findTenantById(parts[3]);
                            else if (role == User.Role.LANDLORD)
                                linked = manager.findLandlordById(parts[3]);
                            manager.getUsers().add(new User(parts[0], parts[1], role, linked));
                            break;

                        case "[Leases]":
                            Property lp = manager.findPropertyById(parts[1]);
                            Tenant lt = manager.findTenantById(parts[2]);
                            Agent la = parts[3].equals("NONE") ? null : manager.findAgentById(parts[3]);
                            LocalDate start = LocalDate.parse(parts[4]);
                            LocalDate end = LocalDate.parse(parts[5]);
                            double rent = Double.parseDouble(parts[6]);
                            LeaseRecord lease = new LeaseRecord(parts[0], lp, lt, la, start, end, rent);
                            if (!Boolean.parseBoolean(parts[7]))
                                lease.terminateLease();
                            for (LeaseRecord exist : manager.getLeases()) {
                                if (exist.getLeaseId().equals(parts[0]))
                                    return;
                            }
                            manager.getLeases().add(lease);
                            break;

                        case "[Payments]":
                            LeaseRecord pl = null;
                            if (!parts[1].equals("DELETED")) {
                                pl = manager.findLeaseById(parts[1]);
                            }
                            double pAmt = Double.parseDouble(parts[2]);
                            LocalDate pDate = LocalDate.parse(parts[3]);
                            boolean isLate = Boolean.parseBoolean(parts[5]);
                            PaymentRecord pay = new PaymentRecord(parts[0], pl, pAmt, pDate, parts[4], isLate);
                            manager.getPayments().add(pay);
                            break;

                        case "[VisitRequests]":
                            Property vrProp = manager.findPropertyById(parts[1]);
                            Tenant   vrTen  = manager.findTenantById(parts[2]);
                            if (vrProp != null && vrTen != null) {
                                LocalDate vrDate = LocalDate.parse(parts[3]);
                                VisitRequest vr = new VisitRequest(parts[0], vrTen, vrProp, vrDate, "");
                                vr.setStatus(VisitRequest.VisitStatus.valueOf(parts[4]));
                                vr.setLandlordReply(parts.length > 5 ? parts[5] : "");
                                manager.getVisitRequests().add(vr);
                            }
                            break;

                        case "[RentalApplications]":
                            Property raProp  = manager.findPropertyById(parts[1]);
                            Tenant   raTen   = manager.findTenantById(parts[2]);
                            Agent    raAgent = parts[3].equals("NONE") ? null : manager.findAgentById(parts[3]);
                            if (raProp != null && raTen != null) {
                                LocalDate raMoveIn = LocalDate.parse(parts[4]);
                                double raRent = Double.parseDouble(parts[5]);
                                String raMsg  = parts.length > 6 ? parts[6] : "";
                                RentalApplication ra = new RentalApplication(
                                        parts[0], raTen, raProp, raAgent, raMoveIn, raRent, raMsg);
                                ra.setStatus(RentalApplication.ApplicationStatus.valueOf(parts[7]));
                                ra.setAgentReply(parts.length > 8 ? parts[8] : "");
                                manager.getRentalApplications().add(ra);
                            }
                            break;

                        case "[Counters]":
                            manager.setCounters(
                                    Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                                    Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                                    Integer.parseInt(parts[4]));
                            if (parts.length > 6) {
                                manager.setRequestCounters(
                                        Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
                            }
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("  [WARNING] Failed to parse line in " + currentSection + ": " + line);
                }
            }
            System.out.println("  [INFO] Database loaded successfully.");
        } catch (Exception e) {
            System.out.println("  [ERROR] Could not load database: " + e.getMessage());
        }
    }
}
