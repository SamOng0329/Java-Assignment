# House Lander 🏠

A console-based Java application for managing a property rental business — properties, leases, payments, tenants, landlords, agents, and staff, all driven by a role-based login and text menus.

```
  _  _                                               _                        _
 | || |    ___    _  _     ___     ___      o O O   | |    __ _    _ _     __| |    ___      _ _
 | __ |   / _ \  | +| |   (_-<    / -_)    o        | |   / _` |  | ' \   / _` |   / -_)    | '_|
 |_||_|   \___/   \_,_|   /__/_   \___|   TS__[O]  _|_|_  \__,_|  |_||_|  \__,_|   \___|   _|_|_
                                          {======|
                                        ./o--000'
              Welcome to House Lander v1.0
```

## What it does

House Lander is a menu-driven simulation of a rental agency's back office. On startup you log in (or register as a new tenant), and depending on your account's **role**, you get a different set of menus:

| Role | Can do |
|---|---|
| **Tenant** | Browse available properties, apply for/view leases, view payment history, request & track property visits, edit own profile |
| **Agent** | Manage property listings, create/terminate/accept leases, approve or reject visit requests, record payments, register & manage tenants/landlords, edit own profile |
| **Landlord** | View/manage owned properties, view current & overdue leases for those properties, view payment history for their properties, edit own profile |
| **Staff** | Full admin access: manage properties, manage all user types (tenants, landlords, agents, staff), manage payments and leases, manage visit requests, generate reports |

All data lives in memory for the duration of a run (there's no database or file persistence) and is seeded with a handful of demo users, properties, leases, and payments on startup so you can log in and try things out immediately.

## Try it out

- Demo staff login: `admin` / `admin123`
- Demo tenant login: `jackson` / `jackson123`
- Demo agent login: `jieying` / `jieying123`
- Demo landlord login: `kahhooi` / `kahhooi123`

(See `initializeUsers()` in `PropertyFunction.java` for the full seed list.)

## Project structure

```
house-lander/
├── pom.xml
├── .gitignore
├── README.md
└── src/
    └── main/
        └── java/
            ├── Main.java                 # Entry point: login/register loop, routes to role menu
            │
            ├── UserRole.java             # Login credentials + role + link to a person record
            ├── UserInfo.java             # Abstract base for all "person" records
            ├── Tenant.java               # extends UserInfo
            ├── Landlord.java             # extends UserInfo — owns a list of Properties
            ├── Agent.java                # extends UserInfo — tracks deals completed
            │
            ├── Property.java             # Abstract base for a listing
            ├── ResidentialProperty.java  # extends Property (apartment/house, bedrooms, etc.)
            ├── CommercialProperty.java   # extends Property (office/retail/industrial, parking, etc.)
            │
            ├── LeaseRecord.java          # Links a Property + Tenant + optional Agent over a date range
            ├── PaymentRecord.java        # A payment tied to a LeaseRecord
            ├── VisitRequest.java         # A tenant's request to visit a property, with approval flow
            │
            ├── PropertyFunction.java     # Singleton service: owns all data lists + all business logic
            │                             # (CRUD for users/properties/leases/payments/visits, reports)
            │
            ├── TenantUI.java             # Tenant-role console menu
            ├── AgentUI.java              # Agent-role console menu
            ├── LandLordUI.java           # Landlord-role console menu
            ├── StaffFunction.java        # Staff-role console menu
            └── UiMaterial.java           # Shared banner + menu text for every role
```

All classes live in the default (unnamed) Java package, matching the original source, placed under the standard Maven `src/main/java` layout so it builds cleanly and is easy to open in an IDE or push to GitHub.

## Data model at a glance

- **`UserRole`** is the login account (username/password/role) and optionally points to a **`UserInfo`** subtype (`Tenant`, `Landlord`, or `Agent`) — Staff accounts have no linked person.
- **`Property`** is abstract; concrete listings are either **`ResidentialProperty`** or **`CommercialProperty`**, each with their own extra fields and a `PropertyStatus` (`AVAILABLE`, `RENTED`, `SOLD`).
- **`LeaseRecord`** ties a `Property` to a `Tenant` (and optionally an `Agent`), with a rent, a deposit (auto-calculated as 2× monthly rent), and a start/end date. Creating a lease updates the property's status automatically.
- **`PaymentRecord`** records a payment against a `LeaseRecord`.
- **`VisitRequest`** tracks a tenant's request to view a property, with `PENDING` / `APPROVED` / `REJECTED` states that an agent can act on.
- **`PropertyFunction`** is a singleton (`getInstance()`) that owns every list above and exposes all the business-logic methods (register/find/update/delete for each entity, lease/payment/visit workflows, and simple reports).

## Building

With Maven installed:

```bash
mvn clean package
```

This produces a runnable JAR at `target/house-lander.jar`.

## Running

```bash
java -jar target/house-lander.jar
```

Or, without Maven, compile and run directly:

```bash
javac -d out src/main/java/*.java
java -cp out Main
```

## Known limitations

- **No persistence** — all data resets every time the program restarts (it's re-seeded by `initializeUsers()`).
- **No package structure** — everything is in the default package; splitting into packages (`model`, `service`, `ui`) would be a natural next step.
- **Plaintext passwords** — credentials are stored and compared as plain strings; not suitable for anything beyond a class project/demo.
- Some display/formatting quirks (e.g. `generateAgentPerformanceReport`) are inherited as-is from the original source.
