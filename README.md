
# 🎓 School Management API (API Control Escolar)

A robust, production-ready REST API built with **Spring Boot 3** and **Java 21**, designed following a clean, service-oriented **Modular Monolith** architecture. This system manages academic structures, student and professor records, course offerings, and enrollment tracking backed by PostgreSQL.

---

## 🚀 Tech Stack

*   **Language:** Java 21 (Records, pattern matching, modern APIs)
*   **Framework:** Spring Boot 3 (Spring Web, Spring Data JPA, Hibernate)
*   **Database:** PostgreSQL
*   **Data Generation:** DataFaker (for rich, localized test seeding)
*   **Build Tool:** Maven

---

## 🏛️ Architecture & Design

This project is structured as a **Modular Monolith**, separating concerns cleanly into domains (`Carrera`, `Profesor`, `Materia`, `PeriodoLectivo`, `Alumno`, `Grupo`, `Inscripcion`).

*   **Service-Layer Validation:** Business logic and uniqueness checks (such as checking existing keys or names) are strictly handled inside services rather than raw repository bypassing.
*   **Idempotent Seeding:** Includes a robust `BulkDataInitializer` (`CommandLineRunner`) that safely populates test data on initial startup without violating foreign key constraints or triggering duplicate key errors.

---

## 📊 Domain Entities & Relationships
*   **Carrera:** Academic programs (e.g., Computer Science, Administration).
*   **Profesor:** Teaching staff linked to system user credentials and office assignments (*cubículo*).
*   **Materia:** Courses belonging to academic careers.
*   **PeriodoLectivo:** Academic terms/semesters.
*   **Alumno:** Student records.
*   **Grupo:** Class sections combining a subject, a professor, and a period.
*   **Inscripcion:** Student enrollment records into specific groups.

---

## ⚙️ Getting Started

### Prerequisites
Make sure you have the following installed on your local machine:
*   [Java 21 JDK](https://adoptium.net/)
*   [PostgreSQL](https://www.postgresql.org/)
*   Maven (or use the included `./mvnw` wrapper)

### 1. Clone the Repository
```bash
git clone [https://github.com/Krasn4ck/gestion-api](https://github.com/Krasn4ck/gestion-api)
cd gestion-api