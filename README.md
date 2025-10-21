# AdventureXP - Backend

**AdventureXP Backend** manages all business logic, data storage, and API endpoints for the action park reservation system.

---

## 🌐 Features


- Manage activity schedules and participant profiles  
- Handle both individual and company reservations  
- Enforce age restrictions and activity-specific rules  
- Employee management with roles (Manager, Operator)  
- Secure authentication and role-based access control  
- Reservation history tracking (and future equipment status) 

---

## 🧱 Technologies

- **Backend:** Java 21, Spring Boot, JDBC  
- **Database (Production):** MySQL (via Docker)  
- **Database (Testing):** H2 In-Memory Database  
- **Cloud Integration:** Azure (for deployment)  
- **Build Tool:** Maven  
- **API:** REST (JSON)  
- **Containerization:** Docker & Docker Compose  
- **CI/CD:** GitHub Actions (Build → Test → Deploy)

---

## 📁 Project Structure (Backend)

```plaintext
white.monster.energy.adventurebackend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── white.monster.energy.adventurebackend/
│   │   │       ├── activity/
│   │   │       │   ├── Activity.java
│   │   │       │   ├── ActivityController.java
│   │   │       │   ├── ActivityRepository.java
│   │   │       │   └── ActivityService.java
│   │   │       │
│   │   │       ├── bookedActivities/
│   │   │       │   ├── BookedActivity.java
│   │   │       │   ├── BookedActivityController.java
│   │   │       │   ├── BookedActivityRepository.java
│   │   │       │   └── BookedActivityService.java
│   │   │       │
│   │   │       ├── booking/
│   │   │       │   ├── Booking.java
│   │   │       │   ├── BookingController.java
│   │   │       │   ├── BookingDto.java
│   │   │       │   ├── BookingRepository.java
│   │   │       │   └── BookingService.java
│   │   │       │
│   │   │       ├── profile/
│   │   │       │   ├── Profile.java
│   │   │       │   ├── ProfileController.java
│   │   │       │   ├── ProfileRepository.java
│   │   │       │   ├── ProfileService.java
│   │   │       │   └── ProfileType.java
│   │   │       │
│   │   │       └── AdventureXP.java              # Main application entry point
│   │   │
│   │   └── resources/                            # Application resources
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
|       |     └── adventurebackendtest/             # Unit and integration tests
│       │           ├── activityTest/
│       │           │   ├── ActivityControllerTest.java
│       │           │   ├── ActivityRepositoryTest.java
│       │           │   ├── ActivityService.java
│       │           │   └── ActivityTest.java
│       │           │
│       │           ├── bookedActivitiesTest/
│       │           │   ├── BookedActivityControllerTest.java
│       │           │   ├── BookedActivityRepositoryTest.java
│       │           │   ├── BookedActivityServiceTest.java
│       │           │   └── BookedActivityTest.java
│       │           │
│       │           ├── bookingTest/
│       │           │   ├── BookingControllerTest.java
│       │           │   ├── BookingDtoTest.java
│       │           │   ├── BookingRepositoryTest.java
│       │           │   ├── BookingServiceTest.java
│       │           │   └── BookingTest.java
│       │           │
│       │           ├── profileTest/
│       │           │   ├── ProfileControllerTest.java
│       │           │   ├── ProfileRepositoryTest.java
│       │           │   ├── ProfileServiceTest.java
│       │           │   ├── ProfileTypeTest.java
│       │           │   └── ProfileTest.java
│       |
|       └── resources
               └── application-test.properties     
