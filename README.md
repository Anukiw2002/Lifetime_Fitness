# Lifetime Fitness Management System

A comprehensive web-based fitness center management system built with Java, featuring user management, workout tracking, session booking, and payment processing capabilities.

## 🏋️ Project Overview

Lifetime Fitness is a full-stack web application designed to manage fitness center operations. It provides role-based access for clients, instructors, and owners, offering features from workout planning and progress tracking to membership management and payment processing.

## 🚀 Features

### 👥 User Management
- **Multi-role Authentication**: Client, Instructor, and Owner roles
- **Secure Registration/Login**: BCrypt password hashing
- **Profile Management**: Complete user profiles with profile pictures
- **Password Recovery**: Forgot password functionality with email verification

### 🏃 Fitness Tracking
- **Workout Planning**: Create and manage custom workout routines
- **Exercise Library**: Comprehensive exercise database with categories
- **Progress Tracking**: Monitor workout statistics and personal records
- **Workout Logs**: Detailed logging of completed exercises
- **Leaderboards**: Competition tracking and rankings

### 📊 Reporting & Analytics
- **Fitness Reports**: Comprehensive health and fitness assessments
- **BMR Calculations**: Basal Metabolic Rate tracking
- **Weight Tracking**: Monitor weight progress over time
- **Goal Setting**: Set and track fitness goals
- **Dashboard Analytics**: Visual progress charts and statistics

### 📅 Session Management
- **Session Booking**: Schedule training sessions with instructors
- **Calendar Integration**: View and manage appointments
- **Booking Constraints**: Flexible scheduling rules
- **Session Rescheduling**: Modify existing bookings
- **Availability Management**: Instructor schedule management

### 💳 Payment Processing
- **PayPal Integration**: Secure payment processing
- **Membership Plans**: Multiple subscription tiers
- **Pricing Management**: Dynamic pricing for different services
- **Payment History**: Transaction tracking and receipts

### 📝 Content Management
- **Blog System**: Fitness articles and tips
- **Video Library**: Exercise tutorials and guides
- **Review System**: Client feedback and ratings
- **Notifications**: Real-time system notifications

### 🏥 Health Management
- **Medical History**: Comprehensive health profiles
- **Emergency Contacts**: Safety contact information
- **Health Assessments**: Regular fitness evaluations

## 🛠️ Technology Stack

### Backend
- **Java 10**: Core programming language
- **Jakarta Servlet API**: Web application framework
- **Maven**: Build and dependency management
- **PostgreSQL**: Primary database
- **BCrypt**: Password encryption
- **PayPal SDK**: Payment processing

### Frontend
- **JSP (JavaServer Pages)**: Dynamic web pages
- **JSTL**: JSP Standard Tag Library
- **HTML5/CSS3**: Modern web standards
- **JavaScript**: Client-side interactions

### Libraries & Dependencies
- **Jackson**: JSON data binding
- **Gson**: JSON processing
- **Apache Commons**: Utility libraries
- **JUnit 5**: Unit testing framework
- **Jakarta Mail**: Email functionality

## 📋 Prerequisites

Before setting up the project, ensure you have:

- **Java Development Kit (JDK) 10 or higher**
- **Apache Maven 3.6+**
- **PostgreSQL 12+**
- **Apache Tomcat 9+ or similar servlet container**
- **Git** for version control

## ⚙️ Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/Anukiw2002/Lifetime_Fitness.git
cd Lifetime_Fitness
```

### 2. Database Setup
1. Create a PostgreSQL database named `finalPrep`
2. Update database credentials in `src/main/java/org/example/demo2/util/DBConnection.java`:
   ```java
   private static final String URL = "jdbc:postgresql://localhost:5432/finalPrep";
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";
   ```

### 3. Configure PayPal Integration
Update PayPal credentials in `src/main/java/org/example/demo2/dao/PaymentServicesDAO.java`:
```java
private static final String CLIENT_ID = "your_paypal_client_id";
private static final String CLIENT_SECRET = "your_paypal_client_secret";
private static final String MODE = "sandbox"; // Use "live" for production
```

### 4. Build the Project
```bash
mvn clean compile
mvn package
```

### 5. Deploy to Tomcat
1. Copy the generated WAR file from `target/demo2-1.0-SNAPSHOT.war` to your Tomcat `webapps` directory
2. Start Tomcat server
3. Access the application at `http://localhost:8080/demo2-1.0-SNAPSHOT/`

## 🗄️ Database Schema

The application uses PostgreSQL with the following main entities:

- **users**: User account information
- **client_details**: Extended client information
- **reviews**: User reviews and ratings
- **reports**: Fitness assessment reports
- **workouts**: Workout routines and exercises
- **bookings**: Session reservations
- **memberships**: Subscription plans
- **payments**: Transaction records
- **blogs**: Content management
- **videos**: Exercise tutorials

## 🔐 User Roles & Permissions

### 👤 Client
- Register and manage personal profile
- Book training sessions
- Track workout progress
- Submit reviews and feedback
- Access fitness reports
- Make payments for services

### 🏋️ Instructor
- Manage client sessions
- Create workout plans
- Upload educational content
- View client progress
- Manage availability

### 👑 Owner
- System administration
- Manage instructors and clients
- View comprehensive analytics
- Configure pricing and plans
- Access all system features

## 🚪 API Endpoints

### Authentication
- `POST /login` - User authentication
- `POST /register` - User registration
- `POST /logout` - User logout
- `POST /resetPassword` - Password reset

### User Management
- `GET /clientDashboard` - Client dashboard
- `GET /instructorDashboard` - Instructor dashboard
- `GET /ownerDashboard` - Owner dashboard
- `POST /clientEditProfile` - Update client profile

### Booking System
- `POST /bookSession` - Book a training session
- `GET /viewBookings` - View scheduled sessions
- `POST /cancelBooking` - Cancel a booking
- `POST /rescheduleBooking` - Reschedule a session

### Content Management
- `GET /getAllBlogs` - Retrieve blog posts
- `POST /uploadBlog` - Create new blog post
- `GET /getAllVideos` - Retrieve video library
- `POST /uploadVideo` - Upload new video

### Payment Processing
- `POST /authorizePayment` - Initialize PayPal payment
- `GET /reviewPayment` - Review payment details
- `POST /executePayment` - Complete payment transaction

## 🏃 Getting Started

### For New Users
1. Visit the registration page
2. Create an account with your email and basic information
3. Complete your profile setup
4. Choose a membership plan
5. Start booking sessions and tracking workouts

### For Developers
1. Follow the installation instructions above
2. Review the codebase structure:
   - `src/main/java/org/example/demo2/model/` - Data models
   - `src/main/java/org/example/demo2/dao/` - Database access layer
   - `src/main/java/org/example/demo2/servlet/` - HTTP request handlers
   - `src/main/webapp/jsp/` - Frontend pages
3. Check the API documentation for endpoint details
4. Run tests with `mvn test`

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

The project includes JUnit 5 tests for core functionality.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is currently private. Please contact the repository owner for licensing information.

## 📞 Support

For support and questions:
- Create an issue in the GitHub repository
- Contact the development team

## 🗓️ Project Status

This project is actively maintained and under continuous development. New features and improvements are regularly added based on user feedback and fitness industry requirements.

---

**Built with ❤️ for the fitness community**