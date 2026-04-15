# System Workflow and Module Implementation

This section outlines the operational workflow for each implemented module within the application. The system is designed with a role-based architecture (Staff, Agent, Landlord, Tenant), ensuring that each role interacts with the system through specialized menus. 

## 1. Authentication and Registration Module
This module acts as the entry point to the system, handling user onboarding, identity verification, and role routing upon entry.
1. **System Launch:** Upon starting the application, the system displays a welcoming banner and prompts the user to either "Login" or "Register".
   * **[Insert Screenshot: Main Welcome and Login/Register prompt]*
2. **Registration Process:** Selecting the register option prompts the user to input core details (Name, Email, Phone, IC Number, Username, Password). By default, public registration creates a "Tenant" profile.
   * **[Insert Screenshot: User inputting registration details]*
3. **Login & Role Routing:** The user inputs their authenticated username and password. The system verifies these credentials against the database and automatically redirects the user to their respective role-specific dashboard (e.g., Staff Menu, Landlord Menu).
   * **[Insert Screenshot: Successful login message branching to the specific Role Menu (e.g. Staff Main Menu)]*

---

## 2. Property Management Module
The Property Management module functions as the central hub for handling real estate assets, allowing users to browse all listings, add new properties, modify existing details, and delete records.
1. **Accessing the Module:** From the main dashboard, the user navigates by selecting option 1 to view the "Property Management" sub-menu.
   * **[Insert Screenshot: The "Property Management" sub-menu]*
2. **Viewing all Properties:** By selecting option 1 from the sub-menu, the user prompts the system to retrieve and display a detailed, formatted list of all registered properties currently held within the database, including their specific characteristics and availability status.
   * **[Insert Screenshot: Display format of "RES001" and other properties]*
3. **Adding Residential Property:** By selecting option 2 from the Property Management menu, the user initiates the "Add Residential Property" workflow, systematically inputting specific details—such as location, rent, size, property type (Apartment/House), and furnishings—to successfully generate and register a new residential listing linked to a landlord.
   * **[Insert Screenshot: Inputting Residential Property details and success message]*
4. **Adding Commercial Property:** By selecting option 3 from the Property Management menu, the user accesses the "Add Commercial Property" workflow, inputting specific business-oriented metrics—such as location, size, office type, parking availability, and loading dock access—to successfully generate and link a new commercial listing within the database.
   * **[Insert Screenshot: Inputting Commercial Property details and success message]*
5. **Updating Status:** To update a property's status, the user selects option 5 from the Property Management menu, inputs the specific Property ID they wish to modify, and chooses a new status (such as AVAILABLE, RENTED, or SOLD) which the system validates and immediately updates in the database.
   * **[Insert Screenshot: Entering a Property ID and updating the status to SOLD]*

---

## 3. User Management Module (Staff)
This module grants administrative staff the capability to manually register, edit, and audit other users inside the system.
1. **Role Selection:** The staff member navigates to User Management and selects the target demographic they wish to manage (Tenants, Agents, or Landlords).
   * **[Insert Screenshot: User Management Menu]*
2. **Viewing Profiles:** Staff can systematically print out all registered profiles for that role to view contact information and IDs.
   * **[Insert Screenshot: List of displayed Tenants/Agents]*
3. **Editing User Data:** Selecting the update option prompts the staff to enter a specific User ID (e.g., TEN001). The system displays a sub-menu of editable fields (Name, IC, Email, Phone), which update immediately upon saving.
   * **[Insert Screenshot: Staff successfully updating a user's phone number or email]*

---

## 4. Lease Management Module
The Lease module governs the creation and lifecycle of rental contracts mapping Tenants to Properties.
1. **Creating a Lease:** A Tenant, Agent, or Staff member selects "Create Lease". The user provides the target Property ID, Tenant ID, Agent ID, and specific Start/End dates. The system fetches the monthly rent and generates a pending lease record.
   * **[Insert Screenshot: Creating a new lease transaction]*
2. **Accepting a Lease:** If created by an Agent or Tenant, an Agent must explicitly navigate to the "Accept Lease Request" menu and approve the pending lease, switching its status to active.
   * **[Insert Screenshot: Agent accepting/verifying a Lease ID]*
3. **Viewing Leases (Overdue Detection):** Users can view their assigned leases. Using the "View Overdue Lease" option, the system scans the database to flag and display leases where the documented End Date has surpassed today's calendar date.
   * **[Insert Screenshot: Output of the Overdue Leases function]*
4. **Terminating a Lease:** An authorized user enters a Lease ID to forcefully terminate the contract, automatically resetting the linked property's status back to "AVAILABLE" in the marketplace.
   * **[Insert Screenshot: Successful termination message for a lease]*

---

## 5. View Requests Module
This module streamlines the scheduling of on-site property viewings between Tenants and Agents.
1. **Requesting a Visit (Tenant):** A tenant selects "Create Visit Request", picking a Property ID, the corresponding Agent ID, and their preferred date.
   * **[Insert Screenshot: Tenant creating a Visit Request]*
2. **Managing Requests (Agent):** The Agent opens the Visit Request menu to view tickets assigned strictly to them. They input the Request ID and can cast a boolean decision (`true` to approve, `false` to reject). 
   * **[Insert Screenshot: Agent approving or rejecting a Visit Request ID]*

---

## 6. Payment Management Module
This module tracks monetary transactions linked to active properties and leases.
1. **Making a Payment:** The Staff or Agent inputs an active Lease ID. The system confirms the Tenant and Rent amount. The user inputs the payment amount and selects the transaction medium (Cash, Online Transfer, Cheque).
   * **[Insert Screenshot: Completing a payment transaction]*
2. **Auditing Payments:** Using the viewing filters, users can cross-reference payment history specifically by isolated Lease IDs or filter by Tenant IDs to ensure accounts are settled.
   * **[Insert Screenshot: Display output filtering all payments made by a specific Tenant]*

---

## 7. Reports Module
This module provides high-level system analytics for staff administrators.
1. **Property Report:** By selecting the Property Report option, the system tallies and outputs a breakdown of the overall property ecosystem, highlighting the ratio of Rented vs. Available vs. Sold units.
   * **[Insert Screenshot: The printed Property Analytics Report]*
2. **Agent Performance Report:** By selecting the Agent Performance option, the system aggregates completed deals from the database to present a leaderboard charting the success rates of agents registered within the firm.
   * **[Insert Screenshot: The printed Agent Performance leaderboard]*
