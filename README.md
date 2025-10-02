# AdventureXP - Backend

**AdventureXP Backend** manages all business logic, data storage, and API endpoints for the action park reservation system.

---

## 🌐 Features

- Manage activity schedules, reservations, and equipment  
- Handle individual and company reservations  
- Enforce age restrictions and activity rules  
- Employee role management (Reservation Managers & Activity Managers)  
- Secure authentication and role-based access control  
- Track reservation history and equipment status  

---

## 🧱 Technologies

- **Backend:** Java 21, Spring Boot, JDBC  
- **Database:** MySQL (production), H2 In-Memory Database (testing)  
- **Cloud Integration:** Azure for deployment and storage  
- **Build Tool:** Maven  
- **API:** REST endpoints for frontend communication  

---

## 📁 Project Structure (Backend) 🚧

```plaintext
adventurexp-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/adventurexp/
│   │   │       ├── activity/
│   │   │       │   ├── IActivity.java
│   │   │       │   └── Activity.java
│   │   │       │
│   │   │       ├── reservation/
│   │   │       │   ├── IReservation.java
│   │   │       │   └── Reservation.java
│   │   │       │
│   │   │       ├── equipment/
│   │   │       │   ├── IEquipment.java
│   │   │       │   └── Equipment.java
│   │   │       │
│   │   │       ├── employee/
│   │   │       │   ├── IEmployee.java
│   │   │       │   └── Employee.java
│   │   │       │
│   │   │       ├── security/
│   │   │       │   ├── ISecuritySystem.java
│   │   │       │   └── SecuritySystem.java
│   │   │       │
│   │   │       └── AdventureXP.java              # Main application entry point
│   │   │
│   │   └── resources/                            # Application resources
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/adventurexptest/             # Unit and integration tests
