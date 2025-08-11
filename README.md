# Point of Sale (POS) - Inventory Management System - Java Backend

## Overview

This is a comprehensive Point of Sale (POS) Inventory Management System backend built with Java Spring Boot, converted from the original C# .NET Framework application. The system maintains the same 5-layer architecture and functionality while leveraging modern Java technologies.

## Architecture

The system follows a clean layered architecture:

1. **Controller Layer** - REST API endpoints
2. **Service Layer** - Business logic
3. **Repository Layer** - Data access
4. **Entity Layer** - Domain models
5. **DTO Layer** - Data transfer objects

## Technologies Used

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Data persistence
- **JWT** - Token-based authentication
- **MapStruct** - Object mapping
- **Lombok** - Boilerplate code reduction
- **Microsoft SQL Server** - Database
- **Maven** - Build tool

## Features

### User Management
- Three user roles: Admin, Cashier, Salesman
- JWT-based authentication
- Role-based access control
- User CRUD operations
- Salary tracking

### Product Management
- Hierarchical category system (Main → Second → Third)
- Brand and vendor management
- Product CRUD operations
- Stock management
- Product search and filtering

### Sales Management
- Order creation and management
- Sales history tracking
- Customer information management
- Payment method tracking
- Stock deduction on sales

### Expense Management
- Expense tracking
- Date-based expense reports
- Category-wise expense management

### Dashboard & Analytics
- Real-time statistics
- Sales analytics (daily, weekly, monthly)
- Expense analytics
- User and product statistics
- Role-based dashboard access

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `GET /api/auth/me` - Get current user
- `POST /api/auth/logout` - User logout

### Users
- `GET /api/users` - Get all users (Admin only)
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create user (Admin only)
- `PUT /api/users/{id}` - Update user (Admin only)
- `DELETE /api/users/{id}` - Delete user (Admin only)

### Products
- `GET /api/products` - Get all products
- `GET /api/products/available` - Get available products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create product (Admin only)
- `PUT /api/products/{id}` - Update product (Admin only)
- `DELETE /api/products/{id}` - Delete product (Admin only)

### Orders
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create order
- `PUT /api/orders/{id}` - Update order
- `DELETE /api/orders/{id}` - Delete order (Admin only)

### Categories
- `GET /api/categories/main` - Get main categories
- `POST /api/categories/main` - Create main category (Admin only)
- `GET /api/categories/second` - Get second categories
- `POST /api/categories/second` - Create second category (Admin only)
- `GET /api/categories/third` - Get third categories
- `POST /api/categories/third` - Create third category (Admin only)

### Expenses
- `GET /api/expenses` - Get all expenses (Admin only)
- `POST /api/expenses` - Create expense (Admin only)
- `PUT /api/expenses/{id}` - Update expense (Admin only)
- `DELETE /api/expenses/{id}` - Delete expense (Admin only)

### Dashboard
- `GET /api/dashboard/stats` - Get dashboard statistics (Admin only)

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Microsoft SQL Server 2019 or higher
- SQL Server Management Studio (optional)

### Database Setup
1. Create a database named `inventoryDB`
2. Update database connection settings in `application.yml`
3. The application will automatically create tables and insert sample data

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Run `mvn clean install`
4. Run `mvn spring-boot:run`
5. The API will be available at `http://localhost:8080/api`

### Default Users
- **Admin**: userId: `U-A-0001`, password: `password`
- **Cashier**: userId: `U-C-0001`, password: `password`
- **Salesman**: userId: `U-S-0001`, password: `password`

## Security

- JWT-based stateless authentication
- Role-based access control
- Password encryption using BCrypt
- CORS configuration for frontend integration
- Method-level security annotations

## Role Permissions

### Admin
- Full access to all features
- User management
- Product management
- Category management
- Expense management
- Dashboard analytics
- Database operations

### Cashier
- Make sales
- View sales history
- Manage stock
- Limited dashboard access

### Salesman
- Make sales only
- View own sales history

## Error Handling

The application includes comprehensive error handling with:
- Custom exception classes
- Global exception handler
- Validation error responses
- Proper HTTP status codes

## Future Enhancements

- Database backup/restore functionality
- Barcode generation
- Report generation
- Email notifications
- Audit logging
- File upload for product images

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.