# WMS System - Workforce Management System

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![University](https://img.shields.io/badge/University-Silesian%20University%20of%20Technology-green.svg)](https://polsl.pl/en/)
[![Year](https://img.shields.io/badge/Year-2025-orange.svg)]()
[![Thesis](https://img.shields.io/badge/Project-Engineering%20Thesis-red.svg)]()

> **A comprehensive workforce management system combining web application, mobile app, and IoT hardware for efficient employee, department, and role administration**

Developed as an Engineering Thesis project at the Silesian University of Technology in Gliwice, this system provides modern solutions for workforce management challenges in contemporary organizations.

## 📋 Table of Contents

- [🎯 Project Overview](#-project-overview)
- [✨ Key Features](#-key-features)
- [🛠️ Technology Stack](#️-technology-stack)
- [🏗️ System Architecture](#️-system-architecture)
- [📊 Database Structure](#-database-structure)
- [🚀 Installation & Setup](#-installation--setup)
- [📖 Usage Guide](#-usage-guide)
- [🔐 User Roles & Permissions](#-user-roles--permissions)
- [📱 Mobile Application](#-mobile-application)
- [🔧 Hardware Component](#-hardware-component)
- [🧪 Testing & Validation](#-testing--validation)
- [🔒 Security Features](#-security-features)
- [🌟 Future Development](#-future-development)
- [🤝 Contributing](#-contributing)
- [📄 License](#-license)
- [👨‍🎓 Academic Information](#-academic-information)
- [📞 Contact](#-contact)

## 🎯 Project Overview

The WMS (Workforce Management System) is a modern, multi-platform solution designed to streamline workforce operations within organizations. This system automates traditional HR processes and provides comprehensive tools for managing employees, organizing departments, and defining roles with appropriate permissions.

### Problem Statement
Traditional workforce management methods in small and medium enterprises are often inefficient, error-prone, and time-consuming. Paper-based systems lack automation, real-time data access, and proper security measures.

### Solution
A complete digital transformation solution consisting of four integrated modules:
- **Web Application** - Comprehensive management interface
- **Mobile Application** - On-the-go access and NFC card management
- **Microcontroller System** - Hardware-based access control
- **Application Server** - Centralized business logic and data management

## ✨ Key Features

### 👥 Employee Management
- **Employee Profiles**: Comprehensive employee data management including personal information, contact details, and employment history
- **Role Assignment**: Flexible role-based access control with customizable permissions
- **Department Assignment**: Hierarchical organizational structure management
- **Employee Onboarding**: Streamlined registration and first-time login process

### 🏢 Organizational Structure
- **Hierarchical Units**: Multi-level organizational structure support
- **Department Management**: Create, edit, and manage company departments
- **Position Management**: Define and assign specific job positions
- **Reporting Structure**: Clear chain of command visualization

### ⏰ Work Time Management
- **Schedule Creation**: Visual schedule builder with drag-and-drop interface
- **Time Tracking**: Automated work time registration via NFC cards
- **Timesheet Management**: Digital timesheet submission and approval workflow
- **Attendance Monitoring**: Real-time attendance tracking and reporting

### 🔐 Access Control System
- **NFC Card Integration**: Contactless access using NFC/RFID technology
- **Real-time Authorization**: Instant access verification through microcontroller system
- **Access Logging**: Comprehensive access history and audit trails
- **Multi-reader Support**: Scalable system supporting multiple card readers

### 📊 Dashboard & Analytics
- **Real-time Overview**: Instant insights into workforce metrics
- **Visual Schedule Display**: Interactive calendar view of work schedules
- **Team Management**: Manager dashboard for team oversight
- **Custom Reports**: Generate detailed reports for various stakeholders

### 🌐 Multi-platform Accessibility
- **Responsive Web Interface**: Cross-browser compatibility with modern design
- **Mobile Application**: Native Android/iOS app for remote access
- **Multi-language Support**: English and Polish language options
- **Accessibility Compliance**: WCAG 2.1 compliant design

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 2.x
- **Security**: Spring Security with JWT authentication
- **Database**: MySQL 8.0
- **ORM**: Hibernate/JPA
- **API Documentation**: Swagger/OpenAPI 3.0
- **Architecture**: Controller-Service-Repository pattern

### Frontend
- **Framework**: Vue.js 3 with Composition API
- **UI Library**: PrimeVue components
- **Styling**: TailwindCSS
- **Icons**: Heroicons
- **Internationalization**: Vue i18n
- **Validation**: Valibot
- **HTTP Client**: Axios

### Mobile Application
- **Framework**: Expo/React Native
- **Styling**: NativeWind (TailwindCSS for React Native)
- **NFC Integration**: react-native-nfc-manager
- **Navigation**: Expo Router
- **Gestures**: React Native Gesture Handler

### Hardware/IoT
- **Microcontroller**: Raspberry Pi Pico W
- **Programming Language**: MicroPython
- **NFC Reader**: MFRC522
- **Communication**: Wi-Fi (IEEE 802.11)
- **Protocols**: HTTP/HTTPS, SPI

### Development Tools
- **Version Control**: Git
- **IDE**: IntelliJ IDEA (Backend), Visual Studio Code (Frontend/Mobile)
- **Design**: Figma, Zeplin
- **Containerization**: Docker
- **Documentation**: PlantUML for diagrams

## 🏗️ System Architecture

The system follows a modern microservice-inspired architecture with four main components:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Web Client    │    │  Mobile Client  │    │ Card Reader     │
│   (Vue.js)      │    │   (Expo)        │    │ (Pico W)        │
└─────────┬───────┘    └─────────┬───────┘    └─────────┬───────┘
          │                      │                      │
          │ HTTPS/REST           │ HTTPS/REST          │ HTTP
          │                      │                     │
          └──────────────────────┼─────────────────────┘
                                 │
                    ┌─────────────▼───────────┐
                    │   Application Server   │
                    │   (Spring Boot)         │
                    └─────────────┬───────────┘
                                  │ SQL
                                  │
                            ┌─────▼─────┐
                            │  Database │
                            │  (MySQL)  │
                            └───────────┘
```

### Communication Flow
1. **Web/Mobile Clients** communicate with the server via RESTful APIs over HTTPS
2. **Application Server** handles business logic, authentication, and data processing
3. **Database** stores all persistent data with relational integrity
4. **Card Reader** performs real-time access control through HTTP requests

## 📊 Database Structure

The database consists of several interconnected tables organized into logical groups:

### Core Tables
- **users**: Employee information and authentication data
- **units**: Organizational structure and hierarchy
- **positions**: Employee-unit relationships with roles
- **schedules**: Work schedule definitions
- **schedule_blocks**: Individual time blocks within schedules

### Access Control Tables
- **access_cards**: NFC card information and assignments
- **user_access**: Access attempt logs and history
- **devices**: Card reader device registry

### Dictionary Tables
- **dict_authorities**: System roles and permissions
- **dict_position_names**: Job position definitions
- **dict_access_card_types**: Card type classifications

## 🚀 Installation & Setup

### Prerequisites
- **Java 11+** for backend development
- **Node.js 16+** for frontend development
- **MySQL 8.0+** for database
- **Docker** (optional, for containerized deployment)
- **Android Studio/Xcode** for mobile development (optional)

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/p-marcol/WMS_System.git
   cd WMS_System/WMS_Backend
   ```

2. **Configure database**
   ```bash
   # Create MySQL database
   mysql -u root -p
   CREATE DATABASE wms_system;
   ```

3. **Configure application properties**
   ```bash
   # Copy and edit configuration
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   
   # Edit database connection settings
   spring.datasource.url=jdbc:mysql://localhost:3306/wms_system
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd WMS_System/WMS_Frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   # or
   yarn install
   ```

3. **Configure environment**
   ```bash
   # Copy environment template
   cp .env.example .env
   
   # Edit API endpoint
   VITE_API_BASE_URL=http://localhost:8080/api
   ```

4. **Start development server**
   ```bash
   npm run dev
   # or
   yarn dev
   ```

### Mobile Application Setup

1. **Navigate to mobile directory**
   ```bash
   cd WMS_System/WMS_Mobile
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure environment**
   ```bash
   # Edit app.config.js with your API endpoint
   export default {
     expo: {
       extra: {
         apiUrl: "http://your-server-ip:8080/api"
       }
     }
   }
   ```

4. **Start development server**
   ```bash
   npx expo start
   ```

### Hardware Setup (NFC Card Reader)

1. **Prepare Raspberry Pi Pico W**
   - Install MicroPython firmware
   - Connect MFRC522 NFC reader module according to circuit diagram

2. **Upload code**
   ```bash
   cd WMS_System/WMS_Embedded
   # Copy main.py to Pico W using Thonny IDE or similar
   ```

3. **Configure network**
   - On first boot, device creates "WMS-AP" Wi-Fi network
   - Connect and configure via http://192.168.4.1
   - Enter your Wi-Fi credentials and server address

## 📖 Usage Guide

### Initial Setup

1. **Administrator Account**
   - Default admin credentials are created during first run
   - Login at: `http://localhost:3000` (or your configured domain)

2. **Create Organizational Structure**
   - Navigate to "Units" section
   - Create departments and sub-departments
   - Define position names and roles

3. **Add Employees**
   - Go to "Users" section
   - Create user accounts with appropriate roles
   - Assign users to organizational units

### Daily Operations

#### Employee Schedule Management
1. Navigate to "Schedule" section
2. Select time period and user/unit
3. Drag and drop to create schedule blocks
4. Save changes

#### Time Tracking
1. Employees can log work hours via:
   - Web interface timesheet
   - NFC card at physical readers
   - Mobile application

#### Access Control
1. Administrators assign NFC cards via mobile app
2. Employees tap cards at readers for access
3. All access attempts are logged automatically

### Mobile App Features

#### For Administrators
- **Card Management**: Scan and assign NFC cards to employees
- **User Overview**: View employee information and status
- **Quick Access**: Essential functions available offline

#### For Employees
- **Schedule View**: Check work schedules
- **Timesheet**: Submit work hour entries
- **Profile**: View personal information

## 🔐 User Roles & Permissions

### 👤 User (Employee)
**Color Code**: Orange (`#FCAB10`)

**Permissions**:
- View own profile and schedule
- Submit timesheet entries
- Access mobile application features
- Use NFC access cards

### 👨‍💼 Manager
**Color Code**: Green (`#ACE849`)

**Permissions** (includes User permissions plus):
- View team schedules and timesheets
- Approve/reject timesheet entries
- Manage team schedules
- View team member profiles

### 👨‍💻 Administrator
**Color Code**: Blue (`#3772FF`)

**Permissions** (includes Manager permissions plus):
- Full system administration
- User account management
- Organizational structure management
- System configuration
- Access control management
- NFC card assignment via mobile app

## 📱 Mobile Application

### Key Features
- **NFC Card Management**: Scan and assign access cards
- **Cross-platform**: iOS and Android support
- **Offline Capability**: Basic functions work without internet
- **Push Notifications**: Important updates and reminders

### NFC Card Assignment Process
1. Administrator opens mobile app
2. Navigates to "Cards" section
3. Taps "Scan Tag" button
4. Holds NFC card near phone
5. If unassigned: Select user from dropdown
6. If assigned: Option to reassign or remove

### Security Features
- JWT token authentication
- Encrypted communication
- Biometric authentication support (where available)
- Session timeout protection

## 🔧 Hardware Component

### NFC Card Reader Specifications
- **Microcontroller**: Raspberry Pi Pico W (RP2040)
- **NFC Module**: MFRC522 (13.56 MHz)
- **Communication**: Wi-Fi 802.11n
- **Power**: 5V USB or external supply
- **Status Indication**: RGB LED

### Supported Card Types
- **Mifare Classic**: 1K/4K variants
- **Mifare Ultralight**: Standard and C variants
- **NTAG**: 213/215/216 series
- **Custom Tags**: Compatible with ISO14443A

### Installation Process
1. **Hardware Assembly**: Connect components according to circuit diagram
2. **Network Configuration**: Configure Wi-Fi via captive portal
3. **Server Registration**: Device auto-registers with main server
4. **Status Monitoring**: LED indicates operational status:
   - Blue: Normal operation
   - Yellow: Processing request
   - Green: Access granted
   - Red: Access denied
   - Orange: Configuration mode

### Circuit Diagram
```
Raspberry Pi Pico W    MFRC522       RGB LED
─────────────────────  ─────────     ────────
3V3                 -> VCC           
GND                 -> GND        -> Common Cathode
GPIO 2              -> RST           
GPIO 3              -> IRQ           
GPIO 4              -> MISO          
GPIO 5              -> MOSI          
GPIO 6              -> SCK           
GPIO 7              -> SDA           
GPIO 8              -> ─          -> Red (via 100Ω)
GPIO 9              -> ─          -> Green (via 85Ω)
GPIO 10             -> ─          -> Blue (via 47Ω)
```

## 🧪 Testing & Validation

### Testing Strategy
The system underwent comprehensive testing across multiple levels:

#### Unit Testing
- **API Endpoints**: All REST endpoints tested with Swagger
- **Database Operations**: CRUD operations validation
- **Authentication**: JWT token generation and validation
- **Data Validation**: Input sanitization and format checking

#### Integration Testing
- **Module Communication**: Web app ↔ Server ↔ Database
- **NFC Hardware**: Card reading and server communication
- **Mobile App**: API integration and offline functionality

#### System Testing
- **End-to-end Workflows**: Complete user journeys
- **Performance**: Response times under load
- **Cross-browser Compatibility**: Chrome, Firefox, Safari, Edge
- **Mobile Platform Testing**: iOS and Android devices

#### Security Testing
- **Authentication**: Login/logout processes
- **Authorization**: Role-based access control
- **Data Protection**: SQL injection and XSS prevention
- **Communication**: HTTPS encryption validation

### Quality Assurance
- **Code Review**: Peer review process
- **Documentation**: Comprehensive API documentation
- **User Acceptance**: Stakeholder validation
- **Performance Metrics**: Response time < 1 second for typical operations

## 🔒 Security Features

### Authentication & Authorization
- **JWT Tokens**: Secure, stateless authentication
- **Role-based Access**: Granular permission system
- **Session Management**: Automatic token refresh
- **Password Security**: BCrypt hashing with salt

### Data Protection
- **HTTPS Communication**: All client-server communication encrypted
- **SQL Injection Prevention**: Parameterized queries via JPA
- **XSS Protection**: Input sanitization and output encoding
- **CSRF Protection**: Spring Security token validation

### Access Control
- **NFC Card Security**: Unique UID-based identification
- **Device Registration**: Authenticated hardware registration
- **Access Logging**: Comprehensive audit trail
- **Failed Attempt Monitoring**: Suspicious activity detection

### Compliance
- **GDPR Ready**: User data anonymization and deletion
- **Audit Trails**: Complete access and modification logs
- **Data Retention**: Configurable retention policies
- **Privacy Controls**: User consent management

## 🌟 Future Development

### Planned Enhancements

#### Short-term (Next Release)
- **Report Generation**: PDF reports for schedules and timesheets
- **Request Management**: Leave requests and approvals
- **Enhanced Mobile Features**: Offline synchronization
- **Multi-reader Support**: Support for multiple card readers

#### Medium-term (6-12 months)
- **Advanced Analytics**: Machine learning insights
- **Integration APIs**: Third-party HR system integration
- **Mobile Push Notifications**: Real-time updates
- **Advanced Scheduling**: Automated schedule optimization

#### Long-term (1-2 years)
- **AI-powered Features**: Predictive analytics for workforce planning
- **IoT Expansion**: Additional sensor integration
- **Blockchain Integration**: Immutable audit trails
- **Advanced Biometrics**: Fingerprint and facial recognition

### Technology Roadmap
- **Microservices Migration**: Full microservices architecture
- **Cloud Native**: Kubernetes deployment support
- **Real-time Features**: WebSocket integration
- **Progressive Web App**: Enhanced offline capabilities

## 🤝 Contributing

We welcome contributions from the community! Here's how you can help:

### Development Guidelines

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Follow coding standards**
   - Java: Google Java Style Guide
   - JavaScript: ESLint configuration
   - Commit messages: Conventional Commits

4. **Write tests**
   - Unit tests for new functionality
   - Integration tests for API changes
   - Update documentation

5. **Submit a pull request**
   - Provide clear description
   - Include test results
   - Reference related issues

### Code Style
- **Backend**: Follow Spring Boot conventions
- **Frontend**: Vue.js official style guide
- **Mobile**: React Native best practices
- **Database**: Proper normalization and indexing

### Reporting Issues
- Use GitHub issue templates
- Provide detailed reproduction steps
- Include system information
- Add relevant screenshots/logs

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2025 Piotr Marcol

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

## 👨‍🎓 Academic Information

**Institution**: Silesian University of Technology (Politechnika Śląska)  
**Faculty**: Faculty of Automatic Control, Electronics and Computer Science  
**Department**: Department of Algorithms and Software  
**Degree Program**: Computer Science  
**Specialization**: Mobile and Industrial Computer Systems  
**Project Type**: Engineering Thesis (Projekt Inżynierski)  
**Academic Year**: 2024/2025  
**Author**: Piotr Marcol (Student ID: 300463)  
**Supervisor**: Dr inż. Marcin Połomski  

### Thesis Abstract
The aim of this thesis was to develop a workforce management system for a company. It describes the process by which the requirements were set, the design was carried out, and finally the complete IT system was implemented and tested. The solution consists of four modules, each with its own well-defined role: an application server, a web application, a mobile application, and a microcontroller unit. The creation of the system has brought tangible benefits, and the directions that can be taken in its development are linked to new technologies such as artificial intelligence or the Internet of Things.

### Academic Contributions
- **Practical Application**: Real-world implementation of software engineering principles
- **Technology Integration**: Successful combination of web, mobile, and IoT technologies
- **System Architecture**: Demonstration of scalable, modular design patterns
- **Security Implementation**: Applied cybersecurity best practices
- **Documentation**: Comprehensive technical documentation and user guides

## 📞 Contact

**Piotr Marcol**
- GitHub: [@p-marcol](https://github.com/p-marcol)
- University Email: [pm300463@student.polsl.pl](pm300463@student.polsl.pl)
- LinkedIn: [in/piotr-marcol-51b263264](https://www.linkedin.com/in/piotr-marcol-51b263264/)

**Academic Supervisor**
- Dr inż. Marcin Połomski
- Department of Algorithms and Software
- Silesian University of Technology

---

## 🙏 Acknowledgments

- **Silesian University of Technology** for academic support and resources
- **Dr inż. Marcin Połomski** for supervision and guidance throughout the project
- **Open Source Community** for providing excellent tools and libraries
- **Beta Testers** who helped improve the system through feedback
- **Family and Friends** for support during development

---

## 📈 Project Statistics

- **Lines of Code**: ~15,000 (Backend: ~8,000, Frontend: ~5,000, Mobile: ~2,000)
- **Development Time**: 5 months (September 2024 - January 2025)
- **Technologies Used**: 20+ frameworks and libraries
- **Database Tables**: 12 main tables + dictionary tables
- **API Endpoints**: 50+ RESTful endpoints
- **Test Coverage**: Unit and integration tests implemented
- **Documentation Pages**: 75+ pages of technical documentation

---

<div align="center">
  <p><strong>Made with ❤️ for efficient workforce management</strong></p>
  <p>© 2025 Piotr Marcol. All rights reserved.</p>
  
  <p>
    <em>This project demonstrates the practical application of modern software engineering principles<br>
    in developing enterprise-grade workforce management solutions.</em>
  </p>
</div>
